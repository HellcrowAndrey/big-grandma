package com.github.mapper.sql.conditions.operators;

public interface AfterNotOperator extends ColumnOperator {

    AfterLogicalOperator in(Object... values);

    AfterLogicalOperator like(String pattern);

    AfterBetweenOperatorsInConditions between(Object val);

}
