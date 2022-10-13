package com.github.mapper.sql.conditions.operators;

import com.github.mapper.sql.conditions.HavingCondition;
import com.github.mapper.sql.conditions.TerminalHaving;

public interface LogicalOperatorsInHaving extends TerminalHaving {

    HavingCondition and();

    HavingCondition or();

}
