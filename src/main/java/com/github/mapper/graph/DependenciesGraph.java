package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.CollectionsUtils;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.mapper.utils.MapperUtils.*;

public class DependenciesGraph {

    private static final int START_POINT = 0;

    private final Root root;

    public DependenciesGraph(Root root) {
        this.root = root;
    }

    public <T> Flux<T> many(List<Map<String, Object>> tuples) {
        return intermediateState(tuples)
                .flatMapMany(this::toTargets);
    }

    @SuppressWarnings(value = "unchecked")
    public <T> Mono<T> single(List<Map<String, Object>> tuples) {
        return (Mono<T>) intermediateState(tuples)
                .flatMapMany(groupBy -> this.toTargets(groupBy).take(1))
                .single();
    }

    private Mono<LinkedHashMap<RootRound, List<Round>>> intermediateState(List<Map<String, Object>> tuples) {
        return Flux.fromStream(tuples.stream())
                .filter(DependenciesGraph::isTupleEmpty)
                .map(tuple -> root.restoreRootRound(tuple, START_POINT))
                .collect(RootRoundCollector.toListOfRootRounds())
                .flatMapMany(source -> Flux.fromStream(source.stream()))
                .collect(Collectors.groupingBy(
                        source -> source,
                        LinkedHashMap::new,
                        Collectors.flatMapping(source -> source.nonMappedValues.stream()
                                        .flatMap(tuple -> root.graphOneToEtc.stream()
                                                .map(subGraph -> subGraph.restore(tuple, START_POINT))),
                                RoundCollector.toListOfRounds()
                        )
                ));
    }

    private <T> Flux<T> toTargetManyToMany(LinkedHashMap<RootRound, List<Round>> groupByRoot) {
        return Mono.just(groupByRoot.keySet())
                .flatMapMany(rootRounds -> {
                    List<T> result = rootRounds.stream().map(rootRound -> {
                        T target = ofTarget(rootRound, groupByRoot);
                        this.root.roundsManyToMany(rootRound);
                        return target;
                    }).collect(Collectors.toList());
                    return Flux.fromStream(result.stream());
                });
    }

    private <T> Flux<T> toTargetOneToEtc(LinkedHashMap<RootRound, List<Round>> groupByRoot) {
        return Flux.fromStream(groupByRoot.keySet().stream())
                .map(rootRound -> ofTarget(rootRound, groupByRoot));
    }

    @SuppressWarnings(value = "unchecked")
    private <T> T ofTarget(RootRound rootRound, LinkedHashMap<RootRound, List<Round>> groupByRoot) {
        Object target = rootRound.value;
        Map<String, Object> values = new HashMap<>();
        List<Round> rounds = groupByRoot.get(rootRound);
        List<SubGraph> graphs = root.graphOneToEtc;
        rounds.forEach(round ->
                graphs.forEach(graph ->
                        graph.rounds(target, round, values)
                )
        );
        mapFields(values, target);
        return (T) target;
    }

    private <T> Flux<T> toTargets(LinkedHashMap<RootRound, List<Round>> groupByRoot) {
        switch (this.root.type) {
            case oneToEtc:
                return toTargetOneToEtc(groupByRoot);
            case manyToMany:
                return toTargetManyToMany(groupByRoot);
            default:
                throw new IllegalArgumentException("Unsupported Relation type");
        }
    }

    private static boolean isTupleEmpty(Map<String, Object> values) {
        return !values.isEmpty();
    }

    public static class Root {

        Class<?> rootType;

        Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); //optional

        List<SubGraph> graphOneToEtc; //optional

        Map<String, Field> fields; // required

        RelationType type;

        @Deprecated
        public Root(Class<?> rootType, List<SubGraph> graphOneToEtc) {
            this.rootType = Objects.requireNonNull(rootType);
            this.graphOneToEtc = Objects.requireNonNullElse(graphOneToEtc, new ArrayList<>());
            this.type = RelationType.oneToEtc;
        }

        private Root(RootBuilder b) {
            this.rootType = b.rootType;
            this.graphOneToEtc = b.graphOneToEtc;
            this.fields = b.fields;
            this.type = b.type;
        }

        private Root(RootManyToManyBuilder b) {
            this.rootType = b.rootType;
            this.graphsManyToMany = b.graphsManyToMany;
            this.graphOneToEtc = b.graphs;
            this.fields = b.fields;
            this.type = b.type;
        }

        public static class RootBuilder {

