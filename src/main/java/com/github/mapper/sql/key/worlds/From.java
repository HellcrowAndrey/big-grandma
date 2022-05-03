package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;
import org.springframework.r2dbc.core.DatabaseClient;

public interface From {

    Where where(SQLCondition condition);

    OrderBy orderBy(SortedType sortedType, String... columns);

    Limit limit(int number);

    Join join(String tableName, ColumnName leftCol, ColumnName rightCol);

    Join join(String tableName, String leftCol, String rightCol);

    Join join(Class<?> fromTable, String to, String from);

    Join join(Class<?> toTable, Class<?> fromTable, String to, String from);

    LeftJoin leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol);

    LeftJoin leftJoin(String tableName, String leftCol, String rightCol);

    LeftJoin leftJoin(Class<?> fromTable, String to, String from);

    LeftJoin leftJoin(Class<?> toTable, Class<?> fromTable, String to, String from);

    SQLSelect toSelect();

    ReactiveSelect toReactiveSelect(DatabaseClient client);

}
