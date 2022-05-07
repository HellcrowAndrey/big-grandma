package com.github.mapper;

import com.github.mapper.sql.Select;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ReactiveEntityTemplate {

    <T> Flux<T> selectAllWhere(String query, Object... params);

    <T> Flux<T> selectAllWhereOrderBy(String query, String orderBy, Object... params);

    <T> Mono<T> selectOneWhere(String query, Object... params);

    <T> Flux<T> selectAllWhere(String query, Map<String, Object> params);

    <T> Flux<T> selectAllWhereOrderBy(String query, String orderBy, Map<String, Object> params);

    <T> Mono<T> selectOneWhere(String query, Map<String, Object> params);

    Select select(String... columns);

    Select select();

    static ReactiveEntityTemplate
    defaultReactiveEntityTemplate(DatabaseClient client, SqlDependenciesMapper sqlDependenciesMapper) {
        return new DefaultReactiveEntityTemplate(client, sqlDependenciesMapper);
    }

}
