package com.github.mapper.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

    public static void setField(Field field, Object target, Object value) {
        try {
            Reflections.setField(field, target, value);
        } catch (Throwable e) {
            var message = String.format(
                    "Field type miss match in target: %s, field: [name: %s, type: %s], source: %s",
                    target.getClass().getSimpleName(),
                    field.getName(),
                    field.getType(),
                    value.getClass().getSimpleName()
            );
            log.error(message);
        }
    }

    public static Constructor<?> requiredEmptyConstructor(Class<?> clz) {
        try {
            return clz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    String.format(
                            "Default constructor is not exist in this entity -> %s, in package -> %s",
                            clz.getSimpleName(), clz.getPackageName()
                    )
            );
        }
    }

    public static void setFields(Object source, Object target, String fieldName) {
        if (StringUtils.hasText(fieldName)) {
            Class<?> clz = target.getClass();
            Field field = Reflections.findField(clz, fieldName);
            if (Objects.nonNull(field)) {
                Objects.requireNonNull(field).setAccessible(Boolean.TRUE);
                setField(field, target, source);
            } else {
                log.error("Can't find field -> {} in: {} ", fieldName, target.getClass());
            }
        } else {
            log.error("Not existing field in -> {}, field name [ {} ], source: {}", target.getClass().getSimpleName(), fieldName, source);
        }
    }

    @SuppressWarnings("unchecked")
    public static Collection<Object> getCollections(String fieldName, Class<?> collectionType, Object target) {
        try {
            Field field = Reflections.findField(collectionType, fieldName);
            if (Objects.isNull(field)) {
                throw new IllegalArgumentException("Can't find field " + fieldName + " in " + target);
            }
            field.setAccessible(Boolean.TRUE);
            return (Collection<Object>) field.get(target);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Can't get access field " + fieldName + " in " + target);
        }
    }

    public static void mapFields(Map<String, Object> values, Object target) {
        values.keySet().stream()
                .filter(key -> MapperUtils.isFieldExist(key, target))
                .forEach(key -> {
                    Object source = values.get(key);
                    MapperUtils.setFields(source, target, key);
                });
    }

    public static boolean isFieldExist(String name, Object target) {
        return isFieldExist(name, target.getClass());
    }

    public static boolean isFieldExist(String name, Class<?> target) {
        return StringUtils.hasText(name) && Objects.nonNull(Reflections.findField(target, name));
    }

    public static Collection<?> collFactory(Class<?> collType) {
        if (Map.class.equals(collType)) {
            throw new RuntimeException("Interface Map<?, ?> not exist in interface Collections<?>.");
        }
        try {
            return (Collection<?>) collType.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Class<?> collTypeMapper(Class<?> clz) {
        if (clz.getTypeName().equals(List.class.getTypeName())) {
            return ArrayList.class;
        } else if (clz.getTypeName().equals(Set.class.getTypeName())) {
            return HashSet.class;
        } else if (clz.getTypeName().equals(Collection.class.getTypeName())) {
            return ArrayList.class;
        }
        return clz;
    }

    @SuppressWarnings(value = "unchecked")
    public static Collection<Object> cast(Object val) {
        return (Collection<Object>) val;
    }

    public static boolean isColl(Class<?> coll) {
        var result = false;
        if (Objects.equals(coll, List.class)) {
            result = true;
        } else if (Objects.equals(coll, Set.class)) {
            result = true;
        } else if (Objects.equals(coll, ArrayList.class)) {
            result = true;
        } else if (Objects.equals(coll, LinkedList.class)) {
            result = true;
        } else if (Objects.equals(coll, HashSet.class)) {
            result = true;
        } else if (Objects.equals(coll, LinkedHashSet.class)) {
            result = true;
        } else if (Objects.equals(coll, TreeSet.class)) {
            result = true;
        } else if (Objects.equals(coll, Collection.class)) {
            result = true;
        }
        return result;
    }

    public static String findRequiredField(String prefix, Field field, Map<String, Object> source) {
        String name = field.getName();
        var requiredFieldName = name.contains(prefix) ||
                name.contains(prefix.toLowerCase(Locale.ROOT)) ||
                name.contains(prefix.toUpperCase(Locale.ROOT))
                ? name : String.format("%s%s", prefix, field.getName());
        return source.keySet().stream()
                .filter(key -> requiredFieldName.toUpperCase(Locale.ROOT).equalsIgnoreCase(key))
                .findFirst().orElse(null);
    }

    public static Map<String, Field> fields(Map<String, String> aliases, Class<?> clz) {
        Field[] fields = clz.getDeclaredFields();
        return aliases.keySet().stream().collect(Collectors.toMap(
                Function.identity(),
                alias -> {
                    String fieldName = aliases.get(alias);
                    return Arrays.stream(fields)
                            .filter(field -> field.getName().equals(fieldName))
                            .findFirst().orElseThrow();
                }
        ));
    }

    public static Class<?> findGenericOfColl(Field field) {
        Type[] array = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        return hasGeneric(array) ? (Class<?>) array[0] : null;
    }

    public static Class<?>[] findGenerics(Field field) {
        Type[] array = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        return hasGeneric(array) ? (Class<?>[]) array : null;
    }

    private static boolean hasGeneric(Type[] array) {
        return !(array.length == 0);
    }

}
