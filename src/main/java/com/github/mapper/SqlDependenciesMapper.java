package com.github.mapper;

import com.github.mapper.graph.DependenciesGraph;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface SqlDependenciesMapper {

    <T> Flux<T> many(List<Map<String, Object>> tuples);

    <T> Mono<T> single(List<Map<String, Object>> tuples);

    static SqlDependenciesMapper defaultMap(DependenciesGraph graph) {
        return new DefaultSqlDependenciesMapper(graph);
    }

}
