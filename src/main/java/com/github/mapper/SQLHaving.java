package com.github.mapper;

import java.util.Objects;

public interface SQLHaving {

    String asString();

    static ComparisonOperator count(String column) {
        return Having.newInstance().count(column);
    }

    static ComparisonOperator min(String column) {
        return Having.newInstance().min(column);
    }

    static ComparisonOperator max(String column) {
        return Having.newInstance().max(column);
    }

    static ComparisonOperator avg(String column) {
        return Having.newInstance().avg(column);
    }

    static ComparisonOperator sum(String column) {
        return Having.newInstance().sum(column);
    }

    class Having extends NodeNext {

        private Having() {
        }

        public static Having newInstance() {
            return new Having();
        }

        public ComparisonOperator count(String column) {
            this.next = new ComparisonOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("count(")
                            .append(column)
                            .append(')');
                }
            };
            this.next.prev = this;
            return (ComparisonOperator) this.next;
        }

        public ComparisonOperator min(String column) {
            this.next = new ComparisonOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("min(")
                            .append(column)
                            .append(')');
                }
            };
            this.next.prev = this;
            return (ComparisonOperator) this.next;
        }

        public ComparisonOperator max(String column) {
            this.next = new ComparisonOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("max(")
                            .append(column)
                            .append(')');
                }
            };
            this.next.prev = this;
            return (ComparisonOperator) this.next;
        }

        public ComparisonOperator avg(String column) {
            this.next = new ComparisonOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("avg(")
                            .append(column)
                            .append(")");
                }
            };
            this.next.prev = this;
            return (ComparisonOperator) this.next;
        }

        public ComparisonOperator sum(String column) {
            this.next = new ComparisonOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("sum(")
                            .append(column)
                            .append(")");
                }
            };
            this.next.prev = this;
            return (ComparisonOperator) this.next;
        }

        @Override
        public StringBuilder asString() {
            StringBuilder sb = new StringBuilder(StringSqlUtils.EMPTY);
            NodeNext iter = this.next;
            while (Objects.nonNull(iter)) {
                if (Objects.nonNull(iter.next)) {
                    sb.append(iter.asString()).append(StringSqlUtils.SPACE);
                } else {
                    sb.append(iter.asString());
                }
                iter = iter.next;
            }
            return sb;
        }

    }

    abstract class IntermediateHaving extends Having {

        public IntermediateHaving() {
        }

        @Override
        public StringBuilder asString() {
            if (Objects.nonNull(this.prev)) {
                NodeNext tmp = this.prev;
                this.prev = null;
                return tmp.asString();
            }
            return new StringBuilder(0);
        }
    }

    abstract class TerminalHaving extends NodeNext {
        public TerminalHaving() {
        }
        @Override
        public StringBuilder asString() {
            if (Objects.nonNull(this.prev)) {
                NodeNext tmp = this.prev;
                this.prev = null;
                return tmp.asString();
            }
            return new StringBuilder(0);
        }

        public final SQLHaving ofCondition() {
            return () -> asString().toString();
        }

    }

    abstract class ComparisonOperator extends NodeNext {

        public LogicalOperator eq(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("=")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator notEq(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("!=")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator greaterThan(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder(">")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator lessThan(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("<")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator greaterThanOrEq(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder(">=")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator lessThanOrEq(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("<=")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator oppositeGreaterThan(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("!>")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public LogicalOperator oppositeLessThan(Object value) {
            this.next = new LogicalOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("!<")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (LogicalOperator) this.next;
        }

        public AfterBetweenOperator between(Object value) {
            this.next = new AfterBetweenOperator() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("between")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (AfterBetweenOperator) this.next;
        }

    }

    abstract class LogicalOperator extends NodeNext {

        public IntermediateHaving and() {
            this.next = new IntermediateHaving() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("and");
                }
            };
            this.next.prev = this;
            return (IntermediateHaving) this.next;
        }

        public IntermediateHaving or() {
            this.next = new IntermediateHaving() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("or");
                }
            };
            this.next.prev = this;
            return (IntermediateHaving) this.next;
        }

        public final SQLHaving ofCondition() {
            return () -> asString().toString();
        }

    }

    abstract class AfterBetweenOperator extends NodeNext {

        public TerminalHaving and(Object value) {
            this.next = new TerminalHaving() {
                @Override
                public StringBuilder asString() {
                    if (Objects.nonNull(this.prev)) {
                        NodeNext tmp = this.prev;
                        this.prev = null;
                        return tmp.asString();
                    }
                    return new StringBuilder("and")
                            .append(StringSqlUtils.SPACE)
                            .append(StringSqlUtils.toTextOrSame(value));
                }
            };
            this.next.prev = this;
            return (TerminalHaving) this.next;
        }
    }

    abstract class NodeNext {
        protected NodeNext next;
        protected NodeNext prev;

        public abstract StringBuilder asString();
    }

}
