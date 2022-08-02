package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;

import java.lang.reflect.Field;
import java.util.*;

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
