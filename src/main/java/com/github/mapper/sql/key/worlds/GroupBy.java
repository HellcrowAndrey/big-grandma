package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SQLHaving;
import com.github.mapper.sql.SortedType;

public interface GroupBy<R> {

    Having<R> having(SQLHaving condition);

    OrderBy<R> orderBy(SortedType sortedType, String... columns);

}
