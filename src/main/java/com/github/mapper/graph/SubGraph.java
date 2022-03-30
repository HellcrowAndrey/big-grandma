package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.mapper.utils.MapperUtils.*;

public class SubGraph {

    Class<?> rootType;

    Class<?> currentType;

    Class<?> rootCollType;

    Class<?> currentCollType;

    String rootFieldName;

    String currentFieldName;

    List<SubGraph> graphsOneToEtc; // optional

    Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); // optional

    Map<String, Field> fields; // required

    RelationType type;

    private SubGraph(OneToEtcBuilder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.currentCollType = b.currentCollType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.rootCollType = b.rootCollType;
        this.graphsOneToEtc = b.graphsOneToEtc;
        this.fields = b.fields;
        this.type = b.type;
    }

    private SubGraph(ManyToManyBuilder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.rootCollType = b.rootCollType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.currentCollType = b.currentCollType;
        this.graphsOneToEtc = b.graphsOneToEtc;
        this.graphsManyToMany = b.graphsManyToMany;
        this.fields = b.fields;
        this.type = b.type;
    }

    public static class OneToEtcBuilder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> rootCollType;

        Class<?> currentCollType;

        String rootFieldName;

        String currentFieldName;

        List<SubGraph> graphsOneToEtc = new ArrayList<>(); //optional

        Map<String, Field> fields; // required

        RelationType type;

        public OneToEtcBuilder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            return this;
        }

        public OneToEtcBuilder currentType(Class<?> currentType) {
            this.currentType = Objects.requireNonNull(currentType);
            return this;
        }

        public OneToEtcBuilder rootFieldName(String rootFieldName) {
            this.rootFieldName = rootFieldName;
            return this;
        }

        public OneToEtcBuilder currentFieldName(String currentFieldName) {
            this.currentFieldName = Objects.requireNonNull(currentFieldName);
            return this;
        }

        public OneToEtcBuilder rootCollType(Class<?> rootCollType) {
            if (!MapperUtils.isColl(rootCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", rootCollType));
            }
            this.rootCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(rootCollType));
            return this;
        }

        public OneToEtcBuilder currentCollType(Class<?> collType) {
            if (!MapperUtils.isColl(collType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", collType));
            }
            this.currentCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(collType));
            return this;
        }

        public OneToEtcBuilder graphOneToEtc(SubGraph graph) {
            this.graphsOneToEtc.add(graph);
            return this;
        }

        public OneToEtcBuilder graphs(List<SubGraph> graphs) {
            this.graphsOneToEtc = graphs;
            return this;
        }

        @Deprecated
        public SubGraph build() {
            this.type = RelationType.oneToEtc;
            return new SubGraph(this);
        }

        public SubGraph build(Map<String, String> aliases) {
            this.type = RelationType.oneToEtc;
            this.fields = MapperUtils.fields(aliases, this.rootType);
            return new SubGraph(this);
        }

    }

    public static class ManyToManyBuilder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> rootCollType;

        Class<?> currentCollType;

        String rootFieldName;

        String currentFieldName;

        List<SubGraph> graphsOneToEtc = new ArrayList<>(); //optional

        Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>(); //optional

        Map<String, Field> fields; // required

        RelationType type;

        public ManyToManyBuilder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            return this;
        }

        public ManyToManyBuilder currentType(Class<?> currentType) {
            this.currentType = Objects.requireNonNull(currentType);
            return this;
        }

        public ManyToManyBuilder rootCollType(Class<?> rootCollType) {
            if (!MapperUtils.isColl(rootCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", rootCollType));
            }
            this.rootCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(rootCollType));
            return this;
        }

        public ManyToManyBuilder rootFieldName(String rootFieldName) {
            this.rootFieldName = rootFieldName;
            return this;
        }

        public ManyToManyBuilder currentFieldName(String currentFieldName) {
            this.currentFieldName = Objects.requireNonNull(currentFieldName);
            return this;
        }

        public ManyToManyBuilder currentCollType(Class<?> collType) {
            if (!MapperUtils.isColl(collType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", collType));
            }
            this.currentCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(collType));
            return this;
        }

        public ManyToManyBuilder graphOneToEtc(SubGraph graph) {
            this.graphsOneToEtc.add(graph);
            return this;
        }

        public ManyToManyBuilder graphsOneToEtc(List<SubGraph> graphs) {
            this.graphsOneToEtc = graphs;
            return this;
        }

        public ManyToManyBuilder graphManyToMany(SubGraph outside) {
            this.graphsManyToMany.put(outside.currentType, outside);
            return this;
        }

        public ManyToManyBuilder graphsManyToMany(Map<Class<?>, SubGraph> graphsManyToMany) {
            this.graphsManyToMany.putAll(graphsManyToMany);
            return this;
        }

        @Deprecated
        public SubGraph build() {
            this.type = RelationType.manyToMany;
            return new SubGraph(this);
        }

        public SubGraph build(Map<String, String> aliases) {
            this.type = RelationType.manyToMany;
            this.fields = MapperUtils.fields(aliases, this.rootType);
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
        Round result = Round.oneToEtc(lvl + 1, this.currentType, EntityFactory.ofEntity(values, this.currentType));
        lvl = lvl + 1;
        if (!this.graphsOneToEtc.isEmpty()) {
            for (SubGraph graph : this.graphsOneToEtc) {
                result.addRound(graph.restore(values, lvl));
            }
        }
        return result;
    }

    private Round restoreManyToRound(Map<String, Object> values, int lvl) {
        Object value = EntityFactory.ofEntity(values, this.currentType);
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
                right.putLeft(round, right);
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
        Map<Round, Set<Round>> lefts = right.lefts;
        Map<String, Object> manyToManyRightFields = new HashMap<>();
        for (Round leftKey : lefts.keySet()) {
            SubGraph leftGraph = this.graphsManyToMany.get(leftKey.type);
            if (Objects.nonNull(leftGraph)) {
                String rfn = leftGraph.rootFieldName;
                String cfn = leftGraph.currentFieldName;
                Class<?> cct = leftGraph.currentCollType;
                Object leftTarget = leftKey.value;
                List<SubGraph> children = leftGraph.graphsOneToEtc;
                Set<Round> leftsRounds = lefts.getOrDefault(leftKey, new HashSet<>());
                Set<Object> leftsValues = leftsRounds.stream()
                        .map(r -> r.value)
                        .collect(Collectors.toSet());
                Map<String, Object> defaultLeftFieldValues = new HashMap<>();
                if (!children.isEmpty()) {
                    for (SubGraph g : children) {
                        g.rounds(leftTarget, leftKey, defaultLeftFieldValues);
                    }
                }
                if (!defaultLeftFieldValues.isEmpty()) {
                    MapperUtils.mapFields(defaultLeftFieldValues, leftTarget);
                }
                if (StringUtils.hasText(cfn) && Objects.nonNull(cct)) {
                    Collection<Object> leftContainer = cast(collFactory(cct));

                    leftContainer.addAll(leftsValues);
                    setFields(leftContainer, leftTarget, cfn);
                }
                Collection<Object> rightContainer = cast(
                        manyToManyRightFields.getOrDefault(rfn, collFactory(leftGraph.rootCollType))
                );
                if (rightContainer.isEmpty()) {
                    rightContainer.add(leftTarget);
                    manyToManyRightFields.put(rfn, rightContainer);
                } else {
                    rightContainer.add(leftTarget);
                }
            }
        }
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
            Collection<Object> container = cast(
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

}
