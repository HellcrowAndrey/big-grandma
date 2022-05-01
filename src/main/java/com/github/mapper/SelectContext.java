package com.github.mapper;

import java.util.Objects;

public class SelectContext {
    public static void main(String[] args) {

        String[] columns = {
                "firstName as fn",
                "lastName as ln",
                "age as aliasAge"
        };

        var tableName = "testTable";

        String result = Select.select(columns)
                .from(tableName)
                .toString();
        System.out.println(result);
        String result1 = Select.select(columns)
                .from(tableName)
                .toString();
        System.out.println(result1);
        String result2 = Select.select(columns)
                .from(tableName)
                .toString();
        System.out.println(result2);
        String result3 = Select.select(columns)
                .from(tableName)
                .orderBy(SortedType.desc, "lastName")
                .orderBy("age", SortedType.asc)
                .toString();
        System.out.println(result3);
        String result4 = Select.select(columns)
                .from("testTable")
                .join("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .join("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                ).groupBy("firstName")
                .orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println(result4);

        String result5 = Select.select(columns)
                .distinct()
                .from(tableName)
                .where(
                        SQLCondition.column("age")
                                .greaterThan(1)
                                .and()
                                .column("lastName")
                                .eq("yes")
                                .get()
                ).groupBy("firstName")
                .orderBy(SortedType.asc, "lastName")
                .limit(10)
                .offset(20)
                .toString();
        System.out.println("SQL -> " + result5);

        String result6 = Select.select(columns)
                .from("testTable")
                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                ).orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println(result6);

