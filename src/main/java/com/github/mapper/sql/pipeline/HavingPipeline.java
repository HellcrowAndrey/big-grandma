package com.github.mapper.sql.pipeline;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.SQLHaving;
import com.github.mapper.sql.conditions.HavingCondition;
import com.github.mapper.sql.conditions.operators.AfterBetweenOperators;
import com.github.mapper.sql.conditions.operators.ComparisonOperators;
import com.github.mapper.sql.conditions.operators.ComparisonOperatorsInHaving;
import com.github.mapper.sql.conditions.operators.LogicalOperatorsInHaving;
import com.github.mapper.sql.conditions.TerminalHaving;

import java.util.LinkedList;
import java.util.List;

public class HavingPipeline implements HavingCondition, ComparisonOperatorsInHaving, LogicalOperatorsInHaving, AfterBetweenOperators, TerminalHaving {

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
    public LogicalOperatorsInHaving eq(Object value) {
        this.operators.add("=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving notEq(Object value) {
        this.operators.add("!=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving greaterThan(Object value) {
        this.operators.add(">" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving lessThan(Object value) {
        this.operators.add("<" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving greaterThanOrEq(Object value) {
        this.operators.add(">=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving lessThanOrEq(Object value) {
        this.operators.add("<=" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving notGreaterThan(Object value) {
        this.operators.add("!>" +
                StringSqlUtils.SPACE +
                StringSqlUtils.toTextOrSame(value));
        return this;
    }

    @Override
    public LogicalOperatorsInHaving notLessThan(Object value) {
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
