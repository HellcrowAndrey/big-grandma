package com.github.mapper.sql;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.utils.MapperUtils;

import java.util.Objects;
import java.util.function.Supplier;

public interface SQLCondition {

    String asString();

    static Operators column(Class<?> entity, String column) {
        return Operators.newInstance().column(entity, column);
    }

    static Operators column(String column) {
        return Operators.newInstance().column(column);
    }

    static AfterNotOperator not() {
        return Operators.newInstance().not();
    }

    static NextLogicalOperator exists(String query) {
        return Operators.newInstance().exists(query);
    }

    class Operators extends BaseOperator {
        protected String column;

        private Operators() {
        }

        public static Operators newInstance() {
            return new Operators();
        }

        public Operators column(Class<?> entity, String column) {
            this.column = String.format("%s.%s", MapperUtils.findTableName(entity), column);
            return this;
        }

        public Operators column(String column) {
            this.column = column;
            return this;
        }

        public AfterComparisonOperator eq(Object value) {
            this.next = new Eq(value);
            this.next.prev = this;
            return (Eq) this.next;
        }

        public AfterComparisonOperator eq() {
            this.next = new Eq();
            this.next.prev = this;
            return (Eq) this.next;
        }

        public AfterComparisonOperator notEq(Object value) {
            this.next = new NotEq(value);
            this.next.prev = this;
            return (NotEq) this.next;
        }

        public AfterComparisonOperator notEq() {
            this.next = new NotEq();
            this.next.prev = this;
            return (NotEq) this.next;
        }

        public AfterComparisonOperator greaterThan(Object value) {
            this.next = new GreaterThan(value);
            this.next.prev = this;
            return (GreaterThan) this.next;
        }

        public AfterComparisonOperator greaterThan() {
            this.next = new GreaterThan();
            this.next.prev = this;
            return (GreaterThan) this.next;
        }

        public AfterComparisonOperator lessThan(Object value) {
            this.next = new LessThan(value);
            this.next.prev = this;
            return (LessThan) this.next;
        }

        public AfterComparisonOperator lessThan() {
            this.next = new LessThan();
            this.next.prev = this;
            return (LessThan) this.next;
        }

        public AfterComparisonOperator greaterThanOrEq(Object value) {
            this.next = new GreaterThanOrEq(value);
            this.next.prev = this;
            return (GreaterThanOrEq) this.next;
        }

        public AfterComparisonOperator greaterThanOrEq() {
            this.next = new GreaterThanOrEq();
            this.next.prev = this;
            return (GreaterThanOrEq) this.next;
        }

        public AfterComparisonOperator lessThanOrEq(Object value) {
            this.next = new LessThanOrEq(value);
            this.next.prev = this;
            return (LessThanOrEq) this.next;
        }

        public AfterComparisonOperator lessThanOrEq() {
            this.next = new LessThanOrEq();
            this.next.prev = this;
            return (LessThanOrEq) this.next;
        }

        public AfterComparisonOperator oppositeGreaterThan(Object value) {
            this.next = new OppositeGreaterThan(value);
            this.next.prev = this;
            return (OppositeGreaterThan) this.next;
        }

        public AfterComparisonOperator oppositeGreaterThan() {
            this.next = new OppositeGreaterThan();
            this.next.prev = this;
            return (OppositeGreaterThan) this.next;
        }

        public AfterComparisonOperator oppositeLessThan(Object value) {
            this.next = new OppositeLessThan(value);
            this.next.prev = this;
            return (OppositeLessThan) this.next;
        }

        public AfterComparisonOperator oppositeLessThan() {
            this.next = new OppositeLessThan();
            this.next.prev = this;
            return (OppositeLessThan) this.next;
        }

        public AfterNotOperator not() {
            this.next = new Not();
            this.next.prev = this;
            return (Not) this.next;
        }

        public NextLogicalOperator in(Object... values) {
            this.next = new In(values);
            this.next.prev = this;
            return (In) this.next;
        }

        public NextLogicalOperator like(String operator) {
            this.next = new Like(operator);
            this.next.prev = this;
            return (Like) this.next;
        }

        public AfterBetween between(Object val) {
            this.next = new Between(val);
            this.next.prev = this;
            return (Between) this.next;
        }

        public NextLogicalOperator isNull() {
            this.next = new IsNull();
            this.next.prev = this;
            return (IsNull) this.next;
        }

        public NextLogicalOperator isNotNull() {
            this.next = new IsNotNull();
            this.next.prev = this;
            return (IsNotNull) this.next;
        }

        public NextLogicalOperator exists(String query) {
            this.next = new Exists(query);
            this.next.prev = this;
            return (Exists) this.next;
        }

        @Override
        public String toString() {
            String start = Objects.nonNull(this.column) ? this.column : StringSqlUtils.EMPTY;
            StringBuilder sb = new StringBuilder(start).append(StringSqlUtils.SPACE);
            BaseOperator iter = this.next;
            while (iter != null) {
                if (Objects.nonNull(iter.next)) {
                    sb.append(iter).append(StringSqlUtils.SPACE);
                } else {
                    sb.append(iter);
                }
                iter = iter.next;
            }
            return sb.toString();
        }
    }

