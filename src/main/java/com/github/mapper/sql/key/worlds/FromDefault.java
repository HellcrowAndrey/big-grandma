package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;
import com.github.mapper.utils.MapperUtils;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class FromDefault extends KeyWorld implements From {

    private static final String FROM = "from %s";

    private final String tableName;

    private Class<?> pojoType;

    public FromDefault(Class<?> pojoType) {
        this.tableName = MapperUtils.findTableName(pojoType);
        this.pojoType = pojoType;
    }

    public FromDefault(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Where where(SQLCondition condition) {
        this.next = new WhereDefault(condition);
        this.next.prev = this;
        return (WhereDefault) this.next;
    }

    @Override
    public OrderBy orderBy(SortedType sortedType, String... columns) {
        this.next = new OrderByDefault().defaultOrderBy(sortedType, columns);
        this.next.prev = this;
        return (OrderByDefault) this.next;
    }

    @Override
    public Limit limit(int number) {
        this.next = new LimitDefault(number);
        this.next.prev = this;
        return (LimitDefault) this.next;
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
    public Join join(Class<?> fromTable, String to, String from) {
        this.next = new JoinDefault(this.tableName, fromTable, to, from);
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public Join join(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.next = new JoinDefault(toTable, fromTable, to, from);
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
    public LeftJoin leftJoin(Class<?> fromTable, String to, String from) {
        this.next = new LeftJoinDefault(this.tableName, fromTable, to, from);
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
    public String asString() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.asString();
        }
        return String.format(FROM, this.tableName);
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

    @Override
    public SQLSelect toSelect() {
        return this::asString;
    }

    @Override
    public ReactiveSelect toReactiveSelect(DatabaseClient client) {
        return new ReactiveSelectDefault(client) {
            @Override
            protected KeyWorld collect() {
                return FromDefault.this.toFirst();
            }
        };
    }

    public Class<?> getPojoType() {
        return pojoType;
    }
}
