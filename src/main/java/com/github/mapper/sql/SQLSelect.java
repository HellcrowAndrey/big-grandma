package com.github.mapper.sql;

import com.github.mapper.sql.key.worlds.SelectDefault;

public interface SQLSelect {
    // TODO: 02.05.22 added proxy to this
    String asString();

    static Select select(String... columns) {
        return SelectDefault.select(columns);
    }

}
