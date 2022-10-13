package com.github.mapper.sql.conditions;

import com.github.mapper.sql.conditions.operators.AfterNotOperator;
import com.github.mapper.sql.conditions.operators.LogicalOperatorsInConditions;

public interface BaseCondition {

    AfterNotOperator not();

    LogicalOperatorsInConditions exists(String query);

}
