package com.github.mapper;

import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.Select;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultReactiveEntityTemplate implements ReactiveEntityTemplate {

    private final DatabaseClient client;

    public DefaultReactiveEntityTemplate(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Select select(String... columns) {
        return SQLSelect.select(columns);
    }

    @Override
    public Select select() {
        return SQLSelect.select(this.client);
    }

}
