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
import java.util.Objects;

public class EntityFactory {

    private static final Logger log = LoggerFactory.getLogger(EntityFactory.class);

    @SuppressWarnings(value = "unchecked")
    public static <T> T ofEntity(Map<String, Object> sources, Class<T> clz) {
        try {
            Constructor<?> constructor = MapperUtils.requiredEmptyConstructor(clz);
            Object target = constructor.newInstance();
            Field[] fields = clz.getDeclaredFields();
            String prefix = clz.getSimpleName();
            Arrays.stream(fields).forEach(field -> {
                var fieldName = MapperUtils.findRequiredField(prefix, field, sources);
                if (StringUtils.hasText(fieldName)) {
                    field.setAccessible(Boolean.TRUE);
                    Object obj = sources.get(fieldName);
                    if (Objects.nonNull(obj)) {
                        MapperUtils.setField(field, target, obj);
                    }
                }
            });
            Object isEmpty = constructor.newInstance();
            return isEmpty.equals(target) ? null : (T) target;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("Enter: Can not construct {}", clz);
            throw new RuntimeException(String.format("Can not construct -> %s", clz));
        }
    }

}
