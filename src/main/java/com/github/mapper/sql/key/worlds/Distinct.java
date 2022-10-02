package com.github.mapper.sql.key.worlds;

public interface Distinct<R> {

    From<R> from(Class<?> clz);

}
