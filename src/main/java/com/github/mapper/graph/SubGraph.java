package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.CollectionsUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.github.mapper.utils.MapperUtils.*;

public class SubGraph {

    private final Class<?> rootType;
    private final Class<?> rootCollType;
    private final String rootFieldName;

    private final Class<?> currentType;
    private final Class<?> currentCollType;
    private final String currentFieldName;

    private final List<SubGraph> graphsOneToEtc;
    private final Map<String, Field> fields;

    public SubGraph(Class<?> rootType,
                    Class<?> rootCollType,
                    String rootFieldName,
                    Class<?> currentType,
                    Class<?> currentCollType,
                    String currentFieldName,
                    List<SubGraph> graphsOneToEtc,
                    Map<String, Field> fields) {
        this.rootType = rootType;
        this.rootCollType = rootCollType;
        this.rootFieldName = rootFieldName;
        this.currentType = currentType;
        this.currentCollType = currentCollType;
        this.currentFieldName = currentFieldName;
        this.graphsOneToEtc = graphsOneToEtc;
        this.fields = fields;
    }

    public Round restore(Map<String, Object> values, Round parent, int lvl, List<Round> previous) {
        lvl = lvl + 1;
        Object entity = EntityFactory.ofEntity(values, this.fields, this.currentType);
        Optional<Round> tmp = findPrevRound(previous, entity);
        Round result;
        if (tmp.isPresent()) {
            Round round = tmp.get();
            result = Round.ofRound(lvl, round.type, round.value);
        } else {
            result = Round.ofRound(lvl, this.currentType, entity);
            if (!this.graphsOneToEtc.isEmpty()) {
                for (SubGraph graph : this.graphsOneToEtc) {
                    result.addRound(graph.restore(
                            values, result, lvl,
                            CollectionsUtils.newArray(previous, result))
                    );
                }
            }
        }
        return result;
    }

    private Optional<Round> findPrevRound(List<Round> previous, Object entity) {
        return previous.stream()
                .filter(r -> r.value.equals(entity))
                .findFirst();
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

    public boolean isHasBidirectionalManyToMany() {

        return false;
    }

    public Class<?> getRootType() {
        return rootType;
    }

    public Class<?> getRootCollType() {
        return rootCollType;
    }

    public String getRootFieldName() {
        return rootFieldName;
    }

    public Class<?> getCurrentType() {
        return currentType;
    }

    public Class<?> getCurrentCollType() {
        return currentCollType;
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
