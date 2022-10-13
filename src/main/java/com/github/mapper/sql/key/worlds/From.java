package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;

import java.util.function.Function;

public interface From<R> {

    Where<R> where(Function<SQLCondition, SQLCondition> condition);

    OrderBy<R> orderBy(SortedType sortedType, String... columns);

    Limit<R> limit(int number);

    Join<R> join(String tableName, ColumnName leftCol, ColumnName rightCol);

    Join<R> join(String tableName, String leftCol, String rightCol);

    Join<R> join(Class<?> joinTable, String parent, String join);

    Join<R> join(Class<?> toTable, Class<?> fromTable, String to, String from);

    LeftJoin<R> leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol);

    LeftJoin<R> leftJoin(String tableName, String leftCol, String rightCol);

    LeftJoin<R> leftJoin(Class<?> fromTable, String to, String from);

    LeftJoin<R> leftJoin(Class<?> toTable, Class<?> fromTable, String to, String from);

    SQLSelect toSelect();

    ReactiveSelect<R> toReactiveSelect();

}
