package com.github.mapper;

import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class DefaultReactiveEntityTemplate implements ReactiveEntityTemplate {

    private final DatabaseClient client;

    private final SqlDependenciesMapper sqlDependenciesMapper;

    public DefaultReactiveEntityTemplate(DatabaseClient client, SqlDependenciesMapper sqlDependenciesMapper) {
        this.client = client;
        this.sqlDependenciesMapper = sqlDependenciesMapper;
    }

    @Override
    public <T> Flux<T> selectAllWhere(String query, Object... params) {
        return sqlWithParametersByIndex(query, params)
                .fetch().all()
                .collectList()
                .flatMapMany(this.sqlDependenciesMapper::many);
    }

    @Override
    public <T> Flux<T> selectAllWhereOrderBy(String query, String orderBy, Object... params) {
        return sqlWithParametersByIndex(query, params)
                .fetch().all()
                .bufferUntilChanged(column -> column.get(orderBy))
                .flatMap(this.sqlDependenciesMapper::many);
    }

    @Override
    public <T> Mono<T> selectOneWhere(String query, Object... params) {
        return sqlWithParametersByIndex(query, params)
                .fetch().all()
                .collectList()
                .flatMap(this.sqlDependenciesMapper::single);
    }

    @Override
    public <T> Flux<T> selectAllWhere(String query, Map<String, Object> params) {
        return sqlWithParametersByName(query, params)
                .fetch().all()
                .collectList()
                .flatMapMany(this.sqlDependenciesMapper::many);
    }

    @Override
    public <T> Flux<T> selectAllWhereOrderBy(String query, String orderBy, Map<String, Object> params) {
        return sqlWithParametersByName(query, params)
                .fetch().all()
                .bufferUntilChanged(column -> column.get(orderBy))
                .flatMap(this.sqlDependenciesMapper::many);
    }

    @Override
    public <T> Mono<T> selectOneWhere(String query, Map<String, Object> params) {
        return sqlWithParametersByName(query, params)
                .fetch().all()
                .collectList()
                .flatMap(this.sqlDependenciesMapper::single);
    }

    private DatabaseClient.GenericExecuteSpec sqlWithParametersByIndex(String query, Object... params) {
        DatabaseClient.GenericExecuteSpec executeSpec = this.client.sql(query);
        for (int i = 0; i < params.length; i++) {
            executeSpec.bind(i, params[i]);
        }
        return executeSpec;
    }

    private DatabaseClient.GenericExecuteSpec sqlWithParametersByName(String query, Map<String, Object> params) {
        DatabaseClient.GenericExecuteSpec executeSpec = this.client.sql(query);
        params.keySet().forEach(key -> executeSpec.bind(key, params.get(key)));
        return executeSpec;
    }

}