        String result7 = Select.select(columns)
                .top(50)
                .from("testTable")
                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                ).orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println(result7);

        String result8 = Select.select(columns)
                .from("testTable")
                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                ).orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println("Max -> " + result8);

        String result9 = Select.select(columns)
                .from("testTable")
                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                ).orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println("Min -> " + result9);

        String result10 = Select.select(columns)
                .from("testTable")
                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                ).orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println("Sum -> " + result10);

        String result11 = Select.select(columns)
                .from("testTable")
                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
                .where(SQLCondition.column("age")
                        .greaterThan(1)
                        .get()
                )
                .groupBy("lastName")
                .having(SQLHaving
                        .count("age")
                        .eq(10)
                        .and()
                        .max("age")
                        .lessThan(40)
                        .ofCondition()
                ).orderBy(SortedType.asc, "lastName")
                .toString();
        System.out.println("Result 11 -> " + result11);
        //*************************************************
        SQLCondition con = SQLCondition.column("age")
                .eq(1)
                .get();
        System.out.println(con.asString());
        SQLCondition con1 = SQLCondition.column("age")
                .eq(1)
                .and()
                .not()
                .column("lastName")
                .eq("vasia")
                .get();
        System.out.println(con1.asString());
        SQLCondition con2 = SQLCondition.column("age")
                .eq(1)
                .and()
                .column("lastName")
                .isNotNull()
                .get();
        System.out.println(con2.asString());
        String str3 = SQLCondition.column("age")
                .between(1)
                .and(20)
                .toString();
        System.out.println(str3);

        String str4 = SQLCondition.column("age")
                .greaterThan(1)
                .and()
                .column("lastName")
                .eq("Tigr")
                .toString();
        System.out.println(str4);
    }
    public static class Select extends KeyWorld {
        private static final String SELECT = "select";
        private final String[] columns;
        private Select(String[] columns) {
            this.columns = columns;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(SELECT)
                    .append(StringSqlUtils.SPACE);
            KeyWorld iter = this.next;
            if (iter instanceof Distinct || iter instanceof Top) {
                sb.append(iter)
                        .append(StringSqlUtils.SPACE)
                        .append(StringSqlUtils.toStringSeparatorComa(this.columns));
                iter = iter.next;
            } else {
                sb.append(StringSqlUtils.toStringSeparatorComa(this.columns))
                        .append(StringSqlUtils.SPACE);
            }
            while (iter != null) {
                sb.append(iter).append(StringSqlUtils.SPACE);
                iter = iter.next;
            }
            return sb.toString();
        }
        public static Select select(String... columns) {
            return new Select(columns);
        }
        public Distinct distinct() {
            this.next = new Distinct();
            this.next.prev = this;
            return (Distinct) this.next;
        }
        public Top top(int number) {
            this.next = new Top(number);
            this.next.prev = this;
            return (Top) this.next;
        }
        public From from(String tableName) {
            this.next = new From(tableName);
            this.next.prev = this;
            return (From) this.next;
        }
        public static class Distinct extends KeyWorld {
            private static final String DISTINCT = "distinct";
            private final String columns;
            public Distinct() {
                this.columns = DISTINCT;
            }
            public From from(String tableName) {
                this.next = new From(tableName);
                this.next.prev = this;
                return (From) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.columns;
            }
        }
        public static class Top extends KeyWorld {
            private static final String TOP = "top %d";
            private final String operator;
            public Top(int number) {
                this.operator = String.format(TOP, number);
            }
            public From from(String tableName) {
                this.next = new From(tableName);
                this.next.prev = this;
                return (From) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class From extends KeyWorld {
            private static final String FROM = "from %s";
            private final String tableName;
            public From(String tableName) {
                this.tableName = tableName;
            }
            public Where where(SQLCondition condition) {
                this.next = new Where(condition);
                this.next.prev = this;
                return (Where) this.next;
            }
            public OrderBy orderBy(SortedType sortedType, String... columns) {
                this.next = new OrderBy().orderBy(sortedType, columns);
                this.next.prev = this;
                return (OrderBy) this.next;
            }
            public Limit limit(int number) {
                this.next = new Limit(number);
                this.next.prev = this;
                return (Limit) this.next;
            }
            public Join join(String tableName, String leftCol, String rightCol) {
                this.next = new Join(tableName, leftCol, rightCol);
                this.next.prev = this;
                return (Join) this.next;
            }
            public LeftJoin leftJoin(String tableName, String leftCol, String rightCol) {
                this.next = new LeftJoin(tableName, leftCol, rightCol);
                this.next.prev = this;
                return (LeftJoin) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return String.format(FROM, this.tableName);
            }
        }
        public static class Where extends KeyWorld {
            private static final String WHERE_PATTERN = "where %s";
            private final String operator;
            private Where(String condition) {
                this.operator = String.format(WHERE_PATTERN, condition);
            }
            public Where(SQLCondition condition) {
                this(Objects.requireNonNull(condition, "Condition is required").asString());
            }
            public GroupBy groupBy(String... columns) {
                this.next = new GroupBy(columns);
                this.next.prev = this;
                return (GroupBy) this.next;
            }
            public OrderBy orderBy(SortedType sortedType, String... columns) {
                this.next = new OrderBy().orderBy(sortedType, columns);
                this.next.prev = this;
                return (OrderBy) this.next;
            }
            public Limit limit(int number) {
                this.next = new Limit(number);
                this.next.prev = this;
                return (Limit) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class OrderBy extends KeyWorld {
            private static final String ORDER_BY = "order by %s %s";
            private static final String COMA_BY = "%s, %s %s";
            private int count = 0;
            private String operator;
            public OrderBy orderBy(SortedType sortedType, String... columns) {
                this.operator = switchOp(sortedType, columns);
                return this;
            }
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
            public Limit limit(int number) {
                this.next = new Limit(number);
                this.next.prev = this;
                return (Limit) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class GroupBy extends KeyWorld {
            private static final String GROUP_BY = "group by %s";
            private final String operator;
            public GroupBy(String... columns) {
                this.operator = String.format(GROUP_BY, StringSqlUtils.toStringSeparatorComa(columns));
            }
            public Having having(SQLHaving condition) {
                this.next = new Having(condition.asString());
                this.next.prev = this;
                return (Having) this.next;
            }
            public OrderBy orderBy(SortedType sortedType, String... columns) {
                this.next = new OrderBy().orderBy(sortedType, columns);
                this.next.prev = this;
                return (OrderBy) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class Having extends KeyWorld {
            private static final String HAVING = "having %s";
            private final String operator;
            public Having(String function) {
                this.operator = String.format(HAVING, function);
            }
            public OrderBy orderBy(SortedType sortedType, String... columns) {
                this.next = new OrderBy().orderBy(sortedType, columns);
                this.next.prev = this;
                return (OrderBy) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class Limit extends KeyWorld {
            private static final String LIMIT = "limit %d";
            private final String operator;
            public Limit(int number) {
                this.operator = String.format(LIMIT, number);
            }
            public Offset offset(int number) {
                this.next = new Offset(number);
                this.next.prev = this;
                return (Offset) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class Offset extends KeyWorld {
            private static final String OFFSET = "offset %d";
            private final String operator;
            public Offset(Integer number) {
                this.operator = String.format(OFFSET, number);
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class Join extends KeyWorld {
            private static final String JOIN = "inner join %s on %s = %s";
            private final String operator;
            public Join(String tableName, String leftCol, String rightCol) {
                this.operator = String.format(JOIN, tableName, leftCol, rightCol);
            }
            public Join join(String tableName, String leftCol, String rightCol) {
                this.next = new Join(tableName, leftCol, rightCol);
                this.next.prev = this;
                return (Join) this.next;
            }
            public Where where(SQLCondition condition) {
                this.next = new Where(condition);
                this.next.prev = this;
                return (Where) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
        public static class LeftJoin extends KeyWorld {
            private static final String LEFT_JOIN = "left join %s on %s = %s";
            private final String operator;
            public LeftJoin(String tableName, String leftCol, String rightCol) {
                this.operator = String.format(LEFT_JOIN, tableName, leftCol, rightCol);
            }
            public LeftJoin leftJoin(String tableName, String leftCol, String rightCol) {
                this.next = new LeftJoin(tableName, leftCol, rightCol);
                this.next.prev = this;
                return (LeftJoin) this.next;
            }
            public Where where(SQLCondition condition) {
                this.next = new Where(condition);
                this.next.prev = this;
                return (Where) this.next;
            }
            @Override
            public String toString() {
                if (Objects.nonNull(this.prev)) {
                    KeyWorld tmp = this.prev;
                    this.prev = null;
                    return tmp.toString();
                }
                return this.operator;
            }
        }
    }

    public static abstract class KeyWorld {
        protected KeyWorld next;
        protected KeyWorld prev;
        @Override
        public abstract String toString();
    }
    public enum SortedType {
        asc, desc
    }
}
