package com.github.mapper.sql;

public interface SQLSelect {

    String asString();

    static SelectClaim select(String... columns) {
        return SelectDefault.select(columns);
    }

}
