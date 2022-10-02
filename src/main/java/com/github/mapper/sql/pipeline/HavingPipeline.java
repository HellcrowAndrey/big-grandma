package com.github.mapper.sql.pipeline;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.SQLHaving;
import com.github.mapper.sql.conditions.HavingCondition;
import com.github.mapper.sql.conditions.operators.AfterBetweenOperators;
import com.github.mapper.sql.conditions.operators.ComparisonOperators;
import com.github.mapper.sql.conditions.operators.LogicalOperators;
import com.github.mapper.sql.conditions.operators.TerminalHaving;

import java.util.LinkedList;
import java.util.List;

public class HavingPipeline implements HavingCondition, ComparisonOperators, LogicalOperators, AfterBetweenOperators, TerminalHaving {

    private final List<String> operators;

    public HavingPipeline() {
        this.operators = new LinkedList<>();
    }

    @Override
    public String asString() {
        return String.join(StringSqlUtils.SPACE, this.operators);
    }

    @Override
    public ComparisonOperators count(String column) {
        this.operators.add("count(" + column + ")");
        return this;
    }

    @Override
    public ComparisonOperators min(String column) {
        this.operators.add("min(" + column + ")");
        return this;
    }

    @Override
    public ComparisonOperators max(String column) {
        this.operators.add("max(" + column + ")");
        return this;
    }

    @Override
    public ComparisonOperators avg(String column) {
        this.operators.add("avg(" + column + ")");
        return this;
    }

    @Override
    public ComparisonOperators sum(String column) {
        this.operators.add("sum(" + column + ")");
        return this;
    }

    @Override
    public LogicalOperators eq(Object value) {
        this.operators.add("=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators notEq(Object value) {
        this.operators.add("!=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators greaterThan(Object value) {
        this.operators.add(">" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators lessThan(Object value) {
        this.operators.add("<" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators greaterThanOrEq(Object value) {
        this.operators.add(">=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators lessThanOrEq(Object value) {
        this.operators.add("<=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators notGreaterThan(Object value) {
        this.operators.add("!>" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperators notLessThan(Object value) {
        this.operators.add("!<" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public AfterBetweenOperators between(Object value) {
        this.operators.add("between" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public TerminalHaving and(Object value) {
        this.operators.add("and" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public HavingCondition and() {
        this.operators.add("and");
        return this;
    }

    @Override
    public HavingCondition or() {
        this.operators.add("or");
        return this;
    }

    @Override
    public SQLHaving get() {
        return this::asString;
    }
}
