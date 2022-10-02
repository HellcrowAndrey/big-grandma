package com.github.mapper.sql.key.worlds;

public interface Limit<R> {

    Offset<R> offset(int number);

}
