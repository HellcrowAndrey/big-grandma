package com.github.mapper.graph;

import java.util.List;

public interface SubGraphBuilder {

    SubGraphBuilder rootType(Class<?> rootType);

    SubGraphBuilder currentType(Class<?> currentType);

    SubGraphBuilder rootFieldName(String rootFieldName);

    SubGraphBuilder currentFieldName(String currentFieldName);

    SubGraphBuilder collType(Class<?> collType);

    SubGraphBuilder graphs(List<SubGraph> graphs);

    SubGraph build();

}
