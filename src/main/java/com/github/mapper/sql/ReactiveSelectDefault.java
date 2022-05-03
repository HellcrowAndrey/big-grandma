package com.github.mapper.sql;

import com.github.mapper.SqlDependenciesMapper;
import com.github.mapper.sql.key.worlds.FromDefault;
import com.github.mapper.sql.key.worlds.JoinDefault;
import com.github.mapper.sql.key.worlds.KeyWorld;
import com.github.mapper.sql.key.worlds.LeftJoinDefault;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class ReactiveSelectDefault implements ReactiveSelect {

    private final DatabaseClient client;

    public ReactiveSelectDefault(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public <T> Mono<T> one() {
        KeyWorld k = collect();
        String sql = k.asString();
        while (k != null) {
            if (k instanceof FromDefault) {
                System.out.println(((FromDefault) k).getPojoType());
            }
            if (k instanceof JoinDefault) {
                System.out.println(((JoinDefault) k).getFromPojoType());
            }
            if (k instanceof LeftJoinDefault) {
                System.out.println(((LeftJoinDefault) k).getToPojoType());
            }
            k = k.next();
        }
        return this.client.sql(sql)
                .fetch()
                .all()
                .collectList()
                .flatMap(tuples -> SqlDependenciesMapper.defaultMap(null).single(tuples));
    }

    @Override
    public <T> Flux<T> any() {
        KeyWorld k = collect();
        String sql = k.asString();
        return this.client.sql(sql)
                .fetch()
                .all()
                .collectList()
                .flatMapMany(tuples -> SqlDependenciesMapper.defaultMap(null).many(tuples));
    }

    protected abstract KeyWorld collect();

}
