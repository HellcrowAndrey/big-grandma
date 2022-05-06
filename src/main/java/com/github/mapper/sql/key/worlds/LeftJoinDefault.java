package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;
import com.github.mapper.utils.MapperUtils;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class LeftJoinDefault extends KeyWorld implements LeftJoin {

    private static final String LEFT_JOIN = "left join %s on %s = %s";

    private static final String LEFT_JOIN_WITH_ALIAS = "left join %s as %s on %s = %s";

    private final String operator;

    private final QueryContext queryContext;

    public LeftJoinDefault(String tableName, String leftCol, String rightCol, QueryContext queryContext) {
        this.operator = String.format(LEFT_JOIN, tableName, leftCol, rightCol);
        this.queryContext = queryContext;
    }

    public LeftJoinDefault(Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable, QueryContext queryContext) {
        this.queryContext = queryContext;
        this.queryContext.addTable(toJoin, toTable);
        QueryContext.Table toJoinTbl = this.queryContext.getTable(toJoin);
        var toJoinTblName = toJoinTbl.getName();
        var toJoinTblAlias = toJoinTbl.getAlias();
        QueryContext.Table toTbl = this.queryContext.getTable(toJoin);
        var toTblAlias = toTbl.getAlias();
        this.operator = String.format(
                LEFT_JOIN_WITH_ALIAS,
                toJoinTblName,
                toJoinTblAlias,
                String.format("%s.%s", toJoinTblAlias, columnToJoin),
                String.format("%s.%s", toTblAlias, columnToTable)
        );
    }

    @Override
    public LeftJoin leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol) {
        this.next = new LeftJoinDefault(tableName, leftCol.get(), rightCol.get(), this.queryContext);
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public LeftJoin leftJoin(String tableName, String leftCol, String rightCol) {
        this.next = new LeftJoinDefault(tableName, leftCol, rightCol, this.queryContext);
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public LeftJoin leftJoin(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.next = new LeftJoinDefault(toTable, fromTable, to, from, this.queryContext);
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public Join join(String tableName, ColumnName leftCol, ColumnName rightCol) {
        this.next = new JoinDefault(tableName, leftCol.get(), rightCol.get(), this.queryContext);
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public Join join(String tableName, String leftCol, String rightCol) {
        this.next = new JoinDefault(tableName, leftCol, rightCol, this.queryContext);
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public Join join(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.next = new JoinDefault(toTable, fromTable, to, from, this.queryContext);
        this.next.prev = this;
        return (JoinDefault) this.next;
    }

    @Override
    public Where where(SQLCondition condition) {
        this.next = new WhereDefault(condition, this.queryContext);
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
                return LeftJoinDefault.this.toFirst();
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
}
