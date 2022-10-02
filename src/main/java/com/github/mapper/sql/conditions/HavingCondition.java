package com.github.mapper.sql.conditions;

import com.github.mapper.sql.conditions.operators.ComparisonOperators;

public interface HavingCondition {

    String asString();

    ComparisonOperators count(String column);

    ComparisonOperators min(String column);

    ComparisonOperators max(String column);

    ComparisonOperators avg(String column);

    ComparisonOperators sum(String column);



}
