package com.github.mapper.sql;

import com.github.mapper.sql.key.worlds.SelectDefault;

public interface SQLSelect {

    String asString();

    static Select select(String... columns) {
        return SelectDefault.select(columns);
    }

}
