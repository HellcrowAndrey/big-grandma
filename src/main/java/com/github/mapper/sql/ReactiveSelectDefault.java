package com.github.mapper.sql;

import com.github.mapper.SqlDependenciesMapper;
import com.github.mapper.factories.DependenciesGraphFactory;
import com.github.mapper.graph.DependenciesGraph;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class ReactiveSelectDefault<T> implements ReactiveSelect<T> {
    public ReactiveSelectDefault() {
    }

    @Override
    public Mono<T> one() {
        SQLSelect it = query();
        String sql = it.asString();
        QueryContext context = context();
        DatabaseClient client = context.getClient();
        DependenciesGraph graph = DependenciesGraphFactory.dependenciesGraph(context);
        return client.sql(sql)
                .fetch()
                .all()
                .collectList()
                .flatMap(tuples -> SqlDependenciesMapper.defaultMap(graph).single(tuples));
    }

    @Override
    public Flux<T> any() {
        SQLSelect it = query();
        String sql = it.asString();
        QueryContext context = context();
        DatabaseClient client = context.getClient();
        DependenciesGraph graph = DependenciesGraphFactory.dependenciesGraph(context);
        return client.sql(sql)
                .fetch()
                .all()
                .collectList()
                .flatMapMany(tuples -> SqlDependenciesMapper.defaultMap(graph).many(tuples));
    }

    protected abstract SQLSelect query();

    protected abstract QueryContext context();

}
