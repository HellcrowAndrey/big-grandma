package com.github.mapper.sql;

public interface ColumnName {

    static ColumnName columnName(String columnName) {
        return new Name(columnName);
    }

    String get();

    class Name implements ColumnName {

        private String name;

        public Name(String name) {
            this.name = name;
        }

        @Override
        public String get() {
            return this.name;
        }
    }

}
