package com.github.mapper.graph;

import com.github.mapper.utils.MapperUtils;

import java.util.*;

public class SubGraph {

    Class<?> rootType;

    Class<?> currentType;

    String rootFieldName;

    String currentFieldName;

    Class<?> collType;

    List<SubGraph> graphs = new ArrayList<>(); //optional

    DependenciesGraph.Key key;

    private SubGraph(Builder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.collType = b.collType;
        this.graphs = b.graphs;
        this.key = b.key;
    }

    public static class Builder {

        Class<?> rootType;

        Class<?> currentType;

        String rootFieldName;

        String currentFieldName;

        Class<?> collType;

        List<SubGraph> graphs = new ArrayList<>(); //optional

        DependenciesGraph.Key key;

        public Builder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            return this;
        }

        public Builder currentType(Class<?> currentType) {
            this.currentType = Objects.requireNonNull(currentType);
            return this;
        }

        public Builder rootFieldName(String rootFieldName) {
            this.rootFieldName = rootFieldName;
            return this;
        }

        public Builder currentFieldName(String currentFieldName) {
            this.currentFieldName = Objects.requireNonNull(currentFieldName);
            return this;
        }

        public Builder collType(Class<?> collType) {
            if (!MapperUtils.isColl(collType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", collType));
            }
            this.collType = MapperUtils.collTypeMapper(Objects.requireNonNull(collType));
            return this;
        }

        public Builder graphs(List<SubGraph> graphs) {
            this.graphs = graphs;
            return this;
        }

        public SubGraph build() {
            this.key = new DependenciesGraph.Key(this.rootType, this.currentType, this.rootFieldName, this.currentFieldName, this.collType);
            return new SubGraph(this);
        }

    }

    public void keyLinks(DependenciesGraph.Key prevKey) {
        key.prev = prevKey;
        this.graphs.forEach(nexKey -> {
            key.next = nexKey.key;
            nexKey.keyLinks(key);
        });
    }

    private Round restore(Map<String, Object> values, List<Round> rounds, int lvl) {
        Round result = new Round(lvl + 1, this.currentType, MapperUtils.ofEntity(values, this.currentType));
        if (!this.graphs.isEmpty()) {
            for (SubGraph graph : this.graphs) {
                result.addRound(graph.restore(values, lvl + 1));
            }
        }
        return result;
    }

    public Round restore(Map<String, Object> values, int lvl) {
        Round result = new Round(lvl + 1, this.currentType, MapperUtils.ofEntity(values, this.currentType));
        lvl = lvl + 1;
        if (!this.graphs.isEmpty()) {
            for (SubGraph graph : this.graphs) {
                result.addRound(graph.restore(values, lvl));
            }
        }
        return result;
    }

    public void rounds(Round round, Map<String, Object> values) {
        Object value = round.value;
        if (round.type.equals(this.currentType)) {
            if (Objects.isNull(this.collType)) {
                values.put(this.rootFieldName, value);
            } else {
                Collection<Object> container = MapperUtils.cast(
                        values.getOrDefault(
                                this.rootFieldName, MapperUtils.collFactory(this.collType)
                        )
                );
                if (container.isEmpty()) {
                    container.add(value);
                    values.put(this.rootFieldName, container);
                } else {
                    container.add(value);
                }
            }
            if (!this.graphs.isEmpty()) {
                // TODO: 10.02.22  work about this
                for (Round r : round.rounds) {
                    for (SubGraph graph : this.graphs) {
                        graph.rounds(value, r);
                    }
                }
            }
        }
    }

    public void rounds(Object prevValue, Round round) {
        Object value = null;

        if (!this.graphs.isEmpty()) {
            for (SubGraph graph : this.graphs) {
                graph.rounds(value, round);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubGraph)) return false;
        SubGraph subGraph = (SubGraph) o;
        return Objects.equals(rootType, subGraph.rootType) &&
                Objects.equals(rootFieldName, subGraph.rootFieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootType, rootFieldName);
    }

}
