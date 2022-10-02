package com.github.mapper;

import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.key.worlds.Select;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultReactiveEntityTemplate implements ReactiveEntityTemplate {

    private final DatabaseClient client;

    public DefaultReactiveEntityTemplate(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public <T> Select<T> select() {
        return SQLSelect.select(this.client);
    }

}
