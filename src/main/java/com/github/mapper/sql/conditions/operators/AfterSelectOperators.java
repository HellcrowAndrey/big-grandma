package com.github.mapper.sql.conditions.operators;

import com.github.mapper.sql.key.worlds.Distinct;
import com.github.mapper.sql.key.worlds.From;
import com.github.mapper.sql.key.worlds.Top;

public interface AfterSelectOperators<R> {

    Distinct<R> distinct();

    Top<R> top(int number);

    From<R> from(Class<?> clz);
}
