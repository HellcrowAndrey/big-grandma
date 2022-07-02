package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.MapperUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Root {

    private final Class<?> rootType;

    private final List<SubGraph> graphOneToEtc; //optional

    private final Map<String, Field> fields; // required

    public Root(Class<?> rootType, List<SubGraph> graphOneToEtc, Map<String, Field> fields) {
        this.rootType = Objects.requireNonNull(rootType);
        this.graphOneToEtc = Objects.requireNonNullElse(graphOneToEtc, new ArrayList<>());
        this.fields = Objects.requireNonNullElse(fields, new HashMap<>());
    }

    public Class<?> getRootType() {
        return rootType;
    }

    public List<SubGraph> getGraphOneToEtc() {
        return graphOneToEtc;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    private Root(Builder b) {
        this.rootType = b.rootType;
        this.graphOneToEtc = b.graphOneToEtc;
        this.fields = b.fields;
    }

    public static class Builder {

        Class<?> rootType;

        List<SubGraph> graphOneToEtc = new ArrayList<>(); //optional

        Map<String, Field> fieldNames = new HashMap<>();

        Map<String, Field> fields = new HashMap<>(); // required

        public Builder() {
        }

        public Builder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            this.fieldNames.putAll(Arrays.stream(rootType.getDeclaredFields())
                    .collect(Collectors.toMap(Field::getName, Function.identity())));
            return this;
        }

        public Builder graphOneToEtc(SubGraph graph) {
            this.graphOneToEtc.add(graph);
            return this;
        }

        public Builder graphsOneToEtc(List<SubGraph> graphOneToEtc) {
            this.graphOneToEtc.addAll(graphOneToEtc);
            return this;
        }

        public Builder aliases(Map<String, String> aliases) {
            this.fields.putAll(MapperUtils.fields(aliases, this.rootType));
            return this;
        }

        public Builder alias(String alias, String fieldName) {
            Field field = this.fieldNames.get(fieldName);
            this.fields.put(alias, field);
            return this;
        }

        public Root build() {
            if (this.fields.isEmpty()) {
                throw new IllegalArgumentException("Fields is empty pleas add fields to this class");
            }
            return new Root(this);
        }

    }

    public Round restore(Map<String, Object> values) {
        var lvl = 0;
        Object entity = EntityFactory.ofEntity(values, this.fields, this.rootType);
        Round result = Round.ofRound(lvl, this.rootType, entity);
        if (!this.graphOneToEtc.isEmpty()) {
            for (SubGraph graph : this.graphOneToEtc) {
                List<Round> previous = List.of(result);
                result.addRound(graph.restore(values,  result, lvl, previous));
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Root)) return false;
        Root root1 = (Root) o;
        return Objects.equals(rootType, root1.rootType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootType);
    }

}
