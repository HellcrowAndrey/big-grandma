package com.github.mapper.sql.key.worlds;

import java.util.Iterator;
import java.util.Objects;

public abstract class KeyWorld implements Iterator<KeyWorld> {

    protected KeyWorld next;
    protected KeyWorld prev;

    public abstract String asString();

    public abstract KeyWorld toFirst();

    @Override
    public boolean hasNext() {
        return Objects.nonNull(this.next);
    }

    @Override
    public KeyWorld next() {
        return this.next;
    }
}
