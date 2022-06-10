package com.github.mapper.graph;

import java.util.*;

public abstract class RootRound {

    Object value;

    List<Map<String, Object>> nonMappedValues = new ArrayList<>();

    RootRound(Object value, Map<String, Object> nonMappedValues) {
        this.value = value;
        this.nonMappedValues.add(nonMappedValues);
    }

    public void addAll(List<Map<String, Object>> nonMappedValues) {
        this.nonMappedValues.addAll(nonMappedValues);
    }

    public Optional<Round> findRound(Set<Round> keys, Round newRound) {
        return keys.stream().filter(key -> key.equals(newRound)).findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootRound)) return false;
        RootRound rootRound = (RootRound) o;
        return Objects.equals(value, rootRound.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