    class IntermediateOperators extends Operators {
        public static IntermediateOperators newInstance() {
            return new IntermediateOperators();
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.column;
        }
    }

    abstract class BaseOperator implements Supplier<SQLCondition> {

        protected BaseOperator next;

        protected BaseOperator prev;

        @Override
        public SQLCondition get() {
            return this::toString;
        }

        @Override
        public abstract String toString();
    }

    abstract class AfterComparisonOperator extends BaseOperator {
        public AfterLogicalOperator and(Object val) {
            this.next = new And(val);
            this.next.prev = this;
            return (And) this.next;
        }

        public AfterLogicalOperator and() {
            this.next = new And();
            this.next.prev = this;
            return (And) this.next;
        }

        public AfterLogicalOperator or(Object val) {
            this.next = new Or(val);
            this.next.prev = this;
            return (Or) this.next;
        }

        public AfterLogicalOperator or() {
            this.next = new Or();
            this.next.prev = this;
            return (Or) this.next;
        }

        public AfterNotOperator not() {
            this.next = new Not();
            this.next.prev = this;
            return (Not) this.next;
        }

        public AfterLogicalOperator any(String query) {
            this.next = new Any(query);
            this.next.prev = this;
            return (Any) this.next;
        }

        public AfterLogicalOperator all(String query) {
            this.next = new All(query);
            this.next.prev = this;
            return (All) this.next;
        }
    }

    abstract class NextLogicalOperator extends BaseOperator {
        public AfterLogicalOperator and() {
            this.next = new And();
            this.next.prev = this;
            return (And) this.next;
        }

        public AfterLogicalOperator or() {
            this.next = new Or();
            this.next.prev = this;
            return (Or) this.next;
        }
    }

    abstract class AfterBetween extends BaseOperator {
        public AfterLogicalOperator and(Object val) {
            this.next = new And(val);
            this.next.prev = this;
            return (And) this.next;
        }
    }

    abstract class AfterLogicalOperator extends BaseOperator {
        public Operators column(Class<?> entity, String column) {
            this.next = IntermediateOperators.newInstance().column(entity, column);
            this.next.prev = this;
            return (Operators) this.next;
        }

        public Operators column(String column) {
            this.next = IntermediateOperators.newInstance().column(column);
            this.next.prev = this;
            return (Operators) this.next;
        }

        public NextLogicalOperator in(Object... values) {
            this.next = IntermediateOperators.newInstance().in(values);
            this.next.prev = this;
            return (In) this.next;
        }

        public NextLogicalOperator like(String pattern) {
            this.next = IntermediateOperators.newInstance().like(pattern);
            this.next.prev = this;
            return (Like) this.next;
        }

        public AfterBetween between(Object val) {
            this.next = IntermediateOperators.newInstance().between(val);
            this.next.prev = this;
            return (Between) this.next;
        }

        public AfterNotOperator not() {
            this.next = new Not();
            this.next.prev = this;
            return (Not) this.next;
        }
    }

    abstract class AfterNotOperator extends BaseOperator {
        public Operators column(Class<?> entity, String column) {
            this.next = IntermediateOperators.newInstance().column(entity, column);
            this.next.prev = this;
            return (Operators) this.next;
        }

        public Operators column(String column) {
            this.next = IntermediateOperators.newInstance().column(column);
            this.next.prev = this;
            return (Operators) this.next;
        }

        public NextLogicalOperator in(Object... values) {
            this.next = IntermediateOperators.newInstance().in(values);
            this.next.prev = this;
            return (In) this.next;
        }

        public NextLogicalOperator like(String pattern) {
            this.next = IntermediateOperators.newInstance().like(pattern);
            this.next.prev = this;
            return (Like) this.next;
        }

        public AfterBetween between(Object val) {
            this.next = IntermediateOperators.newInstance().between(val);
            this.next.prev = this;
            return (Between) this.next;
        }
    }

    class Eq extends AfterComparisonOperator {
        private static final String EQUALS = "=";
        private static final String EQUALS_VAL = "= %s";
        private final String operator;

        public Eq() {
            this.operator = EQUALS;
        }

