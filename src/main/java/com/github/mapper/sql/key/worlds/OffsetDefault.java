package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.ReactiveSelectDefault;
import com.github.mapper.sql.SQLSelect;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class OffsetDefault extends KeyWorld implements Offset {

    private static final String OFFSET = "offset %d";

    private final String operator;

    public OffsetDefault(Integer number) {
        this.operator = String.format(OFFSET, number);
    }

    @Override
    public SQLSelect toSelect() {
        return this::asString;
    }

    @Override
    public ReactiveSelect toReactiveSelect(DatabaseClient client) {
        return new ReactiveSelectDefault(client) {
            @Override
            protected KeyWorld collect() {
                return OffsetDefault.this.toFirst();
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
    public KeyWorld toFirst() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.toFirst();
        }
        return this;
    }

}
