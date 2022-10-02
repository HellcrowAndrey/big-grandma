package com.github.mapper.sql.key.worlds;

public interface AfterSelectOperators<R> {

    Distinct<R> distinct();

    Top<R> top(int number);

    From<R> from(Class<?> clz);
}
