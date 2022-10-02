package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SortedType;

public interface Having<R> {
    OrderBy<R> orderBy(SortedType sortedType, String... columns);
}
