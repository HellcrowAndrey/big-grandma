package com.github.mapper.sql;

import com.github.mapper.SqlDependenciesMapper;
import com.github.mapper.factories.DependenciesGraphFactory;
import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.sql.key.worlds.KeyWorld;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class ReactiveSelectDefault implements ReactiveSelect {
    public ReactiveSelectDefault() {
    }

    @Override
    public <T> Mono<T> one() {
        KeyWorld it = collect();
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
    public <T> Flux<T> any() {
        KeyWorld it = collect();
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

    protected abstract KeyWorld collect();

    protected abstract QueryContext context();

}
