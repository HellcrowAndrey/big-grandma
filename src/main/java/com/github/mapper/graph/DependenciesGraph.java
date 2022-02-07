package com.github.mapper.graph;

import com.github.mapper.utils.MapperUtils;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

public class DependenciesGraph {

    private final Root root;

    public DependenciesGraph(Root root) {
        this.root = root;
        this.root.graphs.forEach(graph -> graph.keyLinks(this.root.key));
    }

    @SuppressWarnings(value = "unchecked")
    public <T> Flux<T> mapMany(List<Map<String, Object>> tuples) {
        return Flux.create(sink -> {
            try {
                Map<Object, List<Map<String, Object>>> groupByRoot = tuples.stream()
                        .collect(Collectors.groupingBy(
                                        k -> MapperUtils.ofEntity(k, root.rootType),
                                        Collectors.toList()
                                )
                        );
                for (Object target : groupByRoot.keySet()) {
                    List<SubGraph> graphs = root.graphs;
                    List<Map<String, Object>> values = groupByRoot.get(target);
                    Map<Key, Object> groupByKey = new HashMap<>();
                    groupByKey.put(new Key(root.rootType), target);
                    for (Map<String, Object> value : values) {
                        for (SubGraph subGraph : graphs) {
                            subGraph.round(value, groupByKey, new Node( null, target, null));
                        }
                    }
                    for (Key key : groupByKey.keySet()) {
                        String fileName = key.fileName;
                        if (MapperUtils.isFieldExist(fileName, target)) {
                            Object source = groupByKey.get(key);
                            MapperUtils.setFields(source, target, fileName);
                        }
                    }
                    sink.next((T) target);
                }
            } catch (Throwable e) {
                sink.error(e);
            }
            sink.complete();
        });
    }

    static class Root {

        Class<?> rootType;

        List<SubGraph> graphs; //optional

        Key key;

