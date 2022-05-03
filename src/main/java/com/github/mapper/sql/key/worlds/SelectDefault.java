package com.github.mapper.sql.key.worlds;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.Select;
import com.github.mapper.utils.MapperUtils;

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
        Set<String> fields = new LinkedHashSet<>();
        StringBuilder end = new StringBuilder();
        while (iter != null) {
            if (this.columns.length == 0) {
                if (iter instanceof FromDefault) {
                    Class<?> type = ((FromDefault) iter).pojoType;
                    if (Objects.nonNull(type)) {
                        fields.addAll(fieldNames(type));
                    }
                }
                if (iter instanceof JoinDefault) {
                    Class<?> toPojo = ((JoinDefault) iter).toPojoType;
                    Class<?> fromPojo = ((JoinDefault) iter).fromPojoType;
                    if (Objects.nonNull(toPojo)) {
                        fields.addAll(fieldNames(toPojo));
                    }
                    if (Objects.nonNull(fromPojo)) {
                        fields.addAll(fieldNames(fromPojo));
                    }
                }
                if (iter instanceof LeftJoinDefault) {
                    Class<?> toPojo = ((LeftJoinDefault) iter).toPojoType;
                    Class<?> fromPojo = ((LeftJoinDefault) iter).fromPojoType;
                    if (Objects.nonNull(toPojo)) {
                        fields.addAll(fieldNames(toPojo));
                    }
                    if (Objects.nonNull(fromPojo)) {
                        fields.addAll(fieldNames(fromPojo));
                    }
                }
            }
            end.append(iter.asString()).append(StringSqlUtils.SPACE);
            iter = iter.next;
        }
        return this.columns.length != 0 ? start.append(StringSqlUtils.toStringSeparatorComa(this.columns))
                .append(StringSqlUtils.SPACE)
                .append(end)
                .toString() : start.append(StringSqlUtils.toStringSeparatorComa(fields.toArray(new String[0])))
                .append(StringSqlUtils.SPACE)
                .append(end)
                .toString();
    }

    private static List<String> fieldNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> MapperUtils.isPrimitiveOrWrapper(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toList());
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

}
