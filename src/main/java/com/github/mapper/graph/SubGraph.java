package com.github.mapper.graph;

import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.github.mapper.utils.MapperUtils.*;

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

    public void rounds(Object root, Round round, Map<String, Object> values) {
        Object target = round.value;
        if (round.type.equals(this.currentType)) {
            if (Objects.isNull(this.collType)) {
                values.put(this.rootFieldName, target);
            } else {
                Collection<Object> container = cast(
                        values.getOrDefault(this.rootFieldName, collFactory(this.collType))
                );
                if (container.isEmpty()) {
                    container.add(target);
                    values.put(this.rootFieldName, container);
                } else {
                    container.add(target);
                }
            }
            if (!this.graphs.isEmpty()) {
                Map<String, Object> nexValues = new HashMap<>();
                for (Round nextRound : round.rounds) {
                    for (SubGraph graph : this.graphs) {
                        graph.rounds(target, nextRound, nexValues);
                    }
                }
                for (String key : nexValues.keySet()) {
                    if (isFieldExist(key, target)) {
                        setFields(nexValues.get(key), target, key);
                    }
                }
            }
            if (StringUtils.hasText(this.currentFieldName)) {
                setFields(root, target, this.currentFieldName);
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