            Class<?> rootType;

            List<SubGraph> graphOneToEtc = new ArrayList<>(); //optional

            Map<String, Field> fieldNames = new HashMap<>();

            Map<String, Field> fields; // required

            RelationType type;

            public RootBuilder() {
                this.type = RelationType.oneToEtc;
            }

            public RootBuilder rootType(Class<?> rootType) {
                this.rootType = Objects.requireNonNull(rootType);
                this.fieldNames.putAll(Arrays.stream(rootType.getDeclaredFields())
                        .collect(Collectors.toMap(Field::getName, Function.identity())));
                return this;
            }

            public RootBuilder graphOneToEtc(SubGraph graph) {
                this.graphOneToEtc.add(graph);
                return this;
            }

            public RootBuilder aliases(Map<String, String> aliases) {
                this.fields.putAll(MapperUtils.fields(aliases, this.rootType));
                return this;
            }

            public RootBuilder alias(String alias, String fieldName) {
                Field field = this.fieldNames.get(fieldName);
                this.fields.put(alias, field);
                return this;
            }

            public Root build() {
                if (this.fields.isEmpty()) {
                    throw new IllegalArgumentException("Fields is empty pleas add fields to this class");
                }
                return new Root(this);
            }

        }

        public static class RootManyToManyBuilder {

            Class<?> rootType;

            Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); //optional

            List<SubGraph> graphs = new ArrayList<>(); //optional

            Map<String, Field> fieldNames = new HashMap<>();

            Map<String, Field> fields; // required

            RelationType type;

            public RootManyToManyBuilder() {
                this.type = RelationType.manyToMany;
            }

            public RootManyToManyBuilder rootType(Class<?> rootType) {
                this.rootType = Objects.requireNonNull(rootType);
                this.fieldNames.putAll(Arrays.stream(rootType.getDeclaredFields())
                        .collect(Collectors.toMap(Field::getName, Function.identity())));
                return this;
            }

            public RootManyToManyBuilder graphsManyToMany(SubGraph outside) {
                this.graphsManyToMany.put(outside.currentType, outside);
                return this;
            }

            public RootManyToManyBuilder graphOneToEtc(SubGraph graph) {
                this.graphs.add(graph);
                return this;
            }

            public RootManyToManyBuilder aliases(Map<String, String> aliases) {
                this.fields.putAll(MapperUtils.fields(aliases, this.rootType));
                return this;
            }

            public RootManyToManyBuilder alias(String alias, String fieldName) {
                if (!StringUtils.hasText(alias) || !StringUtils.hasText(fieldName)) {
                    throw new IllegalArgumentException(String.format("Alias -> %s or field name -> %s", alias, fieldName));
                }
                Field field = this.fieldNames.get(fieldName);
                this.fields.put(alias, field);
                return this;
            }

