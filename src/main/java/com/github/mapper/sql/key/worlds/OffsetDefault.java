package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.QueryContext;
import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.ReactiveSelectDefault;
import com.github.mapper.sql.SQLSelect;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class OffsetDefault extends KeyWorld implements Offset {

    private static final String OFFSET = "offset %d";

    private final String operator;

    private final QueryContext queryContext;

    public OffsetDefault(Integer number, QueryContext queryContext) {
        this.operator = String.format(OFFSET, number);
        this.queryContext = queryContext;
    }

    @Override
    public SQLSelect toSelect() {
        return this::asString;
    }

    @Override
    public ReactiveSelect toReactiveSelect() {
        return new ReactiveSelectDefault() {
            @Override
            protected KeyWorld collect() {
                return OffsetDefault.this.toFirst();
            }

            @Override
            protected QueryContext context() {
                return OffsetDefault.this.queryContext;
            }
        };
    }

    @Override
    public String asString() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.asString();
        }
        return this.operator;
    }

    @Override
    public String getText() {
        return this.asString();
    }

    @Override
    public KeyWorld toFirst() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.toFirst();
        }
        return this;
    }

}
