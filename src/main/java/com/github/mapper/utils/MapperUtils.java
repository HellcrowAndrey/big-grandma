package com.github.mapper.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.util.Pair;
import org.springframework.util.StringUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtils {

    private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

    private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
    static {
        WRAPPER_TYPE_MAP = new HashMap<>(16);
        WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        WRAPPER_TYPE_MAP.put(Character.class, char.class);
        WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TYPE_MAP.put(Double.class, double.class);
        WRAPPER_TYPE_MAP.put(Float.class, float.class);
        WRAPPER_TYPE_MAP.put(Long.class, long.class);
        WRAPPER_TYPE_MAP.put(Short.class, short.class);
    }

    public static boolean isPrimitiveOrWrapper(Class<?> type) {
        return WRAPPER_TYPE_MAP.containsKey(type) || WRAPPER_TYPE_MAP.containsValue(type) || type.equals(String.class);
    }

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
    public static Collection<Object> castToCollection(Object val) {
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
        if (isColl(field.getType())) {
            Type[] array = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            return hasGeneric(array) ? (Class<?>) array[0] : null;
        }
        return null;
    }

    public static Class<?>[] findGenerics(Field field) {
        Type[] array = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        return hasGeneric(array) ? (Class<?>[]) array : null;
    }

    public static Class<?> genericOfColl(Field field) {
        Type genericSuperClass = field.getType().getGenericSuperclass();

        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                if (Objects.isNull(genericSuperClass)) {
                    return null;
                }
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        return  (Class<?>) parametrizedType.getActualTypeArguments()[0];
    }

    private static boolean hasGeneric(Type[] array) {
        return !(array.length == 0);
    }

    public static <T> T sameOrDefault(T obj, T def) {
        return Objects.isNull(obj) ? def : obj;
    }

    public static String findTableName(Class<?> clz) {
        if (!clz.isAnnotationPresent(Table.class)) {
            return clz.getSimpleName().toLowerCase(Locale.ROOT);
        }
        return clz.getAnnotation(Table.class).value();
    }

    public static Map<String, Pair<String, Field>> fieldNames(Class<?> type) {
        String tableName = MapperUtils.findTableName(type);
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> MapperUtils.isPrimitiveOrWrapper(field.getType()))
                .collect(Collectors.toMap(k -> tableName + "." + k.getName(), v -> Pair.of(tableName + "_" + v.getName(), v) ));
    }

    public static Map<String, String> fieldAsAliases(Class<?> type) {
        String tableName = MapperUtils.findTableName(type);
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> MapperUtils.isPrimitiveOrWrapper(field.getType()))
                .collect(Collectors.toMap(Field::getName, v -> tableName + "_" + v.getName()));
    }

    public static List<Field> fieldAllFields(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> MapperUtils.isPrimitiveOrWrapper(field.getType()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static List<Field> fieldFields(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
