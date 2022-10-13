package com.github.mapper.sql.pipeline;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.QueryContext;
import com.github.mapper.sql.SQLCondition;
import com.github.mapper.sql.conditions.BaseCondition;
import com.github.mapper.sql.conditions.operators.*;

import java.util.ArrayList;
import java.util.List;

public class ConditionsPipeline implements ColumnOperator, AfterComparisonOperators, BaseCondition,
        CommonOperators, AfterBetweenOperatorsInConditions,
        AfterNotOperator, AfterLogicalOperator, LogicalOperatorsInConditions {

    private static final String NOT = "not";
    private static final String EXISTS = "exists";
    private static final String EQUALS = "=";
    private static final String NOT_EQUALS = "!=";
    private static final String GREATER_THAN = ">";
    private static final String LESS_THAN = "<";
    private static final String GREATER_THAN_OR_EQ = ">=";
    private static final String LESS_THAN_OR_EQ = "<=";
    private static final String OPPOSITE_GREATER_THAN = "!>";
    private static final String OPPOSITE_LESS_THAN = "!<";
    private static final String BETWEEN = "between";
    private static final String IN = "in (%s)";
    private static final String LIKE = "like";

    private final QueryContext context;

    private final List<String> query;

    public ConditionsPipeline(QueryContext context) {
        this.context = context;
        this.query = new ArrayList<>();
    }

    @Override
    public CommonOperators column(Class<?> entity, String column) {
        this.query.add(String.format("%s.%s", this.context.getTable(entity).getAlias(), column));
        return this;
    }

    @Override
    public SQLCondition get() {
        return () -> String.join(StringSqlUtils.SPACE, this.query);
    }

    @Override
    public AfterNotOperator not() {
        this.query.add(NOT);
        return this;
    }

    @Override
    public LogicalOperatorsInConditions exists(String query) {
        this.query.add(EXISTS + StringSqlUtils.SPACE + query);
        return this;
    }

    @Override
    public AfterComparisonOperators eq() {
        this.query.add(EQUALS);
        return this;
    }

    @Override
    public AfterComparisonOperators notEq() {
        this.query.add(NOT_EQUALS);
        return this;
    }

    @Override
    public AfterComparisonOperators greaterThan() {
        this.query.add(GREATER_THAN);
        return this;
    }

    @Override
    public AfterComparisonOperators lessThan() {
        this.query.add(LESS_THAN);
        return this;
    }

    @Override
    public AfterComparisonOperators greaterThanOrEq() {
        this.query.add(GREATER_THAN_OR_EQ);
        return this;
    }

    @Override
    public AfterComparisonOperators lessThanOrEq() {
        this.query.add(LESS_THAN_OR_EQ);
        return this;
    }

    @Override
    public AfterComparisonOperators notGreaterThan() {
        this.query.add(OPPOSITE_GREATER_THAN);
        return this;
    }

    @Override
    public AfterComparisonOperators notLessThan() {
        this.query.add(OPPOSITE_LESS_THAN);
        return this;
    }

    @Override
    public AfterComparisonOperators eq(Object value) {
        this.query.add(EQUALS + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators notEq(Object value) {
        this.query.add(NOT_EQUALS + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators greaterThan(Object value) {
        this.query.add(GREATER_THAN + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators lessThan(Object value) {
        this.query.add(LESS_THAN + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators greaterThanOrEq(Object value) {
        this.query.add(GREATER_THAN_OR_EQ + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators lessThanOrEq(Object value) {
        this.query.add(LESS_THAN_OR_EQ + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators notGreaterThan(Object value) {
        this.query.add(OPPOSITE_GREATER_THAN + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterComparisonOperators notLessThan(Object value) {
        this.query.add(OPPOSITE_LESS_THAN + StringSqlUtils.SPACE + value);
        return this;
    }

    @Override
    public AfterBetweenOperatorsInConditions between() {
        this.query.add(BETWEEN);
        return this;
    }

    @Override
    public AfterLogicalOperator in(Object... values) {
        if (values.length != 0) {
            Object val = values[0];
            if (val instanceof String || val instanceof Character) {
                this.query.add(String.format(IN, StringSqlUtils.toStringSeparatorComaAndQuotes(values)));
            } else {
                this.query.add(String.format(IN, StringSqlUtils.toStringSeparatorComa(values)));
            }
        }
        return this;
    }

    @Override
    public AfterLogicalOperator like(String pattern) {
        this.query.add(LIKE + StringSqlUtils.SPACE + StringSqlUtils.toStringWithQuote(pattern));
        return this;
    }

    @Override
    public AfterBetweenOperatorsInConditions between(Object value) {
        this.query.add(BETWEEN + StringSqlUtils.SPACE + StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInConditions isNull() {
        this.query.add("is null");
        return this;
    }

    @Override
    public LogicalOperatorsInConditions isNotNull() {
        this.query.add("is not null");
        return this;
    }

    @Override
    public AfterLogicalOperator and() {
        this.query.add("and");
        return this;
    }

    @Override
    public AfterLogicalOperator or() {
        this.query.add("or");
        return this;
    }

    @Override
    public AfterLogicalOperator and(Object val) {
        this.query.add("and" + StringSqlUtils.SPACE + val);
        return this;
    }

    @Override
    public AfterLogicalOperator or(Object val) {
        this.query.add("or" + StringSqlUtils.SPACE + val);
        return this;
    }

    @Override
    public AfterLogicalOperator any(String query) {
        this.query.add("any" + StringSqlUtils.SPACE + query);
        return this;
    }

    @Override
    public AfterLogicalOperator all(String query) {
        this.query.add("all" + StringSqlUtils.SPACE + query);
        return this;
    }

}
