package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SQLHaving;
import com.github.mapper.sql.SortedType;

public interface GroupBy {

    Having having(SQLHaving condition);
    OrderBy orderBy(SortedType sortedType, String... columns);

}
