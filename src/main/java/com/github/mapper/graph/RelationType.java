package com.github.mapper.graph;

public enum RelationType {

    def, manyToMany;

    public boolean isNotDefault() {
        return !this.equals(def);
    }

}
