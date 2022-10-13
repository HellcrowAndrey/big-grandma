package com.github.mapper.sql.conditions.operators;

public interface CommonOperators extends ConditionsTerminalOperator {

    AfterComparisonOperators eq();

    AfterComparisonOperators notEq();

    AfterComparisonOperators greaterThan();

    AfterComparisonOperators lessThan();

    AfterComparisonOperators greaterThanOrEq();

    AfterComparisonOperators lessThanOrEq();

    AfterComparisonOperators notGreaterThan();

    AfterComparisonOperators notLessThan();

    AfterComparisonOperators eq(Object value);

    AfterComparisonOperators notEq(Object value);

    AfterComparisonOperators greaterThan(Object value);

    AfterComparisonOperators lessThan(Object value);

    AfterComparisonOperators greaterThanOrEq(Object value);

    AfterComparisonOperators lessThanOrEq(Object value);

    AfterComparisonOperators notGreaterThan(Object value);

    AfterComparisonOperators notLessThan(Object value);

    AfterBetweenOperatorsInConditions between();

    AfterBetweenOperatorsInConditions between(Object value);

    LogicalOperatorsInConditions isNull();

    LogicalOperatorsInConditions isNotNull();

}
