package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.CollectionsUtils;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
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
                .flatMapMany(this::toTarget);
    }

    @SuppressWarnings(value = "unchecked")
    public <T> Mono<T> single(List<Map<String, Object>> tuples) {
        return (Mono<T>) intermediateState(tuples)
                .flatMapMany(groupBy -> this.toTarget(groupBy).take(1))
                .single();
    }

    private Mono<LinkedHashMap<Object, List<Round>>> intermediateState(List<Map<String, Object>> tuples) {
        return Flux.fromStream(tuples.stream())
                .filter(DependenciesGraph::isTupleEmpty)
                .map(tuple -> root.restoreRootRound(tuple, START_POINT))
                .collect(RootRoundCollector.toListOfRootRounds())
                .flatMapMany(source -> Flux.fromStream(source.stream()))
                .map(root::rounds)
                .collect(Collectors.groupingBy(
                        source -> source.root,
                        LinkedHashMap::new,
                        Collectors.flatMapping(source -> source.nonMappedValues.stream()
                                        .flatMap(tuple -> root.graphOneToEtc.stream()
                                                .map(subGraph -> subGraph.restore(tuple, START_POINT))),
                                RoundCollector.toListOfRounds()
                        )
                ));
    }

    @SuppressWarnings(value = "unchecked")
    private <T> Flux<T> toTarget(LinkedHashMap<?, List<Round>> groupByRoot) {
        return Flux.fromStream(groupByRoot.keySet().stream())
                .map(target -> {
                    Map<String, Object> values = new HashMap<>();
                    List<Round> rounds = groupByRoot.get(target);
                    List<SubGraph> graphs = root.graphOneToEtc;
                    rounds.forEach(round ->
                            graphs.forEach(graph ->
                                    graph.rounds(target, round, values)
                            )
                    );
                    MapperUtils.mapFields(values, target);
                    return (T) target;
                });
    }

    private static boolean isTupleEmpty(Map<String, Object> values) {
        return !values.isEmpty();
    }

    public static class Root {

        Class<?> rootType;

        Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); //optional

        List<SubGraph> graphOneToEtc; //optional

        RelationType type;

        @Deprecated
        public Root(Class<?> rootType, List<SubGraph> graphOneToEtc) {
            this.rootType = Objects.requireNonNull(rootType);
            this.graphOneToEtc = Objects.requireNonNullElse(graphOneToEtc, new ArrayList<>());
            this.type = RelationType.def;
        }

        private Root(RootBuilder b) {
            this.rootType = b.rootType;
            this.graphOneToEtc = b.graphOneToEtc;
            this.type = b.type;
        }

        private Root(RootManyToManyBuilder b) {
            this.rootType = b.rootType;
            this.graphsManyToMany = b.graphsManyToMany;
            this.graphOneToEtc = b.graphs;
            this.type = b.type;
        }

        public static class RootBuilder {

            Class<?> rootType;

            List<SubGraph> graphOneToEtc = new ArrayList<>(); //optional

            RelationType type;

            public RootBuilder rootType(Class<?> rootType) {
                this.rootType = Objects.requireNonNull(rootType);
                return this;
            }

            public RootBuilder graphOneToEtc(SubGraph graph) {
                this.graphOneToEtc.add(graph);
                return this;
            }

            public Root build() {
                this.type = RelationType.def;
                return new Root(this);
            }
        }

        public static class RootManyToManyBuilder {

            Class<?> rootType;

            Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); //optional

            List<SubGraph> graphs = new ArrayList<>(); //optional

            RelationType type;

            public RootManyToManyBuilder rootType(Class<?> rootType) {
                this.rootType = Objects.requireNonNull(rootType);
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

            public Root build() {
                this.type = RelationType.manyToMany;
                return new Root(this);
            }
        }

        public RootRound restoreRootRound(Map<String, Object> nonMappedValues, int lvl) {
            switch (this.type) {
                case def:
                    return restoreByDef(nonMappedValues);
                case manyToMany:
                    return restoreWithManyToMany(nonMappedValues, lvl);
                default:
                    throw new IllegalArgumentException("Unsupported Relation type");
            }
        }

        private RootRound restoreByDef(Map<String, Object> nonMappedValues) {
            return new RootRound(EntityFactory.ofEntity(nonMappedValues, this.rootType), nonMappedValues) {
                @Override
                void putLeft(Round left, RootRound value) {
                    throw new UnsupportedOperationException();
                }

                @Override
                void collectRoundLeft(Map<RootRound, RootRound> rightValues, Object roundRootVal, Map<Round, Set<RootRound>> newLefts) {
                    throw new UnsupportedOperationException();
                }

                @Override
                boolean hashManyToMany() {
                    return Boolean.FALSE;
                }
            };
        }

        private RootRound restoreWithManyToMany(Map<String, Object> nonMappedValues, int lvl) {
            Object value = EntityFactory.ofEntity(nonMappedValues, this.rootType);
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
                    right.putLeft(round, right);
                });
            }
            return right;
        }

        private RootRound manyToManyRoot(Object value, Map<String, Object> nonMappedValues) {
            return new RootRound(value, nonMappedValues) {
                @Override
                void putLeft(Round left, RootRound value) {
                    this.lefts.put(left, CollectionsUtils.genericSet(value));
                }

                @Override
                void collectRoundLeft(Map<RootRound, RootRound> rightValues, Object roundRootVal, Map<Round, Set<RootRound>> newLefts) {
                    for (Round left : newLefts.keySet()) {
                        Set<RootRound> objects = newLefts.get(left).stream()
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
                    return true;
                }
            };
        }

        public RootState rounds(RootRound round) {
            switch (this.type) {
                case def:
                    return roundsDefault(round);
                case manyToMany:
                    return roundsManyToMany(round);
                default:
                    throw new IllegalArgumentException("Unsupported Relation type");
            }
        }

        private RootState roundsDefault(RootRound round) {
            return new RootState(round.value, round.nonMappedValues);
        }

        // TODO: 24.03.22 add cash to this rounds ??
        private RootState roundsManyToMany(RootRound round) {
            Object rightTarget = round.value;
            Map<Round, Set<RootRound>> lefts = round.lefts;
            Map<String, Object> manyToManyRightFields = new HashMap<>();
            for (Round leftKey : lefts.keySet()) {
                SubGraph leftGraph = this.graphsManyToMany.get(leftKey.type);
                if (Objects.nonNull(leftGraph)) {
                    String rfn = leftGraph.rootFieldName;
                    String cfn = leftGraph.currentFieldName;
                    Class<?> cct = leftGraph.currentCollType;
                    List<SubGraph> children = leftGraph.graphsOneToEtc;
                    Object leftTarget = leftKey.value;
                    Set<RootRound> leftsRounds = lefts.getOrDefault(leftKey, new HashSet<>());
                    Set<Object> leftsValues = leftsRounds.stream()
                            .map(r -> r.value)
                            .collect(Collectors.toSet());
                    Map<String, Object> defaultLeftFieldValues = new HashMap<>();
                    if (!children.isEmpty()) {
                        for (SubGraph g : children) {
                            g.rounds(leftTarget, leftKey, defaultLeftFieldValues);
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
            return new RootState(rightTarget, round.nonMappedValues);
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

        Map<Round, Set<RootRound>> lefts = new HashMap<>();

        List<Map<String, Object>> nonMappedValues = new ArrayList<>();

        RootRound(Object value, Map<String, Object> nonMappedValues) {
            this.value = value;
            this.nonMappedValues.add(nonMappedValues);
        }

        abstract void putLeft(Round left, RootRound value);

        abstract void collectRoundLeft(Map<RootRound, RootRound> rightValues, Object roundRootVal, Map<Round, Set<RootRound>> newLefts);

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

    private static class RootState {

        Object root;

        List<Map<String, Object>> nonMappedValues;

        public RootState(Object root, List<Map<String, Object>> nonMappedValues) {
            this.root = root;
            this.nonMappedValues = nonMappedValues;
        }

    }

}
