package com.github.mapper.sql;

public interface SQLSelect {
    // TODO: 02.05.22 added proxy to this
    String asString();

    static SelectTemplate select(String... columns) {
        return SelectDefault.select(columns);
    }

}
