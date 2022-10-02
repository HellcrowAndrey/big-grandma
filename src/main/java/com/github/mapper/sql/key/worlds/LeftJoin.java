package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ColumnName;
import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLCondition;
import com.github.mapper.sql.SQLSelect;

public interface LeftJoin<R> {

    LeftJoin<R> leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol);

    LeftJoin<R> leftJoin(String tableName, String leftCol, String rightCol);

    LeftJoin<R> leftJoin(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable);

    Join<R> join(String tableName, ColumnName leftCol, ColumnName rightCol);

    Join<R> join(String tableName, String leftCol, String rightCol);

    Join<R> join(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable);

    Where<R> where(SQLCondition condition);

    SQLSelect toSelect();

    ReactiveSelect<R> toReactiveSelect();

}
