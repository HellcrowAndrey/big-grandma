package com.github.mapper.sql;

import com.github.mapper.sql.conditions.operators.AfterNotOperator;
import com.github.mapper.sql.conditions.operators.CommonOperators;
import com.github.mapper.sql.conditions.operators.LogicalOperatorsInConditions;
import com.github.mapper.sql.pipeline.ConditionsPipeline;

public interface SQLCondition {

    String asString();

    default CommonOperators column(Class<?> entity, String column) {
        return null;
    }

    default AfterNotOperator not() {
        return null;
    }

    default LogicalOperatorsInConditions exists(String query) {
        return null;
    }

    static SQLCondition newInstance(QueryContext context) {
        return new SQLConditionDefault(context);
    }

    class SQLConditionDefault implements SQLCondition {

        private final ConditionsPipeline conditionsPipeline;

        public SQLConditionDefault(QueryContext context) {
            this.conditionsPipeline = new ConditionsPipeline(context);
        }

        @Override
        public String asString() {
            return this.conditionsPipeline.get().asString();
        }

        @Override
        public CommonOperators column(Class<?> entity, String column) {
            return this.conditionsPipeline.column(entity, column);
        }

        @Override
        public AfterNotOperator not() {
            return this.conditionsPipeline.not();
        }

        @Override
        public LogicalOperatorsInConditions exists(String query) {
            return this.conditionsPipeline.exists(query);
        }

    }

}