        public Eq(Object val) {
            this.operator = String.format(EQUALS_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class NotEq extends AfterComparisonOperator {
        private static final String NOT_EQUALS = "!=";
        private static final String NOT_EQUALS_VAL = "!= %s";
        private final String operator;

        public NotEq() {
            this.operator = NOT_EQUALS;
        }

        public NotEq(Object val) {
            this.operator = String.format(NOT_EQUALS_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class GreaterThan extends AfterComparisonOperator {
        private static final String GREATER_THAN = ">";
        private static final String GREATER_THAN_VAL = "> %s";
        private final String operator;

        public GreaterThan() {
            this.operator = GREATER_THAN;
        }

        public GreaterThan(Object val) {
            this.operator = String.format(GREATER_THAN_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class LessThan extends AfterComparisonOperator {
        private static final String LESS_THAN = "<";
        private static final String LESS_THAN_VAL = "< %s";
        private final String operator;

        public LessThan() {
            this.operator = LESS_THAN;
        }

        public LessThan(Object val) {
            this.operator = String.format(LESS_THAN_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class GreaterThanOrEq extends AfterComparisonOperator {
        private static final String GREATER_THAN_OR_EQ = ">=";
        private static final String GREATER_THAN_OR_EQ_VAL = ">= %s";
        private final String operator;

        public GreaterThanOrEq() {
            this.operator = GREATER_THAN_OR_EQ;
        }

        public GreaterThanOrEq(Object val) {
            this.operator = String.format(GREATER_THAN_OR_EQ_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class LessThanOrEq extends AfterComparisonOperator {
        private static final String LESS_THAN_OR_EQ = "<=";
        private static final String LESS_THAN_OR_EQ_VAL = "<= %s";
        private final String operator;

        public LessThanOrEq() {
            this.operator = LESS_THAN_OR_EQ;
        }

        public LessThanOrEq(Object val) {
            this.operator = String.format(LESS_THAN_OR_EQ_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class OppositeGreaterThan extends AfterComparisonOperator {
        private static final String OPPOSITE_GREATER_THAN = "!>";
        private static final String OPPOSITE_GREATER_THAN_VAL = "!> %s";
        private final String operator;

        public OppositeGreaterThan() {
            this.operator = OPPOSITE_GREATER_THAN;
        }

        public OppositeGreaterThan(Object val) {
            this.operator = String.format(OPPOSITE_GREATER_THAN_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class OppositeLessThan extends AfterComparisonOperator {
        private static final String OPPOSITE_LESS_THAN = "!<";
        private static final String OPPOSITE_LESS_THAN_VAL = "!< %s";
        private final String operator;

        public OppositeLessThan() {
            this.operator = OPPOSITE_LESS_THAN;
        }

        public OppositeLessThan(Object val) {
            this.operator = String.format(OPPOSITE_LESS_THAN_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class All extends AfterLogicalOperator {
        private static final String ALL = "all (%s)";
        private final String operator;

        public All(String query) {
            this.operator = String.format(ALL, query);
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class And extends AfterLogicalOperator {
        private static final String AND = "and %s";
        private final String operator;

        public And() {
            this.operator = "and";
        }

        public And(Object val) {
            this.operator = String.format(AND, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class Any extends AfterLogicalOperator {
        private static final String ANY = "any (%s)";
        private final String operator;

        public Any(String query) {
            this.operator = String.format(ANY, query);
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class Between extends AfterBetween {
        private static final String BETWEEN = "between %s";
        private final String operator;

        public Between(Object val) {
            this.operator = String.format(BETWEEN, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class Exists extends NextLogicalOperator {
        private static final String EXISTS = "exists %s";
        private final String operator;

        public Exists(String query) {
            this.operator = String.format(EXISTS, query);
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class In extends NextLogicalOperator {
        private static final String IN = "in (%s)";
        private String operator = StringSqlUtils.EMPTY;

        public In(Object... values) {
            if (values.length != 0) {
                Object val = values[0];
                if (val instanceof String || val instanceof Character) {
                    this.operator = String.format(IN, StringSqlUtils.toStringSeparatorComaAndQuotes(values));
                } else {
                    this.operator = String.format(IN, StringSqlUtils.toStringSeparatorComa(values));
                }
            }
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class Like extends NextLogicalOperator {
        private static final String LIKE = "like %s";
        private final String operator;

        public Like(String operator) {
            this.operator = String.format(LIKE, StringSqlUtils.toStringWithQuote(operator));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class Not extends AfterNotOperator {
        private static final String NOT = "not";
        private final String operator;

        public Not() {
            this.operator = NOT;
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class Or extends AfterLogicalOperator {
        private static final String OR = "or";
        private static final String OR_VAL = "or %s";
        private final String operator;

        public Or() {
            this.operator = OR;
        }

        public Or(Object val) {
            this.operator = String.format(OR_VAL, StringSqlUtils.toTextOrSame(val));
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class IsNull extends NextLogicalOperator {
        private static final String IS_NULL = "is null";
        private final String operator;

        public IsNull() {
            this.operator = IS_NULL;
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

    class IsNotNull extends NextLogicalOperator {
        private static final String IS_NOT_NULL = "is not null";
        private final String operator;

        public IsNotNull() {
            this.operator = IS_NOT_NULL;
        }

        @Override
        public String toString() {
            if (Objects.nonNull(this.prev)) {
                BaseOperator tmp = this.prev;
                this.prev = null;
                return tmp.toString();
            }
            return this.operator;
        }
    }

}
