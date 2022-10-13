package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SQLHaving;
import com.github.mapper.sql.SortedType;

import java.util.function.Function;

public interface GroupBy<R> {

    Having<R> having(Function<SQLHaving, SQLHaving> condition);

    OrderBy<R> orderBy(SortedType sortedType, String... columns);

}
