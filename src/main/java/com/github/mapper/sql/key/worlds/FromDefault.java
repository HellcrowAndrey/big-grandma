package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;

import java.util.Objects;

public class FromDefault extends KeyWorld implements From {

    private static final String FROM = "from %s";

    private static final String FROM_AND_ALIAS = "from %s as %s";

    private final String operator;

    private final QueryContext queryContext;

    public FromDefault(Class<?> pojoType, QueryContext queryContext) {
        this.queryContext = queryContext;
        this.queryContext.addTable(pojoType);
        QueryContext.Table table = this.queryContext.getTable(pojoType);
        this.operator = String.format(FROM_AND_ALIAS, table.getName(), table.getAlias());
    }

    public FromDefault(String tableName, QueryContext queryContext) {
        this.operator = String.format(FROM, tableName);
        this.queryContext = queryContext;
    }

    @Override
    public Where where(SQLCondition condition) {
        this.next = new WhereDefault(condition, this.queryContext);
        this.next.prev = this;
        return (WhereDefault) this.next;
    }

    @Override
    public OrderBy orderBy(SortedType sortedType, String... columns) {
        this.next = new OrderByDefault(this.queryContext).defaultOrderBy(sortedType, columns);
        this.next.prev = this;
        return (OrderByDefault) this.next;
    }

    @Override
    public Limit limit(int number) {
        this.next = new LimitDefault(number, this.queryContext);
        this.next.prev = this;
        return (LimitDefault) this.next;
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
    public Join join(Class<?> fromTable, String to, String from) {
        this.next = new JoinDefault(fromTable, this.queryContext.getRootTable().getClz(), to, from, this.queryContext);
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
    public LeftJoin leftJoin(Class<?> toJoin, String to, String from) {
        this.next = new LeftJoinDefault(this.queryContext.getRootTable().getClz(), toJoin, to, from, this.queryContext);
        this.next.prev = this;
        return (LeftJoinDefault) this.next;
    }

    @Override
    public LeftJoin leftJoin(Class<?> toJoin, Class<?> toTable, String to, String from) {
        this.next = new LeftJoinDefault(toJoin, toTable, to, from, this.queryContext);
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

    @Override
    public SQLSelect toSelect() {
        return this::asString;
    }

    @Override
    public ReactiveSelect toReactiveSelect() {
        return new ReactiveSelectDefault() {
            @Override
            protected KeyWorld collect() {
                return FromDefault.this.toFirst();
            }

            @Override
            protected QueryContext context() {
                return FromDefault.this.queryContext;
            }
        };
    }
}
