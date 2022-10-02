package com.github.mapper.sql.conditions.operators;

public interface ComparisonOperators {

    LogicalOperators eq(Object value);

    LogicalOperators notEq(Object value);

    LogicalOperators greaterThan(Object value);

    LogicalOperators lessThan(Object value);

    LogicalOperators greaterThanOrEq(Object value);

    LogicalOperators lessThanOrEq(Object value);

    LogicalOperators notGreaterThan(Object value);

    LogicalOperators notLessThan(Object value);

    AfterBetweenOperators between(Object value);

}
