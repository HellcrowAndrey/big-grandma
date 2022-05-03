package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;
import com.github.mapper.utils.MapperUtils;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class JoinDefault extends KeyWorld implements Join {

    private static final String JOIN = "inner join %s on %s = %s";

    private final String operator;

    private Class<?> toPojoType;

    private Class<?> fromPojoType;

    public JoinDefault(String tableName, String leftColName, String rightColName) {
        this.operator = String.format(JOIN, tableName, leftColName, rightColName);
    }

    public JoinDefault(Class<?> toTable, Class<?> fromTable, String to, String from) {
        var toTableName = MapperUtils.findTableName(toTable);
        var fromTableName = MapperUtils.findTableName(fromTable);
        this.operator = String.format(
                JOIN,
                fromTableName,
                String.format("%s.%s", toTableName, to),
                String.format("%s.%s", fromTableName, from)
        );
        this.toPojoType = toTable;
        this.fromPojoType = fromTable;
    }

    public JoinDefault(String toTable, Class<?> fromTable, String to, String from) {
        var fromTableName = MapperUtils.findTableName(fromTable);
        this.operator = String.format(
                JOIN,
                fromTableName,
                String.format("%s.%s", toTable, to),
                String.format("%s.%s", fromTableName, from)
        );
        this.fromPojoType = fromTable;
    }

    @Override
    public Join join(String tableName, ColumnName leftCol, ColumnName rightCol) {
        this.next = new JoinDefault(tableName, leftCol.get(), rightCol.get());
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public Join join(String tableName, String leftCol, String rightCol) {
        this.next = new JoinDefault(tableName, leftCol, rightCol);
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public Join join(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.next = new JoinDefault(toTable, fromTable,to, from);
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public LeftJoin leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol) {
        this.next = new LeftJoinDefault(tableName, leftCol.get(), rightCol.get());
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public LeftJoin leftJoin(String tableName, String leftCol, String rightCol) {
        this.next = new LeftJoinDefault(tableName, leftCol, rightCol);
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public LeftJoin leftJoin(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.next = new LeftJoinDefault(toTable, fromTable, to, from);
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public Where where(SQLCondition condition) {
        this.next = new WhereDefault(condition);
        this.next.prev = this;
        return (WhereDefault) this.next;
    }

    @Override
    public SQLSelect toSelect() {
        return this::asString;
    }

    @Override
    public ReactiveSelect toReactiveSelect(DatabaseClient client) {
        return new ReactiveSelectDefault(client) {
            @Override
            protected KeyWorld collect() {
                return JoinDefault.this.toFirst();
            }
        };
    }

    @Override
    public String asString() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.asString();
        }
        return this.operator;
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

    public Class<?> getToPojoType() {
        return toPojoType;
    }

    public Class<?> getFromPojoType() {
        return fromPojoType;
    }
}
