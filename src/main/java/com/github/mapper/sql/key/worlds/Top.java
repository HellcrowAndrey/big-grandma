package com.github.mapper.sql.key.worlds;

public interface Top {
    From from(String tableName);
    From from(Class<?> clz);
}
