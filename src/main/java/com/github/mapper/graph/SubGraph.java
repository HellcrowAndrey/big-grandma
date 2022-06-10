package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.mapper.utils.MapperUtils.*;

public class SubGraph {

    final Class<?> rootType;

    final Class<?> currentType;

    final Class<?> rootCollType;

    final Class<?> currentCollType;

    final String rootFieldName;

    final String currentFieldName;

    final List<SubGraph> graphsOneToEtc; // optional

    final Map<String, Field> fields; // required

    private SubGraph(Builder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.currentCollType = b.currentCollType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.rootCollType = b.rootCollType;
        this.graphsOneToEtc = b.graphsOneToEtc;
        this.fields = b.fields;
    }

    public static class Builder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> rootCollType;

        Class<?> currentCollType;

        String rootFieldName;

        String currentFieldName;

        List<SubGraph> graphsOneToEtc = new ArrayList<>(); //optional

        Map<String, Field> fieldNames = new HashMap<>(); // required

        Map<String, Field> fields = new HashMap<>(); // required

        public Builder() {
        }

        public Builder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            return this;
        }

        public Builder currentType(Class<?> currentType) {
            this.currentType = Objects.requireNonNull(currentType);
            this.fieldNames.putAll(Arrays.stream(currentType.getDeclaredFields())
                    .collect(Collectors.toMap(Field::getName, Function.identity())));
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

        public Builder rootCollType(Class<?> rootCollType) {
            if (!MapperUtils.isColl(rootCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", rootCollType));
            }
            this.rootCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(rootCollType));
            return this;
        }

        public Builder currentCollType(Class<?> collType) {
            if (!MapperUtils.isColl(collType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", collType));
            }
            this.currentCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(collType));
            return this;
        }

        public Builder graphOneToEtc(SubGraph graph) {
            this.graphsOneToEtc.add(graph);
            return this;
        }

        public Builder graphs(List<SubGraph> graphs) {
            this.graphsOneToEtc = graphs;
            return this;
        }

        public Builder aliases(Map<String, String> aliases) {
            this.fields.putAll(MapperUtils.fields(aliases, this.currentType));
            return this;
        }

        public Builder alias(String alias, String fieldName) {
            Field field = this.fieldNames.get(fieldName);
            this.fields.put(alias, field);
            return this;
        }

        public SubGraph build() {
            if (this.fields.isEmpty()) {
                throw new IllegalArgumentException("Fields is empty pleas add fields to this class");
            }
            return new SubGraph(this);
        }

    }

    public Round restore(Map<String, Object> values, int lvl) {
        Round result = Round.ofRound(lvl + 1, this.currentType, EntityFactory.ofEntity(values, this.fields, this.currentType));
        lvl = lvl + 1;
        if (!this.graphsOneToEtc.isEmpty()) {
            for (SubGraph graph : this.graphsOneToEtc) {
                result.addRound(graph.restore(values, lvl));
            }
        }
        return result;
    }

    public void rounds(Object root, Round round, Map<String, Object> values) {
        Object target = round.value;
        if (round.type.equals(this.currentType)) {
            setRootValues(values, target);
            if (!this.graphsOneToEtc.isEmpty()) {
                Map<String, Object> nexValues = new HashMap<>();
                for (Round nextRound : round.roundsOneToEtc) {
                    for (SubGraph graph : this.graphsOneToEtc) {
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

    private void setRootValues(Map<String, Object> values, Object target) {
        if (Objects.isNull(this.rootCollType)) {
            values.put(this.rootFieldName, target);
        } else {
            Collection<Object> container = castToCollection(
                    values.getOrDefault(this.rootFieldName, collFactory(this.rootCollType))
            );
            if (container.isEmpty()) {
                container.add(target);
                values.put(this.rootFieldName, container);
            } else {
                container.add(target);
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

    public Class<?> getCurrentCollType() {
        return currentCollType;
    }

    public Class<?> getRootType() {
        return rootType;
    }

    public Class<?> getCurrentType() {
        return currentType;
    }

    public Class<?> getRootCollType() {
        return rootCollType;
    }

    public String getRootFieldName() {
        return rootFieldName;
    }

    public String getCurrentFieldName() {
        return currentFieldName;
    }

    public List<SubGraph> getGraphsOneToEtc() {
        return graphsOneToEtc;
    }

    public Map<String, Field> getFields() {
        return fields;
    }
}
