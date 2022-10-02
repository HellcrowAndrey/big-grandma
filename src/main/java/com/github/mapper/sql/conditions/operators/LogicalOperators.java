package com.github.mapper.sql.conditions.operators;

import com.github.mapper.sql.conditions.HavingCondition;

public interface LogicalOperators extends TerminalHaving {

    HavingCondition and();

    HavingCondition or();

}
