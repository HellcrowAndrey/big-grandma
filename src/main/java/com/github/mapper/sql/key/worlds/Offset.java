package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLSelect;

public interface Offset {
    SQLSelect toSelect();
    ReactiveSelect toReactiveSelect();
}
