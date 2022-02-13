package com.github.mapper.factories;

import com.github.mapper.graph.DependenciesGraph;

public class SqlSelectFactory {

    private static final String SELECT = "select ";

    private static final String FROM = "from";

    private static final String INNER_JOIN = "inner join";

//    private final Map<Class<?>, SqlMapToPojoField> fieldCash = new HashMap<>();

    private final DependenciesGraph dependenciesGraph;

    public SqlSelectFactory(DependenciesGraph dependenciesGraph) {
        this.dependenciesGraph = dependenciesGraph;
    }

    public String createSelect() {
        this.dependenciesGraph.sql();
        return null;
    }

}
