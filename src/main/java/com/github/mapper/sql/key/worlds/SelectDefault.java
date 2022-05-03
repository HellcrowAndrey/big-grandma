package com.github.mapper.sql.key.worlds;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.Select;
import com.github.mapper.utils.MapperUtils;
import org.springframework.data.util.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class SelectDefault extends KeyWorld implements Select {

    private static final String SELECT = "select";

    private final String[] columns;

    private SelectDefault(String[] columns) {
        this.columns = columns;
    }

    public static Select select(String... columns) {
        return new SelectDefault(columns);
    }

    @Override
    public String asString() {
        StringBuilder start = new StringBuilder(SELECT)
                .append(StringSqlUtils.SPACE);
        KeyWorld iter = this.next;
        if (iter instanceof DistinctDefault || iter instanceof TopDefault) {
            start.append(iter.asString())
                    .append(StringSqlUtils.SPACE);
            iter = iter.next;
        }
        Map<String, Pair<String, Field>> fields = new LinkedHashMap<>();
        StringBuilder end = new StringBuilder();
        while (iter != null) {
            if (this.columns.length == 0) {
                if (iter instanceof FromDefault) {
                    Class<?> type = ((FromDefault) iter).getPojoType();
                    if (Objects.nonNull(type)) {
                        fields.putAll(fieldNames(type));
                    }
                }
                if (iter instanceof JoinDefault) {
                    Class<?> toPojo = ((JoinDefault) iter).getToPojoType();
                    Class<?> fromPojo = ((JoinDefault) iter).getFromPojoType();
                    if (Objects.nonNull(toPojo)) {
                        fields.putAll(fieldNames(toPojo));
                    }
                    if (Objects.nonNull(fromPojo)) {
                        fields.putAll(fieldNames(fromPojo));
                    }
                }
                if (iter instanceof LeftJoinDefault) {
                    Class<?> toPojo = ((LeftJoinDefault) iter).getToPojoType();
                    Class<?> fromPojo = ((LeftJoinDefault) iter).getFromPojoType();
                    if (Objects.nonNull(toPojo)) {
                        fields.putAll(fieldNames(toPojo));
                    }
                    if (Objects.nonNull(fromPojo)) {
                        fields.putAll(fieldNames(fromPojo));
                    }
                }
            }
            end.append(iter.asString()).append(StringSqlUtils.SPACE);
            iter = iter.next;
        }
        return this.columns.length != 0 ? start.append(StringSqlUtils.toStringSeparatorComa(this.columns))
                .append(StringSqlUtils.SPACE)
                .append(end)
                .toString() : start.append(StringSqlUtils.toStringSeparatorComa(
                        fields.keySet().stream().map(key -> key + " as " + fields.get(key).getFirst()).toArray())
                ).append(StringSqlUtils.SPACE)
                .append(end)
                .toString();
    }

    @Override
    public String getText() {
        StringBuilder start = new StringBuilder(SELECT)
                .append(StringSqlUtils.SPACE);
        KeyWorld iter = this.next;
        if (iter instanceof DistinctDefault || iter instanceof TopDefault) {
            start.append(iter.asString())
                    .append(StringSqlUtils.SPACE);
        }
        return start.toString();
    }

    private static Map<String, Pair<String, Field>> fieldNames(Class<?> type) {
        String tableName = MapperUtils.findTableName(type);
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> MapperUtils.isPrimitiveOrWrapper(field.getType()))
                .collect(Collectors.toMap(k -> tableName + "." + k.getName(), v -> Pair.of(tableName + "_" + v.getName(), v) ));
    }

    @Override
    public Distinct distinct() {
        this.next = new DistinctDefault();
        this.next.prev = this;
        return (DistinctDefault) this.next;
    }

    @Override
    public TopDefault top(int number) {
        this.next = new TopDefault(number);
        this.next.prev = this;
        return (TopDefault) this.next;
    }

    @Override
    public From from(String tableName) {
        this.next = new FromDefault(tableName);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

    @Override
    public From from(Class<?> clz) {
        this.next = new FromDefault(clz);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

    @Override
    public KeyWorld toFirst() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.toFirst();
        }
        return this;
    }

}
