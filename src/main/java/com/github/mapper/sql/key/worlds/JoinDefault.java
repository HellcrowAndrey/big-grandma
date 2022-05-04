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

    public JoinDefault(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable) {
        var toJoinTblName = MapperUtils.findTableName(toJoin);
        var toTblName = MapperUtils.findTableName(toTable);
        this.operator = String.format(
                JOIN,
                toJoinTblName,
                String.format("%s.%s", toJoinTblName, columnToJoin),
                String.format("%s.%s", toTblName, columnToTable)
        );
        this.toPojoType = toJoin;
        this.fromPojoType = toTable;
    }

    public JoinDefault(String tableNameToJoin, Class<?> toTable, String columnToJoin, String columnToTable) {
        var toTblName = MapperUtils.findTableName(toTable);
        this.operator = String.format(
                JOIN,
                tableNameToJoin,
                String.format("%s.%s", tableNameToJoin, columnToJoin),
                String.format("%s.%s", toTblName, columnToTable)
        );
        this.fromPojoType = toTable;
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
    public String getText() {
        return this.asString();
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
