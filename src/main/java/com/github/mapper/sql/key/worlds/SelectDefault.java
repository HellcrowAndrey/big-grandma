package com.github.mapper.sql.key.worlds;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.QueryContext;
import com.github.mapper.sql.Select;
import com.github.mapper.utils.MapperUtils;
import org.springframework.data.util.Pair;
import org.springframework.r2dbc.core.DatabaseClient;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class SelectDefault extends KeyWorld implements Select {

    private static final String SELECT = "select";

    private String[] columns;

    private final QueryContext queryContext;

    private SelectDefault(String[] columns) {
        this.columns = columns;
        this.queryContext = new QueryContext();
    }

    private SelectDefault(DatabaseClient client) {
        this.queryContext = new QueryContext(client);
    }

    public static Select select(String... columns) {
        return new SelectDefault(columns);
    }

    public static Select select(DatabaseClient client) {
        return new SelectDefault(client);
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder(SELECT)
                .append(StringSqlUtils.SPACE);
        KeyWorld iter = this.next;
        if (iter instanceof DistinctDefault || iter instanceof TopDefault) {
            sb.append(iter.asString())
                    .append(StringSqlUtils.SPACE);
            iter = iter.next;
        }
        if (this.columns.length == 0) {
            Map<QueryContext.Table, List<QueryContext.Column>> columns = this.queryContext.getColumns();
            List<String> array = new LinkedList<>();
            columns.forEach((table, cols) -> {
                cols.forEach(column -> {
                    String s = column.getColumnName() +
                            StringSqlUtils.SPACE +
                            "as" +
                            StringSqlUtils.SPACE +
                            column.getAlias();
                    array.add(s);
                });
            });
            sb.append(StringSqlUtils.toStringSeparatorComa(array.toArray()))
                    .append(StringSqlUtils.SPACE);
        } else {
            sb.append(StringSqlUtils.toStringSeparatorComa(this.columns))
                    .append(StringSqlUtils.SPACE);
        }
        while (iter != null) {
            sb.append(iter.asString()).append(StringSqlUtils.SPACE);
            iter = iter.next;
        }
        return sb.toString();
    }

    @Override
    public String getText() {
        return SELECT +
                StringSqlUtils.SPACE;
    }

    private static Map<String, Pair<String, Field>> fieldNames(Class<?> type) {
        String tableName = MapperUtils.findTableName(type);
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> MapperUtils.isPrimitiveOrWrapper(field.getType()))
                .collect(Collectors.toMap(k -> tableName + "." + k.getName(), v -> Pair.of(tableName + "_" + v.getName(), v)));
    }

    @Override
    public Distinct distinct() {
        this.next = new DistinctDefault(this.queryContext);
        this.next.prev = this;
        return (DistinctDefault) this.next;
    }

    @Override
    public TopDefault top(int number) {
        this.next = new TopDefault(number, this.queryContext);
        this.next.prev = this;
        return (TopDefault) this.next;
    }

    @Override
    public From from(String tableName) {
        this.next = new FromDefault(tableName, this.queryContext);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

    @Override
    public From from(Class<?> clz) {
        this.next = new FromDefault(clz, this.queryContext);
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
