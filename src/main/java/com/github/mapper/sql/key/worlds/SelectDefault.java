package com.github.mapper.sql.key.worlds;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.SelectTemplate;

public final class SelectDefault extends KeyWorld implements SelectTemplate {

    private static final String SELECT = "select";

    private final String[] columns;

    private SelectDefault(String[] columns) {
        this.columns = columns;
    }

    public static SelectTemplate select(String... columns) {
        return new SelectDefault(columns);
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder(SELECT)
                .append(StringSqlUtils.SPACE);
        KeyWorld iter = this.next;
        if (iter instanceof DistinctDefault || iter instanceof TopDefault) {
            sb.append(iter.asString())
                    .append(StringSqlUtils.SPACE)
                    .append(StringSqlUtils.toStringSeparatorComa(this.columns));
            iter = iter.next;
        } else {
            sb.append(StringSqlUtils.toStringSeparatorComa(this.columns))
                    .append(StringSqlUtils.SPACE);
        }
        while (iter != null) {
            sb.append(iter.asString()).append(StringSqlUtils.SPACE);
            iter = iter.next;
        }
        return sb.toString();
    }

    @Override
    public Distinct distinct() {
        this.next = new DistinctDefault();
        this.next.prev = this;
        return (DistinctDefault) this.next;
    }

    @Override
    public TopDefault top(int number) {
        this.next = new TopDefault(number);
        this.next.prev = this;
        return (TopDefault) this.next;
    }

    @Override
    public From from(String tableName) {
        this.next = new FromDefault(tableName);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

    @Override
    public From from(Class<?> clz) {
        this.next = new FromDefault(clz);
        this.next.prev = this;
        return (FromDefault) this.next;
    }

}
