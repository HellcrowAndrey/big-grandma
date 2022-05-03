package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLSelect;
import org.springframework.r2dbc.core.DatabaseClient;

public interface Offset {
    SQLSelect toSelect();
    ReactiveSelect toReactiveSelect(DatabaseClient client);
}
