package com.github.mapper.sql.key.worlds;

import java.util.Objects;

public class LimitDefault extends KeyWorld implements Limit {

    private static final String LIMIT = "limit %d";

    private final String operator;

    public LimitDefault(int number) {
        this.operator = String.format(LIMIT, number);
    }

    @Override
    public Offset offset(int number) {
        this.next = new OffsetDefault(number);
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
    public KeyWorld toFirst() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.toFirst();
        }
        return this;
    }

}