            public Root build() {
                if (this.fields.isEmpty()) {
                    throw new IllegalArgumentException("Fields is empty pleas add fields to this class");
                }
                return new Root(this);
            }

        }

        public RootRound restoreRootRound(Map<String, Object> nonMappedValues, int lvl) {
            switch (this.type) {
                case oneToEtc:
                    return restoreOneToEtc(nonMappedValues);
                case manyToMany:
                    return restoreManyToMany(nonMappedValues, lvl);
                default:
                    throw new IllegalArgumentException("Unsupported Relation type");
            }
        }

        private RootRound restoreOneToEtc(Map<String, Object> nonMappedValues) {
            return new RootRound(EntityFactory.ofEntity(nonMappedValues, this.fields, this.rootType), nonMappedValues) {
                @Override
                void putLeft(Round left, Object value) {
                    throw new UnsupportedOperationException();
                }

                @Override
                void collectRoundLeft(Map<Object, Object> rightValues, Object roundRootVal, Map<Round, Set<Object>> newLefts) {
                    throw new UnsupportedOperationException();
                }

                @Override
                boolean hashManyToMany() {
                    return Boolean.FALSE;
                }
            };
        }

        private RootRound restoreManyToMany(Map<String, Object> nonMappedValues, int lvl) {
            Object value = EntityFactory.ofEntity(nonMappedValues, this.fields, this.rootType);
            RootRound right = manyToManyRoot(value, nonMappedValues);
            if (!this.graphsManyToMany.isEmpty()) {
                var defaultLvl = lvl + 1;
                this.graphsManyToMany.values().forEach(left -> {
                    Round round = left.restore(nonMappedValues, lvl);
                    List<SubGraph> leftGraphs = left.graphsOneToEtc;
                    if (!leftGraphs.isEmpty()) {
                        for (SubGraph graph : leftGraphs) {
                            round.addRound(graph.restore(nonMappedValues, defaultLvl));
                        }
                    }
                    right.putLeft(round, right.value);
                });
            }
            return right;
        }

        private RootRound manyToManyRoot(Object value, Map<String, Object> nonMappedValues) {
            return new RootRound(value, nonMappedValues) {
                @Override
                void putLeft(Round left, Object value) {
                    this.lefts.put(left, CollectionsUtils.genericSet(value));
                }

                @Override
                void collectRoundLeft(Map<Object, Object> rightValues, Object roundRootVal, Map<Round, Set<Object>> newLefts) {
                    for (Round left : newLefts.keySet()) {
                        Set<Object> objects = newLefts.get(left).stream()
                                .map(rightValues::get)
                                .collect(Collectors.toSet());
                        if (this.lefts.containsKey(left)) {
                            this.lefts.get(left).addAll(objects);
                            findRound(this.lefts.keySet(), left)
                                    .ifPresent(left::collectRounds);
                        } else {
                            if (this.value.equals(roundRootVal)) {
                                this.lefts.put(left, objects);
                            }
                        }
                    }
                }

                @Override
                boolean hashManyToMany() {
                    return Boolean.TRUE;
                }
            };
        }

        public void roundsManyToMany(RootRound round) {
            Object rightTarget = round.value;
            Map<Round, Set<Object>> lefts = round.lefts;
            Map<String, Object> manyToManyRightFields = new HashMap<>();
            for (Round leftKey : lefts.keySet()) {
                SubGraph leftGraph = this.graphsManyToMany.get(leftKey.type);
                if (Objects.nonNull(leftGraph)) {
                    String rfn = leftGraph.rootFieldName;
                    String cfn = leftGraph.currentFieldName;
                    Class<?> cct = leftGraph.currentCollType;
                    List<SubGraph> children = leftGraph.graphsOneToEtc;
                    Object leftTarget = leftKey.value;
                    Set<Object> leftsValues = lefts.getOrDefault(leftKey, new HashSet<>());
                    Map<String, Object> defaultLeftFieldValues = new HashMap<>();
                    if (!children.isEmpty()) {
                        for (SubGraph child : children) {
                            child.rounds(leftTarget, leftKey, defaultLeftFieldValues);
                        }
                    }
                    if (!defaultLeftFieldValues.isEmpty()) {
                        MapperUtils.mapFields(defaultLeftFieldValues, leftTarget);
                    }
                    if (StringUtils.hasText(cfn) && Objects.nonNull(cct)) {
                        Collection<Object> leftContainer = cast(collFactory(cct));
                        leftContainer.addAll(leftsValues);
                        setFields(leftContainer, leftTarget, cfn);
                    }
                    Collection<Object> rightContainer = cast(
                            manyToManyRightFields.getOrDefault(rfn, collFactory(leftGraph.rootCollType))
                    );
                    if (rightContainer.isEmpty()) {
                        rightContainer.add(leftTarget);
                        manyToManyRightFields.put(rfn, rightContainer);
                    } else {
                        rightContainer.add(leftTarget);
                    }
                }
            }
            if (!manyToManyRightFields.isEmpty()) {
                mapFields(manyToManyRightFields, rightTarget);
            }
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

    public static abstract class RootRound {

        Object value;

        Map<Round, Set<Object>> lefts = new HashMap<>();

        List<Map<String, Object>> nonMappedValues = new ArrayList<>();

        RootRound(Object value, Map<String, Object> nonMappedValues) {
            this.value = value;
            this.nonMappedValues.add(nonMappedValues);
        }

        abstract void putLeft(Round left, Object value);

        abstract void collectRoundLeft(Map<Object, Object> rightValues, Object roundRootVal, Map<Round, Set<Object>> newLefts);

        abstract boolean hashManyToMany();

        public void addAll(List<Map<String, Object>> nonMappedValues) {
            this.nonMappedValues.addAll(nonMappedValues);
        }

        public Optional<Round> findRound(Set<Round> keys, Round newRound) {
            return keys.stream().filter(key -> key.equals(newRound)).findFirst();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RootRound)) return false;
            RootRound rootRound = (RootRound) o;
            return Objects.equals(value, rootRound.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

}
