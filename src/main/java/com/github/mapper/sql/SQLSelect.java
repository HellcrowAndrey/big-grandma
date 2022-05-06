package com.github.mapper.sql;

import com.github.mapper.sql.key.worlds.SelectDefault;
import org.springframework.r2dbc.core.DatabaseClient;

public interface SQLSelect {

    String asString();

    static Select select(DatabaseClient client) {
        return SelectDefault.select(client);
    }

    static Select select(String... columns) {
        return SelectDefault.select(columns);
    }

}
