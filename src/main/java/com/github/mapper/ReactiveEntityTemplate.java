package com.github.mapper;

import com.github.mapper.sql.key.worlds.Select;

public interface ReactiveEntityTemplate {

    <T> Select<T> select();

}
