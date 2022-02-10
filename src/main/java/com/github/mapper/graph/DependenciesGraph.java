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
                Map<Object, List<Round>> groupByRoot = tuples.stream()
                        .collect(Collectors.groupingBy(
                                        k -> MapperUtils.ofEntity(k, root.rootType),
                                        LinkedHashMap::new,
                                        Collectors.flatMapping(tuple -> root.graphs.stream()
                                                        .map(subGraph -> subGraph.restore(tuple, 0))
                                                , RoundCollector.toListOfRounds()
                                        )
                                )
                        );
                for (Object target : groupByRoot.keySet()) {
                    Map<String, Object> values = new HashMap<>();
                    List<Round> rounds = groupByRoot.get(target);
                    List<SubGraph> graphs = root.graphs;
                    for (Round round : rounds) {
                        for (SubGraph graph : graphs) {
                            graph.rounds(target, round, values);
                        }
                    }
                    for (String key : values.keySet()) {
                        if (MapperUtils.isFieldExist(key, target)) {
                            Object source = values.get(key);
                            MapperUtils.setFields(source, target, key);
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

    public static class Root {

        Class<?> rootType;

        List<SubGraph> graphs; //optional

        Key key;

        Round round;

        public Root(Class<?> rootType, List<SubGraph> graphs) {
            this.rootType = Objects.requireNonNull(rootType);
            this.graphs = Objects.requireNonNullElse(graphs, new ArrayList<>());
            this.key = new Key(rootType);
        }

        public void setRound(Round round) {
            this.round = round;
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

    static class Key {

        Class<?> rootType;

        Class<?> currentType;

        String rootFieldName;

        String currentFieldName;

        Class<?> collType;

        Key next; //optional

        Key prev; //optional

        public Key(Class<?> rootType) {
            this.rootType = rootType;
        }

        public Key(Class<?> rootType, Class<?> currentType, String rootFieldName, String currentFieldName, Class<?> collType) {
            this.rootType = rootType;
            this.currentType = currentType;
            this.rootFieldName = rootFieldName;
            this.currentFieldName = currentFieldName;
            this.collType = collType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return Objects.equals(rootType, key.rootType) &&
                    Objects.equals(currentType, key.currentType) &&
                    Objects.equals(rootFieldName, key.rootFieldName) &&
                    Objects.equals(currentFieldName, key.currentFieldName) &&
                    Objects.equals(collType, key.collType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rootType, currentType, rootFieldName, currentFieldName, collType);
        }

        @Override
        public String toString() {
            return "Key{" +
                    "rootType=" + rootType +
                    ", currentType=" + currentType +
                    ", rootFieldName='" + rootFieldName + '\'' +
                    ", currentFieldName='" + currentFieldName + '\'' +
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
