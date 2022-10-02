package com.github.mapper.sql;

import com.github.mapper.sql.key.worlds.Select;
import org.springframework.r2dbc.core.DatabaseClient;

public interface SQLSelect {

    String asString();

    static <T> Select<T> select(DatabaseClient client) {
        return Select.select(client);
    }

}
