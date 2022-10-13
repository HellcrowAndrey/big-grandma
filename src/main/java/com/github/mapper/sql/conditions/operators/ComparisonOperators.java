package com.github.mapper.sql.conditions.operators;

public interface ComparisonOperators {

    LogicalOperatorsInHaving eq(Object value);

    LogicalOperatorsInHaving notEq(Object value);

    LogicalOperatorsInHaving greaterThan(Object value);

    LogicalOperatorsInHaving lessThan(Object value);

    LogicalOperatorsInHaving greaterThanOrEq(Object value);

    LogicalOperatorsInHaving lessThanOrEq(Object value);

    LogicalOperatorsInHaving notGreaterThan(Object value);

    LogicalOperatorsInHaving notLessThan(Object value);

}