        public Root(Class<?> rootType, List<SubGraph> graphs) {
            this.rootType = Objects.requireNonNull(rootType);
            this.graphs = Objects.requireNonNullElse(graphs, new ArrayList<>());
            this.key = new Key(rootType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Root)) return false;
            Root root1 = (Root) o;
            return Objects.equals(rootType, root1.rootType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rootType);
        }
    }

    static class SubGraph {

        Class<?> rootType;

        Class<?> currentType;

        String fileName;

        Class<?> collType;

        List<SubGraph> graphs = new ArrayList<>(); //optional

        Key key;

        public SubGraph(Class<?> rootType, Class<?> currentType, String fileName) {
            this.rootType = Objects.requireNonNull(rootType);
            this.currentType = Objects.requireNonNull(currentType);
            this.fileName = Objects.requireNonNull(fileName);
            this.key = new Key(rootType, currentType, fileName, null);
        }

        public SubGraph(Class<?> rootType, Class<?> currentType, String fileName, Class<?> collType) {
            this(rootType, currentType, fileName);
            if (!MapperUtils.isColl(collType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", collType));
            }
            this.collType = MapperUtils.collTypeMapper(Objects.requireNonNull(collType));
            this.key = new Key(rootType, currentType, fileName, this.collType);
        }

        public SubGraph(Class<?> rootType, Class<?> currentType, String fileName,
                        Class<?> collType, List<SubGraph> graphs) {
            this(rootType, currentType, fileName, collType);
            this.graphs = graphs;
        }

        public void keyLinks(Key prevKey) {
            key.prev = prevKey;
            this.graphs.forEach(nexKey -> {
                key.next = nexKey.key;
                nexKey.keyLinks(key);
            });
        }

        private void round(Map<String, Object> values, Map<Key, Object> groupBy, Node node) {
            Key sourceKey = findKey();
            Object source = groupBy.get(sourceKey);
            if (Objects.nonNull(sourceKey) && Objects.nonNull(source)) {
                if (isBidirectional()) {
                    Key containerKey = key.prev;
                    if (Objects.isNull(sourceKey.collType)) {
                        if (Objects.isNull(containerKey.collType)) {
                            Object prevTarget = node.prevTarget;
                            if (Objects.nonNull(prevTarget)) {
                                MapperUtils.setFields(source, prevTarget, this.fileName);
                                MapperUtils.setFields(prevTarget, source, containerKey.fileName);
                            }
                        } else {
                            Collection<?> prevCollTarget = node.prevCollTarget;
                            if (Objects.nonNull(prevCollTarget)) {
                                prevCollTarget.forEach(target ->
                                        MapperUtils.setFields(source, target, this.fileName)
                                );
                                MapperUtils.setFields(prevCollTarget, source, containerKey.fileName);
                            }
                        }
                    } else {
                        Object prevPrevTarget = node.prev.prevTarget;
                        if (Objects.nonNull(prevPrevTarget)) {
                            if (Objects.nonNull(containerKey.collType)) {
                                Collection<?> prevCollTarget = node.prevCollTarget;
                                if (Objects.nonNull(prevCollTarget)) {
                                    Object prevTarget = node.prevTarget;
                                    Collection<?> tmp = MapperUtils.cast(groupBy.get(key.prev.prev));
                                    Object container = tmp.stream()
                                            .filter(elem -> elem.equals(prevPrevTarget))
                                            .findFirst().orElse(null);
                                    if (Objects.nonNull(container)) {
                                        Collection<Object> coll = MapperUtils.getCollections(containerKey.fileName, containerKey.rootType, container);
                                        if (Objects.isNull(coll)) {
                                            coll = MapperUtils.collFactory(containerKey.collType);
                                        }
                                        coll.add(prevTarget);
                                        MapperUtils.setFields(container, prevTarget, this.fileName);
                                    }
                                }
                            } else {
                                // TODO: 06.02.22 check it case ?
                                Object prevTarget = node.prevTarget;
                                MapperUtils.setFields(prevTarget, prevPrevTarget, containerKey.fileName);
                                MapperUtils.setFields(prevPrevTarget, prevTarget, this.fileName);
                            }
                        }
                    }
                } else {
                    Object obj;
                    Collection<Object> coll = null;
                    if (Objects.isNull(this.collType)) {
                        obj = MapperUtils.ofEntity(values, this.currentType);
                        if (Objects.isNull(sourceKey.collType)) {
                            MapperUtils.setFields(obj, source, this.fileName);
                        } else {
                            coll = MapperUtils.cast(source);
                            coll.forEach(val -> MapperUtils.setFields(obj, val, this.fileName));
                        }
                        groupBy.put(this.key, obj);
                    } else {
                        Object val = groupBy.get(this.key);
                        obj = MapperUtils.ofEntity(values, this.currentType);
                        if (Objects.isNull(val)) {
                            Collection<Object> collTo = MapperUtils.collFactory(this.collType);
                            collTo.add(obj);
                            Collection<Object> collFrom = MapperUtils.cast(source);
                            collFrom.forEach(s -> collTo.forEach(t ->
                                            MapperUtils.setFields(s, t, sourceKey.fileName)
                                    )
                            );
                            coll = collTo;
                            groupBy.put(this.key, collTo);
                        } else {
                            // TODO: 03.02.22 check many to many ?
                            Collection<Object> collTo = MapperUtils.cast(val);
                            collTo.add(obj);
                            coll = collTo;
                        }
                    }
                    if (!this.graphs.isEmpty()) {
                        for (SubGraph graph : this.graphs) {
                            graph.round(values, groupBy, new Node(node, obj, coll));
                        }
                    }
                }
            } else {
                Object obj = null;
                Collection<Object> coll = null;
                if (Objects.isNull(this.collType)) {
                    if (Objects.isNull(groupBy.get(this.key))) {
                        obj = MapperUtils.ofEntity(values, this.currentType);
                        groupBy.put(this.key, obj);
                    }
                } else {
                    Object val = groupBy.get(this.key);
                    obj = MapperUtils.ofEntity(values, this.currentType);
                    if (Objects.isNull(val)) {
                        coll = MapperUtils.collFactory(this.collType);
                    } else {
                        coll = MapperUtils.cast(val);
                    }
                    if (!coll.contains(obj)) {
                        coll.add(obj);
                        groupBy.put(this.key, coll);
                    }
                }
                if (!this.graphs.isEmpty()) {
                    for (SubGraph graph : this.graphs) {
                        graph.round(values, groupBy, new Node(node, obj, coll));
                    }
                }
            }
        }

        boolean isBidirectional() {
            return this.currentType.equals(key.prev.rootType);
        }

        Key findKey() {
            Key index = key.prev;
            if (this.currentType.equals(index.rootType)) {
                return key.prev.prev;
            } else if (this.rootType.equals(index.currentType)) {
                return index;
            }
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SubGraph)) return false;
            SubGraph subGraph = (SubGraph) o;
            return Objects.equals(rootType, subGraph.rootType) &&
                    Objects.equals(fileName, subGraph.fileName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rootType, fileName);
        }
    }

    static class Key {

        Class<?> rootType;

        Class<?> currentType;

        String fileName;

        Class<?> collType;

        Key next; //optional

        Key prev; //optional

        public Key(Class<?> rootType) {
            this.rootType = rootType;
        }

        public Key(Class<?> rootType, Class<?> currentType, String fileName, Class<?> collType) {
            this.rootType = rootType;
            this.currentType = currentType;
            this.fileName = fileName;
            this.collType = collType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return Objects.equals(rootType, key.rootType) &&
                    Objects.equals(currentType, key.currentType) &&
                    Objects.equals(fileName, key.fileName) &&
                    Objects.equals(collType, key.collType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rootType, currentType, fileName, collType);
        }

        @Override
        public String toString() {
            return "Key{" +
                    "rootType=" + rootType +
                    ", currentType=" + currentType +
                    ", fileName='" + fileName + '\'' +
                    ", collType=" + collType +
                    '}';
        }
    }

    static class Node {

        Node prev;

        Object prevTarget;

        Collection<?> prevCollTarget;

        public Node(Node prev, Object prevTarget, Collection<?> prevCollTarget) {
            this.prev = prev;
            this.prevTarget = prevTarget;
            this.prevCollTarget = prevCollTarget;
        }

    }

}
