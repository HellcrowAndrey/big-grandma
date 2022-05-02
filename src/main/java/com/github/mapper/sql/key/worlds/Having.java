package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SortedType;

public interface Having {
    OrderBy orderBy(SortedType sortedType, String... columns);
}
