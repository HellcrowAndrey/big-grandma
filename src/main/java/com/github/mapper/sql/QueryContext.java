package com.github.mapper.sql;

import com.github.mapper.utils.MapperUtils;
import org.springframework.r2dbc.core.DatabaseClient;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class QueryContext {

    private DatabaseClient client;

    /**
     * key tableName.column
     * value Pair key alias value fieldName
     */
    private final Map<Class<?>, Table> tables = new LinkedHashMap<>();

    private final Map<Table, List<Column>> columns = new LinkedHashMap<>();

    private final Map<Table, List<Table>> tableLinks = new LinkedHashMap<>();

    private Table rootTable;

    public QueryContext() {
    }

    public QueryContext(DatabaseClient client) {
        this.client = client;
    }

    public Table getRootTable() {
        return rootTable;
    }

    public List<Column> getColumnByTable(Table key) {
        return this.columns.get(key);
    }

    public Table getTable(Class<?> clz) {
        return this.tables.get(clz);
    }

    public DatabaseClient getClient() {
        return client;
    }

    public Map<Table, List<Column>> getColumns() {
        return columns;
    }

    public Map<Table, List<Table>> getTableLinks() {
        return tableLinks;
    }

    public void setRootTable(Class<?> clz) {
        if (Objects.isNull(clz)) {
            throw new IllegalArgumentException("Table class can't be null");
        }
        var tableName = MapperUtils.findTableName(clz);
        var alias = alias(tableName);
        this.rootTable = new Table(clz, tableName, alias);
        List<Field> fields = MapperUtils.fieldAllFields(clz);
        this.columns.put(this.rootTable, columns(alias, fields));
        this.tables.put(clz, this.rootTable);
    }

    public void addTable(Class<?> clz) {
        if (Objects.isNull(clz)) {
            throw new IllegalArgumentException("Table class can't be null");
        }
        var tableName = MapperUtils.findTableName(clz);
        var alias = alias(tableName);
        Table table = new Table(clz, tableName, alias);
        if (!this.columns.containsKey(table)) {
            List<Field> fields = MapperUtils.fieldAllFields(clz);
            this.columns.put(table, columns(alias, fields));
            List<Table> values = this.tableLinks.getOrDefault(table, new ArrayList<>());
            this.tableLinks.put(table, values);
            this.tables.put(clz, table);
            this.rootTable = table;
        }
    }

    public void addTable(Class<?> one, Class<?> two) {
        if (Objects.isNull(two) || Objects.isNull(one)) {
            throw new IllegalArgumentException("Table class can't be null");
        }
        var tableNameOne = MapperUtils.findTableName(one);
        var aliasOne = alias(tableNameOne);
        Table tableOne = new Table(one, tableNameOne, aliasOne);
        if (!this.columns.containsKey(tableOne)) {
            List<Field> fieldsOne = MapperUtils.fieldAllFields(one);
            this.columns.put(tableOne, columns(aliasOne, fieldsOne));
        }
        Table tableTwo = this.columns.keySet().stream()
                .filter(t -> t.clz.equals(two))
                .findFirst().orElse(null);
        if (Objects.isNull(tableTwo)) {
            var tableNameTwo = MapperUtils.findTableName(one);
            var aliasTwo = alias(tableNameTwo);
            tableTwo = new Table(one, tableNameOne, aliasTwo);
            List<Field> fieldsTwo = MapperUtils.fieldAllFields(one);
            this.columns.put(tableTwo, columns(aliasTwo, fieldsTwo));
        }
        addToTableLink(tableTwo, tableOne);
        this.tables.put(one, tableOne);
        this.tables.put(two, tableTwo);
    }

    private void addToTableLink(Table key, Table value) {
        List<Table> values = this.tableLinks.getOrDefault(key, new ArrayList<>());
        if (values.isEmpty()) {
            values.add(value);
            this.tableLinks.put(key, values);
        } else {
            values.add(value);
        }
    }

    private String alias(String tableName) {
        int count = 0;
        var alias = Character.toString(tableName.charAt(count));
        boolean isAnyMatch = isAnyMatch(alias);
        while (isAnyMatch) {
            ++count;
            alias += Character.toString(tableName.charAt(count));
            isAnyMatch = isAnyMatch(alias);
        }
        return alias;
    }

    private List<Column> columns(String alias, List<Field> fields) {
        return fields.stream()
                .map(field -> new Column(
                        field.getName(),
                        alias + "." + field.getName(),
                        alias + "_" + field.getName() + "_" + this.columns.size()
                )).collect(Collectors.toCollection(LinkedList::new));
    }

    private boolean isAnyMatch(String alias) {
        return this.columns.keySet().stream()
                .anyMatch(k -> k.alias.equals(alias));
    }

    public String name() {
        return this.tables.keySet().stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining("-"));
    }

    public static class Column {

        private final String fieldName;

        private final String columnName;

        private final String alias;

        public Column(String fieldName, String columnName, String alias) {
            this.fieldName = fieldName;
            this.columnName = columnName;
            this.alias = alias;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getAlias() {
            return alias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Column)) return false;
            Column column = (Column) o;
            return Objects.equals(fieldName, column.fieldName) &&
                    Objects.equals(columnName, column.columnName) &&
                    Objects.equals(alias, column.alias);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fieldName, columnName, alias);
        }

        @Override
        public String toString() {
            return "Column{" +
                    "fieldName='" + fieldName + '\'' +
                    ", columnName='" + columnName + '\'' +
                    ", alias='" + alias + '\'' +
                    '}';
        }
    }

    public static class Table {

        private final Class<?> clz;

        private final String name;

        private final String alias;

        public Table(Class<?> clz, String name, String alias) {
            this.clz = clz;
            this.name = name;
            this.alias = alias;
        }

        public Class<?> getClz() {
            return clz;
        }

        public String getName() {
            return name;
        }

        public String getAlias() {
            return alias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Table)) return false;
            Table table = (Table) o;
            return Objects.equals(clz, table.clz) &&
                    Objects.equals(name, table.name) &&
                    Objects.equals(alias, table.alias);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clz, name, alias);
        }

        @Override
        public String toString() {
            return "Table{" +
                    "clz=" + clz +
                    ", name='" + name + '\'' +
                    ", alias='" + alias + '\'' +
                    '}';
        }

    }

}
