package com.github.mapper.sql.key.worlds;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.SQLHaving;
import com.github.mapper.sql.SortedType;

import java.util.Objects;

public class GroupByDefault extends KeyWorld implements GroupBy {

    private static final String GROUP_BY = "group by %s";

    private final String operator;

    public GroupByDefault(String... columns) {
        this.operator = String.format(GROUP_BY, StringSqlUtils.toStringSeparatorComa(columns));
    }

    @Override
    public Having having(SQLHaving condition) {
        this.next = new HavingDefault(condition.asString());
        this.next.prev = this;
        return (HavingDefault) this.next;
    }

    @Override
    public OrderBy orderBy(SortedType sortedType, String... columns) {
        this.next = new OrderByDefault().defaultOrderBy(sortedType, columns);
        this.next.prev = this;
        return (OrderByDefault) this.next;
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
