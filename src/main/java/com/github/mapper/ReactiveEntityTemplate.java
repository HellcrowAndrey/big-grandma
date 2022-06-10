package com.github.mapper;

import com.github.mapper.sql.Select;

public interface ReactiveEntityTemplate {

    Select select(String... columns);

    Select select();

}
