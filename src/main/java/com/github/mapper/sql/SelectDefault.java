package com.github.mapper.sql;

import com.github.mapper.StringSqlUtils;

import java.util.Objects;

public final class SelectDefault extends KeyWorld implements SelectClaim {

    private static final String SELECT = "select";
    private final String[] columns;

    private SelectDefault(String[] columns) {
        this.columns = columns;
    }

    public static SelectClaim select(String... columns) {
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

    public Distinct distinct() {
        this.next = new DistinctDefault();
        this.next.prev = this;
        return (DistinctDefault) this.next;
    }

    public TopDefault top(int number) {
        this.next = new TopDefault(number);
        this.next.prev = this;
        return (TopDefault) this.next;
    }

    public From from(String tableName) {
        this.next = new FromDefault(tableName);
        this.next.prev = this;
        return (FromDefault) this.next;
    }


    interface Distinct {
        From from(String tableName);
    }

    public static class DistinctDefault extends KeyWorld implements Distinct {

        private static final String DISTINCT = "distinct";
        private final String columns;

        public DistinctDefault() {
            this.columns = DISTINCT;
        }

        @Override
        public From from(String tableName) {
            this.next = new FromDefault(tableName);
            this.next.prev = this;
            return (FromDefault) this.next;
        }

        @Override
        public String asString() {
            if (Objects.nonNull(this.prev)) {
                KeyWorld tmp = this.prev;
                this.prev = null;
                return tmp.asString();
            }
            return this.columns;
        }
    }

    interface Top {
        From from(String tableName);
    }

    public static class TopDefault extends KeyWorld implements Top {

        private static final String TOP = "top %d";
        private final String operator;

        public TopDefault(int number) {
            this.operator = String.format(TOP, number);
        }

        @Override
        public From from(String tableName) {
            this.next = new FromDefault(tableName);
            this.next.prev = this;
            return (FromDefault) this.next;
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

    interface From {
        Where where(SQLCondition condition);

        OrderBy orderBy(SortedType sortedType, String... columns);

        Limit limit(int number);

        Join join(String tableName, String leftCol, String rightCol);

        LeftJoinDefault leftJoin(String tableName, String leftCol, String rightCol);

        SQLSelect toSelect();
    }

    public static class FromDefault extends KeyWorld implements From {

        private static final String FROM = "from %s";
        private final String tableName;

        public FromDefault(String tableName) {
            this.tableName = tableName;
        }

        @Override
        public Where where(SQLCondition condition) {
            this.next = new WhereDefault(condition);
            this.next.prev = this;
            return (WhereDefault) this.next;
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
        public Join join(String tableName, String leftCol, String rightCol) {
            this.next = new JoinDefault(tableName, leftCol, rightCol);
            this.next.prev = this;
            return (JoinDefault) this.next;
        }

        @Override
        public LeftJoinDefault leftJoin(String tableName, String leftCol, String rightCol) {
            this.next = new LeftJoinDefault(tableName, leftCol, rightCol);
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
            return String.format(FROM, this.tableName);
        }

        @Override
        public SQLSelect toSelect() {
            return this::asString;
        }
    }

    interface Where {

        GroupBy groupBy(String... columns);

        OrderBy orderBy(SortedType sortedType, String... columns);

        Limit limit(int number);

        SQLSelect toSelect();

    }

    public static class WhereDefault extends KeyWorld implements Where {
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

    interface OrderBy {

        OrderBy orderBy(SortedType sortedType, String... columns);

        OrderBy orderBy(String column, SortedType type);

        Limit limit(int number);

        SQLSelect toSelect();

    }

    public static class OrderByDefault extends KeyWorld implements OrderBy {
        private static final String ORDER_BY = "order by %s %s";
        private static final String COMA_BY = "%s, %s %s";
        private int count = 0;
        private String operator;

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
            this.next = new LimitDefault(number);
            this.next.prev = this;
            return (LimitDefault) this.next;
        }

        @Override
        public SQLSelect toSelect() {
            return this::asString;
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

    interface GroupBy {

        Having having(SQLHaving condition);

        OrderBy orderBy(SortedType sortedType, String... columns);

    }

    public static class GroupByDefault extends KeyWorld implements GroupBy {
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
    }

    interface Having {
        OrderBy orderBy(SortedType sortedType, String... columns);
    }

    public static class HavingDefault extends KeyWorld implements Having {
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

    interface Limit {
        Offset offset(int number);
    }

    public static class LimitDefault extends KeyWorld implements Limit {
        private static final String LIMIT = "limit %d";
        private final String operator;

        public LimitDefault(int number) {
            this.operator = String.format(LIMIT, number);
        }

        @Override
        public Offset offset(int number) {
            this.next = new OffsetDefault(number);
            this.next.prev = this;
            return (OffsetDefault) this.next;
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

    interface Offset {
        SQLSelect toSelect();
    }

    public static class OffsetDefault extends KeyWorld implements Offset {
        private static final String OFFSET = "offset %d";
        private final String operator;

        public OffsetDefault(Integer number) {
            this.operator = String.format(OFFSET, number);
        }

        @Override
        public SQLSelect toSelect() {
            return this::asString;
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

    interface Join {

        Join join(String tableName, String leftCol, String rightCol);

        LeftJoin leftJoin(String tableName, String leftCol, String rightCol);

        Where where(SQLCondition condition);

        SQLSelect toSelect();

    }

    public static class JoinDefault extends KeyWorld implements Join {
        private static final String JOIN = "inner join %s on %s = %s";
        private final String operator;

        public JoinDefault(String tableName, String leftCol, String rightCol) {
            this.operator = String.format(JOIN, tableName, leftCol, rightCol);
        }

        @Override
        public Join join(String tableName, String leftCol, String rightCol) {
            this.next = new JoinDefault(tableName, leftCol, rightCol);
            this.next.prev = this;
            return (JoinDefault) this.next;
        }

        @Override
        public LeftJoin leftJoin(String tableName, String leftCol, String rightCol) {
            this.next = new LeftJoinDefault(tableName, leftCol, rightCol);
            this.next.prev = this;
            return (LeftJoinDefault) this.next;
        }

        @Override
        public Where where(SQLCondition condition) {
            this.next = new WhereDefault(condition);
            this.next.prev = this;
            return (WhereDefault) this.next;
        }

        @Override
        public SQLSelect toSelect() {
            return this::asString;
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

    interface LeftJoin {

        LeftJoin leftJoin(String tableName, String leftCol, String rightCol);

        Join join(String tableName, String leftCol, String rightCol);

        Where where(SQLCondition condition);

        SQLSelect toSelect();

    }

    public static class LeftJoinDefault extends KeyWorld implements LeftJoin {
        private static final String LEFT_JOIN = "left join %s on %s = %s";
        private final String operator;

        public LeftJoinDefault(String tableName, String leftCol, String rightCol) {
            this.operator = String.format(LEFT_JOIN, tableName, leftCol, rightCol);
        }

        @Override
        public LeftJoin leftJoin(String tableName, String leftCol, String rightCol) {
            this.next = new LeftJoinDefault(tableName, leftCol, rightCol);
            this.next.prev = this;
            return (LeftJoinDefault) this.next;
        }

        @Override
        public Join join(String tableName, String leftCol, String rightCol) {
            this.next = new JoinDefault(tableName, leftCol, rightCol);
            this.next.prev = this;
            return (JoinDefault) this.next;
        }

        @Override
        public Where where(SQLCondition condition) {
            this.next = new WhereDefault(condition);
            this.next.prev = this;
            return (WhereDefault) this.next;
        }

        @Override
        public SQLSelect toSelect() {
            return this::asString;
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

}
