package com.github.mapper.sql;

public abstract class KeyWorld {

    protected KeyWorld next;
    protected KeyWorld prev;

    public abstract String asString();

}
