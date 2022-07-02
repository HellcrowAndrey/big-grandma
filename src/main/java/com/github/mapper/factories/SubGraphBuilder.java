package com.github.mapper.factories;

import com.github.mapper.graph.SubGraph;
import com.github.mapper.utils.MapperUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface SubGraphBuilder {

    void setRootType(Class<?> rootType);

    void setRootFieldName(String rootFieldName);

    void setRootCollType(Class<?> collType);

    void setCurrentType(Class<?> currentType);

    void setCurrentFieldName(String currentFieldName);

    void setCurrentCollType(Class<?> currentCollType);

    void setGraphs(List<SubGraph> graphs);

    void setGraph(SubGraph graph);

    void setAliases(Map<String, String> aliases);

    void setAlias(String alias, String fieldName);

    SubGraph build();

    class SubGraphBuilderDefault implements SubGraphBuilder {

        private Class<?> rootType;

        private Class<?> rootCollType;

        private String rootFieldName;

        private Class<?> currentType;

        private Class<?> currentCollType;

        private String currentFieldName;

        private final List<SubGraph> graphsOneToEtc = new ArrayList<>();

        private final Map<String, Field> fields = new HashMap<>();

        private final Map<String, Field> aliases = new HashMap<>();

        @Override
        public void setRootType(Class<?> rootType) {
            this.rootType = rootType;
        }

        @Override
        public void setRootFieldName(String rootFieldName) {
            this.rootFieldName = rootFieldName;
        }

        @Override
        public void setRootCollType(Class<?> rootCollType) {
            if (!MapperUtils.isColl(rootCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", rootCollType));
            }
            this.rootCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(rootCollType));
        }

        @Override
        public void setCurrentType(Class<?> currentType) {
            this.currentType = currentType;
            this.fields.putAll(Arrays.stream(this.currentType.getDeclaredFields())
                    .collect(Collectors.toMap(Field::getName, Function.identity())));
        }

        @Override
        public void setCurrentFieldName(String currentFieldName) {
            this.currentFieldName = currentFieldName;
        }

        @Override
        public void setCurrentCollType(Class<?> currentCollType) {
            if (!MapperUtils.isColl(rootCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", rootCollType));
            }
            this.rootCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(rootCollType));
        }

        @Override
        public void setGraphs(List<SubGraph> graphs) {
            this.graphsOneToEtc.addAll(graphs);
        }

        @Override
        public void setGraph(SubGraph graph) {
            this.graphsOneToEtc.add(graph);
        }

        @Override
        public void setAliases(Map<String, String> aliases) {
            this.aliases.putAll(MapperUtils.fields(aliases, this.currentType));
        }

        @Override
        public void setAlias(String alias, String fieldName) {
            this.aliases.put(alias, this.fields.get(fieldName));
        }

        @Override
        public SubGraph build() {
            return new SubGraph(
                    this.rootType,
                    this.rootCollType,
                    this.rootFieldName,
                    this.currentType,
                    this.currentCollType,
                    this.currentFieldName,
                    this.graphsOneToEtc,
                    this.aliases
            );
        }
    }

    static SubGraphBuilder subBuilder() {
        return new SubGraphBuilderDefault();
    }

}
