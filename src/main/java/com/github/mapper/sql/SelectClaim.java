package com.github.mapper.sql;

public interface SelectClaim {

    SelectDefault.Distinct distinct();

    SelectDefault.TopDefault top(int number);

    SelectDefault.From from(String tableName);

    SelectDefault.From from(Class<?> clz);

}
