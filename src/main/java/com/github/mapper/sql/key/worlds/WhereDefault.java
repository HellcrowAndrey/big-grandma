package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.*;

import java.util.Objects;

public class WhereDefault extends KeyWorld implements Where {


    private static final String WHERE_PATTERN = "where %s";

    private final String operator;

    private WhereDefault(String condition) {
        this.operator = String.format(WHERE_PATTERN, condition);
    }

    public WhereDefault(SQLCondition condition) {
        this(Objects.requireNonNull(condition, "Condition is required").asString());
    }

    @Override
    public GroupBy groupBy(String... columns) {
        this.next = new GroupByDefault(columns);
        this.next.prev = this;
        return (GroupByDefault) this.next;
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
    public String asString() {
        if (Objects.nonNull(this.prev)) {
            KeyWorld tmp = this.prev;
            this.prev = null;
            return tmp.asString();
        }
        return this.operator;
    }

    @Override
    public SQLSelect toSelect() {
        return this::asString;
    }

}
