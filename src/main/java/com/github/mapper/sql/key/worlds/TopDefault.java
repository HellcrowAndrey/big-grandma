package com.github.mapper.sql.key.worlds;

import java.util.Objects;

public class TopDefault extends KeyWorld implements Top  {

    private static final String TOP = "top %d";

    private final String operator;

    public TopDefault(int number) {
        this.operator = String.format(TOP, number);
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
            return tmp;
        }
        return this;
    }

}
