package com.github.mapper.graph;

public enum RelationType {

    oneToEtc, manyToMany;

    public boolean isNotDefault() {
        return !this.equals(oneToEtc);
    }

}
