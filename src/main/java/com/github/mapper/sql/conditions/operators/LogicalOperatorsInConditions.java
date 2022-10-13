package com.github.mapper.sql.conditions.operators;

public interface LogicalOperatorsInConditions extends ConditionsTerminalOperator {

    AfterLogicalOperator and();

    AfterLogicalOperator or();

}
