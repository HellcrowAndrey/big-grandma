package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.MapperUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class DependenciesGraph {

    private final Root root;

    public DependenciesGraph(Root root) {
        this.root = root;
    }

    public <T> Flux<T> many(List<Map<String, Object>> tuples) {
        return Flux.fromStream(tuples.stream())
                .filter(DependenciesGraph::isTupleEmpty)
                .collect(Collectors.groupingBy(
                        source -> EntityFactory.ofEntity(source, root.rootType),
                        LinkedHashMap::new,
                        Collectors.flatMapping(tuple -> root.graphs.stream()
                                        .map(subGraph -> subGraph.restore(tuple, 0))
                                , RoundCollector.toListOfRounds()
                        )
                )).flatMapMany(this::toTarget);
    }

    @SuppressWarnings(value = "unchecked")
    public <T> Mono<T> single(List<Map<String, Object>> tuples) {
        return (Mono<T>) Flux.fromStream(tuples.stream())
                .filter(DependenciesGraph::isTupleEmpty)
                .collect(Collectors.groupingBy(
                        source -> EntityFactory.ofEntity(source, root.rootType),
                        LinkedHashMap::new,
                        Collectors.flatMapping(tuple -> root.graphs.stream()
                                        .map(subGraph -> subGraph.restore(tuple, 0))
                                , RoundCollector.toListOfRounds()
                        )
                )).flatMapMany(groupBy -> this.toTarget(groupBy).take(1))
                .single();
    }

    @SuppressWarnings(value = "unchecked")
    private <T> Flux<T> toTarget(LinkedHashMap<?, List<GeneralRounds>> groupByRoot) {
        return Flux.fromStream(groupByRoot.keySet().stream())
                .map(target -> {
                    Map<String, Object> values = new HashMap<>();
                    List<GeneralRounds> rounds = groupByRoot.get(target);
                    List<SubGraph> graphs = root.graphs;
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

        List<SubGraph> graphs; //optional

        public Root(Class<?> rootType, List<SubGraph> graphs) {
            this.rootType = Objects.requireNonNull(rootType);
            this.graphs = Objects.requireNonNullElse(graphs, new ArrayList<>());
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

}
