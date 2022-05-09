package com.github.mapper.graph;

import java.util.Map;

public enum RelationType {

    oneToEtc, manyToMany;

    public boolean isNotDefault() {
        return !this.equals(oneToEtc);
    }

    public static RelationType hashManyToMany(Map<Class<?>, SubGraph> graphs) {
        return graphs.isEmpty() ? oneToEtc : manyToMany;
    }

}
