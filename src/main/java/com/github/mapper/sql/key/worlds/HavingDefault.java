package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.QueryContext;
import com.github.mapper.sql.SortedType;

import java.util.Objects;

public class HavingDefault extends KeyWorld implements Having {

    private static final String HAVING = "having %s";

    private final String operator;

    private final QueryContext queryContext;

    public HavingDefault(String function, QueryContext queryContext) {
        this.operator = String.format(HAVING, function);
        this.queryContext = queryContext;
    }

    @Override
    public OrderBy orderBy(SortedType sortedType, String... columns) {
        this.next = new OrderByDefault(this.queryContext).defaultOrderBy(sortedType, columns);
        this.next.prev = this;
        return (OrderBy) this.next;
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
        return asString();
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
