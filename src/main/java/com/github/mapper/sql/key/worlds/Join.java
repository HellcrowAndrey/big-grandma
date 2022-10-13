package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;

import java.util.function.Function;

public interface Join<R> {

    Join<R> join(String tableName, ColumnName leftCol, ColumnName rightCol);

    Join<R> join(String tableName, String leftCol, String rightCol);

    Join<R> join(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable);

    LeftJoin<R> leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol);

    LeftJoin<R> leftJoin(String tableName, String leftCol, String rightCol);

    LeftJoin<R> leftJoin(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable);

    Where<R> where(Function<SQLCondition, SQLCondition> condition);

    SQLSelect toSelect();

    ReactiveSelect<R> toReactiveSelect();

}
