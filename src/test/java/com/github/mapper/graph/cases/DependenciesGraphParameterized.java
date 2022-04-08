package com.github.mapper.graph.cases;

import com.github.mapper.graph.DependenciesGraph;

import java.util.List;
import java.util.Map;

public interface DependenciesGraphParameterized {

    List<Map<String, Object>> tuples();

    List<Map<String, Object>> tuple();

    DependenciesGraph graph();

    List<Object> expect_many();

    Object expect_one();

}
