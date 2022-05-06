package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class WhereDefault extends KeyWorld implements Where {

    private static final String WHERE_PATTERN = "where %s";

    private final String operator;

    private final QueryContext queryContext;

    private WhereDefault(String condition, QueryContext queryContext) {
        this.operator = String.format(WHERE_PATTERN, condition);
        this.queryContext = queryContext;
    }

    public WhereDefault(SQLCondition condition, QueryContext queryContext) {
        this(
                Objects.requireNonNull(condition, "Condition is required").asString(),
                queryContext
        );
    }

    @Override
    public GroupBy groupBy(String... columns) {
        this.next = new GroupByDefault(this.queryContext, columns);
        this.next.prev = this;
        return (GroupByDefault) this.next;
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
    public ReactiveSelect toReactiveSelect(DatabaseClient client) {
        return new ReactiveSelectDefault(client) {
            @Override
            protected KeyWorld collect() {
                return WhereDefault.this.toFirst();
            }
        };
    }

}
