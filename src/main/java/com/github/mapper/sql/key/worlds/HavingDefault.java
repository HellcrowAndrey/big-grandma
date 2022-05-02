package com.github.mapper.sql.key.worlds;

import com.github.mapper.sql.SortedType;

import java.util.Objects;

public class HavingDefault extends KeyWorld implements Having {

    private static final String HAVING = "having %s";

    private final String operator;

    public HavingDefault(String function) {
        this.operator = String.format(HAVING, function);
    }

    @Override
    public OrderBy orderBy(SortedType sortedType, String... columns) {
        this.next = new OrderByDefault().defaultOrderBy(sortedType, columns);
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

}
