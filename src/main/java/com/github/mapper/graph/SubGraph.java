package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.mapper.utils.MapperUtils.*;

public class SubGraph {

    Class<?> rootType;

    Class<?> currentType;

    Class<?> collType;

    Class<?> bottomCollType;

    String rootFieldName;

    String currentFieldName;

    String bottomFieldName;

    List<SubGraph> graphs; // optional

    SubGraph top; // depend on builder

    Map<Class<?>, SubGraph> bottoms; // depend on builder

    RelationType type;

    private SubGraph(DefaultBuilder b) {
        this.rootType = b.rootType;
        this.currentType = b.currentType;
        this.rootFieldName = b.rootFieldName;
        this.currentFieldName = b.currentFieldName;
        this.collType = b.collType;
        this.bottomCollType = b.bottomCollType;
        this.graphs = b.graphs;
        this.type = b.type;
        this.bottomFieldName = b.bottomFieldName;
    }

    private SubGraph(ManyToManyBuilder b) {
        this.top = b.top;
        this.bottoms = b.bottoms.stream()
                .collect(Collectors.toMap(bottom -> bottom.currentType, Function.identity()));
        this.type = b.type;
    }

    public static class DefaultBuilder {

        Class<?> rootType;

        Class<?> currentType;

        Class<?> collType;

        Class<?> bottomCollType;

        String rootFieldName;

        String currentFieldName;

        String bottomFieldName;

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

        public DefaultBuilder bottomFieldName(String bottomFieldName) {
            this.bottomFieldName = Objects.requireNonNull(bottomFieldName);
            return this;
        }

        public DefaultBuilder collType(Class<?> collType) {
            if (!MapperUtils.isColl(collType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", collType));
            }
            this.collType = MapperUtils.collTypeMapper(Objects.requireNonNull(collType));
            return this;
        }

        public DefaultBuilder bottomCollType(Class<?> bottomCollType) {
            if (!MapperUtils.isColl(bottomCollType)) {
                throw new IllegalArgumentException(String.format("Is not collections -> %s", bottomCollType));
            }
            this.bottomCollType = MapperUtils.collTypeMapper(Objects.requireNonNull(bottomCollType));
            return this;
        }

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

        SubGraph top;

        List<SubGraph> bottoms = new ArrayList<>();

        RelationType type;

        public ManyToManyBuilder top(SubGraph top) {
            this.top = top;
            return this;
        }

        public ManyToManyBuilder bottom(SubGraph bottom) {
            this.bottoms.add(bottom);
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
        GeneralRounds resultResult = RoundManyToMany.create(this.top.currentType);
        GeneralRounds top = Round.create(lvl + 1, this.currentType, EntityFactory.ofEntity(values, this.currentType));
        if (!this.bottoms.isEmpty()) {
            this.bottoms.values().forEach(bottom -> resultResult.putRounds(top, bottom.restoreDefRound(values, lvl)));
        }
        return resultResult;
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

    public void roundsDefault(Object root, GeneralRounds round, Map<String, Object> values) {
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
            if (StringUtils.hasText(this.currentFieldName)) {
                setFields(root, target, this.currentFieldName);
            }
        }
    }

    private void roundsManyToMany(Object root, GeneralRounds round, Map<String, Object> values) {
        var cFN = this.top.currentFieldName;
        var rFN = this.top.rootFieldName;
        var bFN = this.top.bottomFieldName;
        Class<?> tCT = this.top.collType;
        Class<?> bCT = this.top.bottomCollType;
        List<SubGraph> tGs = this.top.graphs;
        Map<GeneralRounds, Map<GeneralRounds, Set<Object>>> rawRelations = round.roundsManyToMany();
        if (Objects.nonNull(round.topType()) && Objects.nonNull(this.top) && round.topType().equals(this.top.currentType)) {
            Set<GeneralRounds> keysOfRoundsTop = rawRelations.keySet();
            for (GeneralRounds roundTop : keysOfRoundsTop) {
                Object targetTop = roundTop.value();
                Map<GeneralRounds, Set<Object>> tableOfBottoms = rawRelations.get(roundTop);
                Set<GeneralRounds> keysOfBottomRounds = tableOfBottoms.keySet();
                Collection<Object> containerTop = cast(collFactory(bCT));
                for (GeneralRounds roundsBottom : keysOfBottomRounds) {
                    Object targetBottom = roundsBottom.value();
                    SubGraph bottomGraph = this.bottoms.get(roundsBottom.type());
                    Collection<Object> containerBottom = cast(collFactory(bottomGraph.collType));
                    containerBottom.addAll(tableOfBottoms.get(roundsBottom));
                    Map<String, Object> nexValues = new HashMap<>();
                    List<SubGraph> graphs = bottomGraph.graphs;
                    setFields(containerBottom, targetBottom, bottomGraph.currentFieldName);
                    roundsSecondLvl(roundsBottom, targetBottom, nexValues, graphs);
                    containerTop.add(targetBottom);
                }
                setFields(containerTop, targetTop, bFN);
                Map<String, Object> nexValues = new HashMap<>();
                roundsSecondLvl(roundTop, targetTop, nexValues, tGs);
                if (StringUtils.hasText(cFN)) {
                    setFields(root, targetTop, cFN);
                }
                if (Objects.isNull(tCT)) {
                    if (!values.containsKey(rFN)) {
                        values.put(rFN, targetTop);
                    }
                } else {
                    Collection<Object> container = cast(values.getOrDefault(rFN, collFactory(tCT)));
                    if (container.isEmpty()) {
                        container.add(targetTop);
                        values.put(rFN, container);
                    } else {
                        container.add(targetTop);
                    }
                }
            }
        }
    }

    private void roundsSecondLvl(GeneralRounds roundsBottom, Object targetBottom, Map<String, Object> nexValues, List<SubGraph> graphs) {
        for (GeneralRounds bottomRound : roundsBottom.rounds()) {
            for (SubGraph graph : graphs) {
                graph.rounds(targetBottom, bottomRound, nexValues);
            }
        }
        for (String key : nexValues.keySet()) {
            if (isFieldExist(key, targetBottom)) {
                setFields(nexValues.get(key), targetBottom, key);
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
