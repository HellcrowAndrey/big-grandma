package com.github.mapper;

import com.github.mapper.graph.DependenciesGraph;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class DefaultSqlDependenciesMapper implements SqlDependenciesMapper {

    private final DependenciesGraph graph;

    public DefaultSqlDependenciesMapper(DependenciesGraph graph) {
        this.graph = graph;
    }

    @Override
    public <T> Flux<T> many(List<Map<String, Object>> tuples) {
        return this.graph.many(tuples);
    }

    @Override
    public <T> Mono<T> single(List<Map<String, Object>> tuples) {
        return this.graph.single(tuples);
    }

}
