package com.github.mapper.sql;

public interface SQLHaving {

    String asString();

    static HavingDefault.ComparisonOperator count(String column) {
        return HavingDefault.newInstance().count(column);
    }

    static HavingDefault.ComparisonOperator min(String column) {
        return HavingDefault.newInstance().min(column);
    }

    static HavingDefault.ComparisonOperator max(String column) {
        return HavingDefault.newInstance().max(column);
    }

    static HavingDefault.ComparisonOperator avg(String column) {
        return HavingDefault.newInstance().avg(column);
    }

    static HavingDefault.ComparisonOperator sum(String column) {
        return HavingDefault.newInstance().sum(column);
    }

}
