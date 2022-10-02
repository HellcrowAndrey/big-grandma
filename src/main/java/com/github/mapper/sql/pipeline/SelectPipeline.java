package com.github.mapper.sql.pipeline;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.*;
import com.github.mapper.sql.key.worlds.*;
import com.github.mapper.sql.key.worlds.Select;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.LinkedList;
import java.util.Objects;

public final class SelectPipeline<R> extends SelectTemplatePipeline implements Select<R>, From<R>, Where<R>, GroupBy<R>,
        OrderBy<R>, Limit<R>, Offset<R>, Join<R>, LeftJoin<R>, Having<R>, Top<R>, Distinct<R> {

    private int columnsPosition = 1;

    public SelectPipeline(DatabaseClient client) {
        super(new QueryContext(client), new LinkedList<>());
    }

    @Override
    public Distinct<R> distinct() {
        this.columnsPosition++;
        this.partsOfQuery.add(DISTINCT);
        return this;
    }

    @Override
    public Top<R> top(int number) {
        this.columnsPosition++;
        this.partsOfQuery.add(String.format(TOP, number));
        return this;
    }

    @Override
    public From<R> from(Class<?> clz) {
        this.queryContext.addTable(clz);
        QueryContext.Table table = this.queryContext.getTable(clz);
        this.partsOfQuery.add(String.format(FROM_AND_ALIAS, table.getName(), table.getAlias()));
        return this;
    }

    @Override
    public Where<R> where(SQLCondition condition) {
        this.partsOfQuery.add(String.format(WHERE_PATTERN,
                Objects.requireNonNull(condition, "Condition is required")
                        .asString(this.queryContext)));
        return this;
    }

    @Override
    public GroupBy<R> groupBy(String... columns) {
        this.partsOfQuery.add(String.format(GROUP_BY, StringSqlUtils.toStringSeparatorComa(columns)));
        return this;
    }

    @Override
    public Having<R> having(SQLHaving condition) {
        this.partsOfQuery.add(String.format(HAVING, condition.asString()));
        return this;
    }

    @Override
    public OrderBy<R> orderBy(SortedType sortedType, String... columns) {
        this.partsOfQuery.add(switchOperatorOrderBy(sortedType, columns));
        return this;
    }

    @Override
    public OrderBy<R> orderBy(String column, SortedType type) {
        this.partsOfQuery.add(switchOperatorOrderBy(type, column));
        return this;
    }

    @Override
    public Limit<R> limit(int number) {
        this.partsOfQuery.add(String.format(LIMIT, number));
        return this;
    }

    @Override
    public Offset<R> offset(int number) {
        this.partsOfQuery.add(String.format(OFFSET, number));
        return this;
    }

    @Override
    public Join<R> join(String tableName, ColumnName leftCol, ColumnName rightCol) {
        this.partsOfQuery.add(String.format(JOIN, tableName, leftCol.get(), rightCol.get()));
        return this;
    }

    @Override
    public Join<R> join(String tableName, String leftCol, String rightCol) {
        this.partsOfQuery.add(String.format(JOIN, tableName, leftCol, rightCol));
        return this;
    }

    @Override
    public Join<R> join(Class<?> joinTable, String parent, String join) {
        this.partsOfQuery.add(toJoin(JOIN_WITH_ALIAS, joinTable, this.queryContext.getRootTable().getClz(), parent, join));
        return this;
    }

    @Override
    public Join<R> join(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.partsOfQuery.add(toJoin(JOIN_WITH_ALIAS, toTable, fromTable, to, from));
        return this;
    }

    @Override
    public LeftJoin<R> leftJoin(String tableName, ColumnName leftCol, ColumnName rightCol) {
        this.partsOfQuery.add(String.format(LEFT_JOIN, tableName, leftCol.get(), rightCol.get()));
        return this;
    }

    @Override
    public LeftJoin<R> leftJoin(String tableName, String leftCol, String rightCol) {
        this.partsOfQuery.add(String.format(LEFT_JOIN, tableName, leftCol, rightCol));
        return this;
    }

    @Override
    public LeftJoin<R> leftJoin(Class<?> fromTable, String to, String from) {
        this.partsOfQuery.add(toJoin(LEFT_JOIN_WITH_ALIAS, fromTable, this.queryContext.getRootTable().getClz(), to, from));
        return this;
    }

    @Override
    public LeftJoin<R> leftJoin(Class<?> toTable, Class<?> fromTable, String to, String from) {
        this.partsOfQuery.add(toJoin(LEFT_JOIN_WITH_ALIAS, toTable, fromTable, to, from));
        return this;
    }

    @Override
    public SQLSelect toSelect() {
        this.partsOfQuery.add(this.columnsPosition, StringSqlUtils.toStringSeparatorComa(aggregateColumns().toArray()));
        return () -> String.join(StringSqlUtils.SPACE, this.partsOfQuery);
    }

    @Override
    public ReactiveSelect<R> toReactiveSelect() {
        return new ReactiveSelectDefault<>() {
            @Override
            protected SQLSelect query() {
                return toSelect();
            }

            @Override
            protected QueryContext context() {
                return queryContext;
            }
        };
    }

}
