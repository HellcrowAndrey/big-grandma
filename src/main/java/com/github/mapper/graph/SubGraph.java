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

    final Map<Class<?>, SubGraph> graphsManyToMany; // optional

    final Map<String, Field> fields; // required

    final RelationType type;

    private SubGraph(Builder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.currentCollType = b.currentCollType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.rootCollType = b.rootCollType;
        this.graphsOneToEtc = b.graphsOneToEtc;
        this.graphsManyToMany = b.graphsManyToMany;
        this.fields = b.fields;
        this.type = b.type;
    }

    public static class Builder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> rootCollType;

        Class<?> currentCollType;

        String rootFieldName;

        String currentFieldName;

        Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); //optional

        List<SubGraph> graphsOneToEtc = new ArrayList<>(); //optional

        Map<String, Field> fieldNames = new HashMap<>(); // required

        Map<String, Field> fields = new HashMap<>(); // required

        RelationType type;

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

        public Builder graphManyToMany(SubGraph outside) {
            this.graphsManyToMany.put(outside.currentType, outside);
            return this;
        }

        public Builder graphsManyToMany(Map<Class<?>, SubGraph> graphsManyToMany) {
            this.graphsManyToMany.putAll(graphsManyToMany);
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
            this.type = RelationType.hashManyToMany(this.graphsManyToMany);
            return new SubGraph(this);
        }

    }

    public Round restore(Map<String, Object> values, int lvl) {
        switch (this.type) {
            case manyToMany:
                return restoreManyToRound(values, lvl);
            case oneToEtc:
                return restoreOneToEtc(values, lvl);
            default:
                throw new IllegalArgumentException("Unsupported Relation type");
        }
    }

    private Round restoreOneToEtc(Map<String, Object> values, int lvl) {
        Round result = Round.oneToEtc(lvl + 1, this.currentType, EntityFactory.ofEntity(values, this.fields, this.currentType));
        lvl = lvl + 1;
        if (!this.graphsOneToEtc.isEmpty()) {
            for (SubGraph graph : this.graphsOneToEtc) {
                result.addRound(graph.restore(values, lvl));
            }
        }
        return result;
    }

    private Round restoreManyToRound(Map<String, Object> values, int lvl) {
        Object value = EntityFactory.ofEntity(values, this.fields, this.currentType);
        Round right = Round.manyToMany(lvl + 1, this.currentType, value);
        if (!this.graphsOneToEtc.isEmpty()) {
            var defaultLvl = lvl + 1;
            for (SubGraph graph : this.graphsOneToEtc) {
                right.addRound(graph.restore(values, defaultLvl));
            }
        }
        if (!this.graphsManyToMany.isEmpty()) {
            var defaultLvl = lvl + 1;
            this.graphsManyToMany.values().forEach(left -> {
                Round round = left.restore(values, lvl);
                List<SubGraph> leftGraphs = left.graphsOneToEtc;
                if (!leftGraphs.isEmpty()) {
                    for (SubGraph graph : leftGraphs) {
                        round.addRound(graph.restore(values, defaultLvl));
                    }
                }
                right.putLeft(round, right.value);
            });
        }
        return right;
    }

    public void rounds(Object root, Round round, Map<String, Object> values) {
        switch (this.type) {
            case oneToEtc:
                roundsOneToEtc(root, round, values);
                break;
            case manyToMany:
                roundsManyToMany(root, round, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported Relation type");
        }
    }

    public void roundsOneToEtc(Object root, Round round, Map<String, Object> values) {
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

    private void roundsManyToMany(Object root, Round right, Map<String, Object> values) {
        Object rightTarget = right.value;
        setRootValues(values, rightTarget);
        Map<Round, Set<Object>> lefts = right.lefts;
        Map<String, Object> manyToManyRightFields = new HashMap<>();
        lefts.forEach((leftKey, leftsValues) -> {
            leftsValues = MapperUtils.sameOrDefault(leftsValues, new HashSet<>());
            SubGraph leftGraph = this.graphsManyToMany.get(leftKey.type);
            if (Objects.nonNull(leftGraph)) {
                String rfn = leftGraph.rootFieldName;
                String cfn = leftGraph.currentFieldName;
                Class<?> cct = leftGraph.currentCollType;
                Object leftTarget = leftKey.value;
                List<SubGraph> children = leftGraph.graphsOneToEtc;
                Map<String, Object> defaultLeftFieldValues = new HashMap<>();
                if (!children.isEmpty()) {
                    for (SubGraph child : children) {
                        child.rounds(leftTarget, leftKey, defaultLeftFieldValues);
                    }
                }
                if (!defaultLeftFieldValues.isEmpty()) {
                    MapperUtils.mapFields(defaultLeftFieldValues, leftTarget);
                }
                if (StringUtils.hasText(cfn) && Objects.nonNull(cct)) {
                    Collection<Object> leftContainer = castToCollection(collFactory(cct));

                    leftContainer.addAll(leftsValues);
                    setFields(leftContainer, leftTarget, cfn);
                }
                Collection<Object> rightContainer = castToCollection(
                        manyToManyRightFields.getOrDefault(rfn, collFactory(leftGraph.rootCollType))
                );
                if (rightContainer.isEmpty()) {
                    rightContainer.add(leftTarget);
                    manyToManyRightFields.put(rfn, rightContainer);
                } else {
                    rightContainer.add(leftTarget);
                }
            }
        });
        if (!manyToManyRightFields.isEmpty()) {
            mapFields(manyToManyRightFields, rightTarget);
        }
        if (StringUtils.hasText(this.currentFieldName)) {
            setFields(root, rightTarget, this.currentFieldName);
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

    public Class<?> currentType() {
        return currentType;
    }

    public Class<?> currentCollType() {
        return currentCollType;
    }
}
