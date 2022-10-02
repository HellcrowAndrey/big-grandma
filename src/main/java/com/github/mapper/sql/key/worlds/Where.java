package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.SortedType;

public interface Where<R> {

    GroupBy<R> groupBy(String... columns);

    OrderBy<R> orderBy(SortedType sortedType, String... columns);

    Limit<R> limit(int number);

    SQLSelect toSelect();

    ReactiveSelect<R> toReactiveSelect();

}
