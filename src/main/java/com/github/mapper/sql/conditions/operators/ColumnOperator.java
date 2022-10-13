package com.github.mapper.sql.conditions.operators;

public interface ColumnOperator extends ConditionsTerminalOperator {

    CommonOperators column(Class<?> entity, String column);

}
