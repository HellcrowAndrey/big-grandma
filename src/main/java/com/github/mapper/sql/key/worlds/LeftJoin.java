package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ColumnName;
import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLCondition;
import com.github.mapper.sql.SQLSelect;

public interface LeftJoin {

    LeftJoin leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol);

    LeftJoin leftJoin(String tableName, String leftCol, String rightCol);

    LeftJoin leftJoin(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable);

    Join join(String tableName, ColumnName leftCol, ColumnName rightCol);

    Join join(String tableName, String leftCol, String rightCol);

    Join join(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable);

    Where where(SQLCondition condition);

    SQLSelect toSelect();

    ReactiveSelect toReactiveSelect();

}
