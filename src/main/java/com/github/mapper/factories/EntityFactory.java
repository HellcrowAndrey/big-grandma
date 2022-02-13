package com.github.mapper.factories;

import com.github.mapper.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class EntityFactory {

    private static final Logger log = LoggerFactory.getLogger(EntityFactory.class);

    @SuppressWarnings(value = "unchecked")
    public static <T> T ofEntity(Map<String, Object> sources, Class<T> czl) {
        try {
            Constructor<?> constructor = MapperUtils.requiredEmptyConstructor(czl);
            Object target = constructor.newInstance();
            Field[] fields = czl.getDeclaredFields();
            String prefix = czl.getSimpleName();
            Arrays.stream(fields).forEach(field -> {
                var fieldName = MapperUtils.findRequiredField(prefix, field, sources);
                if (StringUtils.hasText(fieldName)) {
                    field.setAccessible(Boolean.TRUE);
                    MapperUtils.setField(field, target, sources.get(fieldName));
                }
            });
            return (T) target;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("Enter: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
