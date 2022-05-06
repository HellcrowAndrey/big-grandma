package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.QueryContext;

import java.util.Objects;

public class LimitDefault extends KeyWorld implements Limit {

    private static final String LIMIT = "limit %d";

    private final String operator;

    private final QueryContext queryContext;

    public LimitDefault(int number, QueryContext queryContext) {
        this.operator = String.format(LIMIT, number);
        this.queryContext = queryContext;
    }

    @Override
    public Offset offset(int number) {
        this.next = new OffsetDefault(number, this.queryContext);
        this.next.prev = this;
        return (OffsetDefault) this.next;
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
