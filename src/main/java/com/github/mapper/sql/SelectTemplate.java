package com.github.mapper.sql;

import com.github.mapper.sql.key.worlds.Distinct;
import com.github.mapper.sql.key.worlds.From;
import com.github.mapper.sql.key.worlds.TopDefault;

public interface SelectTemplate {

    Distinct distinct();

    TopDefault top(int number);

    From from(String tableName);

    From from(Class<?> clz);

}
