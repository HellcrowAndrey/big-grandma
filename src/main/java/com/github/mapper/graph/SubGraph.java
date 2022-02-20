package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
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

    List<SubGraph> graphs; //optional

    boolean isManyToMany;

    private SubGraph(Builder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.collType = b.collType;
        this.graphs = b.graphs;
        this.isManyToMany = b.isManyToMany;
    }

    public static class Builder {

        Class<?> rootType;

        Class<?> currentType;

        String rootFieldName;

        String currentFieldName;

        Class<?> collType;

        List<SubGraph> graphs = new ArrayList<>(); //optional

        boolean isManyToMany;

        public Builder isManyToMany() {
            this.isManyToMany = Boolean.TRUE;
            return this;
        }

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
            return new SubGraph(this);
        }

    }

    public GeneralRounds restore(Map<String, Object> values, int lvl) {
        GeneralRounds result = Round.create(lvl + 1, this.currentType, EntityFactory.ofEntity(values, this.currentType));
        lvl = lvl + 1;
        if (!this.graphs.isEmpty()) {
            for (SubGraph graph : this.graphs) {
                result.addRound(graph.restore(values, lvl));
            }
        }
        return result;
    }

    public void rounds(Object root, GeneralRounds round, Map<String, Object> values) {
        Object target = round.value();
        if (round.type().equals(this.currentType)) {
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
                for (GeneralRounds nextRound : round.rounds()) {
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
            if (StringUtils.hasText(this.currentFieldName) && !this.isManyToMany) {
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
