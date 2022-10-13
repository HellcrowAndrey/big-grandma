package com.github.mapper.sql;

import com.github.mapper.sql.conditions.operators.ComparisonOperators;
import com.github.mapper.sql.pipeline.HavingPipeline;

public interface SQLHaving {

    String asString();

    default ComparisonOperators count(String column) {
        return new HavingPipeline().count(column);
    }

    default ComparisonOperators min(String column) {
        return new HavingPipeline().min(column);
    }

    default ComparisonOperators max(String column) {
        return new HavingPipeline().max(column);
    }

    default ComparisonOperators avg(String column) {
        return new HavingPipeline().avg(column);
    }

    default ComparisonOperators sum(String column) {
        return new HavingPipeline().sum(column);
    }

    static SQLHaving newInstance() {
        return new SQLHavingDefault();
    }

    class SQLHavingDefault implements SQLHaving {

        private final HavingPipeline pipeline;

        public SQLHavingDefault() {
            this.pipeline = new HavingPipeline();
        }

        @Override
        public String asString() {
            return this.pipeline.asString();
        }

        @Override
        public ComparisonOperators count(String column) {
            return this.pipeline.count(column);
        }

        @Override
        public ComparisonOperators min(String column) {
            return this.pipeline.max(column);
        }

        @Override
        public ComparisonOperators max(String column) {
            return this.pipeline.max(column);
        }

        @Override
        public ComparisonOperators sum(String column) {
            return this.pipeline.sum(column);
        }
    }

}
