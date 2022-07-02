package com.github.mapper.factories;

import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.utils.MapperUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RootBuilder {

    void setRootType(Class<?> clz);

    void setGraphsOneToEtc(List<SubGraph> graphs);

    void setGraphOneToEtc(SubGraph graph);

    void setFields(Map<String, Field> fields);

    void setAliases(Map<String, String> aliases);

    void setAlias(String alias, String aliases);

    Root build();

    final class DefaultRootBuilder implements RootBuilder {

        private Class<?> rootType;

        private final List<SubGraph> graphs = new ArrayList<>();

        private final Map<String, Field> fields = new HashMap<>();

        private final Map<String, Field> aliases = new HashMap<>();

        @Override
        public void setRootType(Class<?> rootType) {
            this.rootType = rootType;
        }

        @Override
        public void setGraphsOneToEtc(List<SubGraph> graphs) {
            this.graphs.addAll(graphs);
        }

        @Override
        public void setGraphOneToEtc(SubGraph graph) {
            this.graphs.add(graph);
        }

        @Override
        public void setFields(Map<String, Field> fields) {
            this.fields.putAll(fields);
        }

        @Override
        public void setAliases(Map<String, String> aliases) {
            this.aliases.putAll(MapperUtils.fields(aliases, this.rootType));
        }

        @Override
        public void setAlias(String alias, String field) {
            this.aliases.put(alias, this.fields.get(field));
        }

        @Override
        public Root build() {
            return new Root(
                    this.rootType,
                    this.graphs,
                    this.aliases
            );
        }
    }

    static RootBuilder rootBuilder() {
        return new DefaultRootBuilder();
    }

}
