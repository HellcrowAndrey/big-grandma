package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.SortedType;

public interface OrderBy<R> {

    OrderBy<R> orderBy(SortedType sortedType, String... columns);

    OrderBy<R> orderBy(String column, SortedType type);

    Limit<R> limit(int number);

    SQLSelect toSelect();

    ReactiveSelect<R> toReactiveSelect();
}
