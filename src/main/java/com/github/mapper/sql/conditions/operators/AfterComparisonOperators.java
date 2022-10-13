package com.github.mapper.sql.conditions.operators;

public interface AfterComparisonOperators extends LogicalOperatorsInConditions {

    AfterLogicalOperator and(Object val);

    AfterLogicalOperator or(Object val);

    AfterLogicalOperator any(String query);

    AfterLogicalOperator all(String query);

    AfterNotOperator not();

}
