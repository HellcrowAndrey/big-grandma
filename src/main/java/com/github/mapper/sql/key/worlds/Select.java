package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.pipeline.SelectPipeline;
import org.springframework.r2dbc.core.DatabaseClient;

public interface Select<R> extends AfterSelectOperators<R> {

    Distinct<R> distinct();

    Top<R> top(int number);

    From<R> from(Class<?> clz);

    static <R> Select<R> select(DatabaseClient databaseClient) {
        return new SelectPipeline<>(databaseClient);
    }

}
