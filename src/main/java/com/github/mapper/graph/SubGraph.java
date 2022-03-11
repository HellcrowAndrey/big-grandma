package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.github.mapper.utils.MapperUtils.*;

public class SubGraph {

    Class<?> rootType;

    Class<?> currentType;

    Class<?> rootCollType;

    Class<?> currentCollType;

    String rootFieldName;

    String currentFieldName;

    List<SubGraph> graphs; // optional

    Map<Class<?>, SubGraph> relationship = new HashMap<>(); // optional

    RelationType type;

    private SubGraph(DefaultBuilder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.rootCollType = b.rootCollType;
        this.graphs = b.graphs;
        this.type = b.type;
    }

    private SubGraph(ManyToManyBuilder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.currentCollType = b.currentCollType;
        this.graphs = b.graphs;
        this.relationship = b.relationship;
        this.type = b.type;
    }

    public static class DefaultBuilder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> rootCollType;

        String rootFieldName;

        String currentFieldName;

        List<SubGraph> graphs = new ArrayList<>(); //optional

        RelationType type;

        public DefaultBuilder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            return this;
        }

        public DefaultBuilder currentType(Class<?> currentType) {
            this.currentType = Objects.requireNonNull(currentType);
            return this;
        }

        public DefaultBuilder rootFieldName(String rootFieldName) {
            this.rootFieldName = rootFieldName;
            return this;
        }

        public DefaultBuilder currentFieldName(String currentFieldName) {
            this.currentFieldName = Objects.requireNonNull(currentFieldName);
            return this;
        }

        public DefaultBuilder rootCollType(Class<?> rootCollType) {
            if (!MapperUtils.isColl(rootCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", rootCollType));
            }
            this.rootCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(rootCollType));
            return this;
        }

        public DefaultBuilder node(SubGraph graph) {
            this.graphs.add(graph);
            return this;
        }

        @Deprecated
        public DefaultBuilder graphs(List<SubGraph> graphs) {
            this.graphs = graphs;
            return this;
        }

        public SubGraph build() {
            this.type = RelationType.def;
            return new SubGraph(this);
        }

    }

    public static class ManyToManyBuilder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> currentCollType;

        String rootFieldName;

        String currentFieldName;

        List<SubGraph> graphs = new ArrayList<>(); //optional

        Map<Class<?>, SubGraph> relationship = new HashMap<>(); //optional

        RelationType type;

        public ManyToManyBuilder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            return this;
        }

        public ManyToManyBuilder currentType(Class<?> currentType) {
            this.currentType = Objects.requireNonNull(currentType);
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

        public ManyToManyBuilder node(SubGraph graph) {
            this.graphs.add(graph);
            return this;
        }

        public ManyToManyBuilder outside(SubGraph outside) {
            this.relationship.put(outside.currentType, outside);
            return this;
        }

        public SubGraph build() {
            this.type = RelationType.manyToMany;
            return new SubGraph(this);
        }

    }

    public GeneralRounds restore(Map<String, Object> values, int lvl) {
        switch (this.type) {
            case manyToMany:
                return restoreManyToRound(values, lvl);
            case def:
                return restoreDefRound(values, lvl);
            default:
                throw new IllegalArgumentException("Unsupported Relation type");
        }
    }

    private GeneralRounds restoreDefRound(Map<String, Object> values, int lvl) {
        GeneralRounds result = Round.create(lvl + 1, this.currentType, EntityFactory.ofEntity(values, this.currentType));
        lvl = lvl + 1;
        if (!this.graphs.isEmpty()) {
            for (SubGraph graph : this.graphs) {
                result.addRound(graph.restore(values, lvl));
            }
        }
        return result;
    }

    private GeneralRounds restoreManyToRound(Map<String, Object> values, int lvl) {
        Object value = EntityFactory.ofEntity(values, this.currentType);
        GeneralRounds right = Round.create(lvl + 1, this.currentType, value);
        if (!this.graphs.isEmpty()) {
            var defaultLvl = lvl + 1;
            for (SubGraph graph : this.graphs) {
                right.addRound(graph.restore(values, defaultLvl));
            }
        }
        GeneralRounds result = RoundManyToMany.create(right);
        if (!this.relationship.isEmpty()) {
            var defaultLvl = lvl + 1;
            this.relationship.values().forEach(left -> {
                GeneralRounds round = left.restore(values, lvl);
                List<SubGraph> leftGraphs = left.graphs;
                if (!leftGraphs.isEmpty()) {
                    for (SubGraph graph : leftGraphs) {
                        round.addRound(graph.restore(values, defaultLvl));
                    }
                }
                result.putLeft(round, value);
            });
        }
        return result;
    }

    public void rounds(Object root, GeneralRounds round, Map<String, Object> values) {
        switch (this.type) {
            case def:
                roundsDefault(root, round, values);
                break;
            case manyToMany:
                roundsManyToMany(root, round, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported Relation type");
        }
    }

    private void roundsDefault(Object root, GeneralRounds round, Map<String, Object> values) {
        Object target = round.value();
        if (round.type().equals(this.currentType)) {
            setRootValues(values, target);
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
            if (StringUtils.hasText(this.currentFieldName)) {
                setFields(root, target, this.currentFieldName);
            }
        }
    }

    private void roundsManyToMany(Object root, GeneralRounds round, Map<String, Object> values) {
        GeneralRounds right = round.right();
        Object rightTarget = right.value();
        Map<String, Object> defaultRightFieldValues = new HashMap<>();
        roundsDefault(rightTarget, right, defaultRightFieldValues);
        MapperUtils.mapFields(defaultRightFieldValues, rightTarget);
        setRootValues(values, rightTarget);
        Map<GeneralRounds, Set<Object>> lefts = round.lefts();
        Map<String, Object> manyToManyRightFields = new HashMap<>();
        for (GeneralRounds leftKey : lefts.keySet()) {
            SubGraph leftGraph = this.relationship.get(leftKey.type());
            if (Objects.nonNull(leftGraph)) {
                String cfn = leftGraph.currentFieldName;
                Class<?> cct = leftGraph.currentCollType;
                Object leftTarget = leftKey.value();
                Set<Object> leftsValues = lefts.get(leftKey);
                Map<String, Object> defaultLeftFieldValues = new HashMap<>();
                leftGraph.roundsDefault(leftTarget, leftKey, defaultLeftFieldValues);
                MapperUtils.mapFields(defaultLeftFieldValues, leftTarget);
                if (StringUtils.hasText(cfn) && Objects.nonNull(cct)) {
                    Collection<Object> leftContainer = cast(
                            manyToManyRightFields.getOrDefault(cfn, collFactory(cct))
                    );
                    leftContainer.addAll(leftsValues);
                    setFields(leftContainer, leftTarget, cfn);
                }
                Collection<Object> rightContainer = cast(
                        manyToManyRightFields.getOrDefault(
                                leftGraph.rootFieldName,
                                collFactory(leftGraph.rootCollType)
                        )
                );
                if (rightContainer.isEmpty()) {
                    rightContainer.add(leftTarget);
                    manyToManyRightFields.put(this.rootFieldName, rightContainer);
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
