package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.SortedType;
import org.springframework.r2dbc.core.DatabaseClient;

public interface OrderBy {

    OrderBy orderBy(SortedType sortedType, String... columns);
    OrderBy orderBy(String column, SortedType type);
    Limit limit(int number);
    SQLSelect toSelect();
    ReactiveSelect toReactiveSelect(DatabaseClient client);
}
