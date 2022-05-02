package com.github.mapper.sql.key.worlds;

public interface Distinct {

    From from(String tableName);
    From from(Class<?> clz);

}
