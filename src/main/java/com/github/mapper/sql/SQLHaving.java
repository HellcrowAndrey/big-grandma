package com.github.mapper.sql;

import com.github.mapper.sql.conditions.operators.ComparisonOperators;
import com.github.mapper.sql.pipeline.HavingPipeline;

public interface SQLHaving {

    String asString();

    static ComparisonOperators count(String column) {
        return new HavingPipeline().count(column);
    }

    static ComparisonOperators min(String column) {
        return new HavingPipeline().min(column);
    }

    static ComparisonOperators max(String column) {
        return new HavingPipeline().max(column);
    }

    static ComparisonOperators avg(String column) {
        return new HavingPipeline().avg(column);
    }

    static ComparisonOperators sum(String column) {
        return new HavingPipeline().sum(column);
    }

}
