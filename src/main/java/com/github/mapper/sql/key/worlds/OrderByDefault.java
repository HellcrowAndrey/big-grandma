package com.github.mapper.sql.key.worlds;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.*;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.Objects;

public class OrderByDefault extends KeyWorld implements OrderBy {

    private static final String ORDER_BY = "order by %s %s";

    private static final String COMA_BY = "%s, %s %s";

    private int count = 0;

    private String operator;

    private final QueryContext queryContext;

    public OrderByDefault(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    @Override
    public OrderBy orderBy(SortedType sortedType, String... columns) {
        this.operator = switchOp(sortedType, columns);
        return this;
    }

    public OrderByDefault defaultOrderBy(SortedType sortedType, String... columns) {
        this.operator = switchOp(sortedType, columns);
        return this;
    }

    @Override
    public OrderBy orderBy(String column, SortedType type) {
        this.operator = switchOp(type, column);
        return this;
    }

    private String switchOp(SortedType sortedType, String... columns) {
        String result;
        Object tmp = columns.length > 1 ? StringSqlUtils.toStringSeparatorComa(columns) : columns[0];
        if (this.count++ == 0) {
            result = String.format(ORDER_BY, tmp, sortedType.name());
        } else {
            result = String.format(COMA_BY, this.operator, tmp, sortedType.name());
        }
        return result;
    }

    @Override
    public Limit limit(int number) {
        this.next = new LimitDefault(number, this.queryContext);
        this.next.prev = this;
        return (LimitDefault) this.next;
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
                return OrderByDefault.this.toFirst();
            }

            @Override
            protected QueryContext context() {
                return OrderByDefault.this.queryContext;
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
