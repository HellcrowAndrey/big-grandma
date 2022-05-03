package com.github.mapper.sql.key.worlds;

public abstract class KeyWorld {

    protected KeyWorld next;
    protected KeyWorld prev;

    public abstract String asString();

    public abstract KeyWorld toFirst();

}
