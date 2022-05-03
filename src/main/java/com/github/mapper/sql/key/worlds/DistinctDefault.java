package com.github.mapper.sql.key.worlds;

import java.util.Objects;

public class DistinctDefault extends KeyWorld implements Distinct {

    private static final String DISTINCT = "distinct";

    private final String columns;

    public DistinctDefault() {
        this.columns = DISTINCT;
    }

    @Override
    public From from(String tableName) {
        this.next = new FromDefault(tableName);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

    @Override
    public From from(Class<?> clz) {
        this.next = new FromDefault(clz);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

    @Override
    public String asString() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.asString();
        }
        return this.columns;
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
