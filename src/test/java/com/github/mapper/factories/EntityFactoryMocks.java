package com.github.mapper.factories;

import com.github.mapper.utils.MapperUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityFactoryMocks {

    static Map<String, Object> sourcesActualParameters() {
        return Map.of("aliasId", 1L, "aliasName", "test-entities");
    }

    static Map<String, Object> sourcesNotActualParameters() {
        return Map.of("ID", 1L, "NAME", "test-entities");
    }

    static Map<String, String> aliases() {
        return Map.of("aliasId", "id", "aliasName", "name");
    }

    static Map<String, Field> fields(Class<?> clz) {
        return MapperUtils.fields(aliases(), clz);
    }

}
