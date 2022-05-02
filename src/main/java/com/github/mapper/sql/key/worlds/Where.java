package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.SortedType;

public interface Where {

    GroupBy groupBy(String... columns);

    OrderBy orderBy(SortedType sortedType, String... columns);

    Limit limit(int number);

    SQLSelect toSelect();

}
