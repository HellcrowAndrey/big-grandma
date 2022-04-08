package com.github.mapper.graph;

import com.github.mapper.factories.EntityFactory;
import com.github.mapper.utils.CollectionsUtils;
import com.github.mapper.utils.MapperUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.mapper.utils.MapperUtils.*;

public class Root {

    final Class<?> rootType;

    final Map<Class<?>, SubGraph> graphsManyToMany; //optional

    final List<SubGraph> graphOneToEtc; //optional

    final Map<String, Field> fields; // required

    final RelationType type;

    @Deprecated
    public Root(Class<?> rootType, List<SubGraph> graphOneToEtc) {
        this.rootType = Objects.requireNonNull(rootType);
        this.graphOneToEtc = Objects.requireNonNullElse(graphOneToEtc, new ArrayList<>());
        this.type = RelationType.oneToEtc;
        this.graphsManyToMany = new HashMap<>();
        this.fields = new HashMap<>();
    }

    private Root(RootBuilder b) {
        this.rootType = b.rootType;
        this.graphOneToEtc = b.graphOneToEtc;
        this.graphsManyToMany = new HashMap<>();
        this.fields = b.fields;
        this.type = b.type;
    }

    private Root(RootManyToManyBuilder b) {
        this.rootType = b.rootType;
        this.graphsManyToMany = b.graphsManyToMany;
        this.graphOneToEtc = b.graphOneToEtc;
        this.fields = b.fields;
        this.type = b.type;
    }

    public static class RootBuilder {

        Class<?> rootType;

        List<SubGraph> graphOneToEtc = new ArrayList<>(); //optional

        Map<String, Field> fieldNames = new HashMap<>();

        Map<String, Field> fields = new HashMap<>(); // required

        RelationType type;

        public RootBuilder() {
            this.type = RelationType.oneToEtc;
        }

        public RootBuilder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            this.fieldNames.putAll(Arrays.stream(rootType.getDeclaredFields())
                    .collect(Collectors.toMap(Field::getName, Function.identity())));
            return this;
        }

        public RootBuilder graphOneToEtc(SubGraph graph) {
            this.graphOneToEtc.add(graph);
            return this;
        }

        public RootBuilder graphsOneToEtc(List<SubGraph> graphOneToEtc) {
            this.graphOneToEtc.addAll(graphOneToEtc);
            return this;
        }

        public RootBuilder aliases(Map<String, String> aliases) {
            this.fields.putAll(MapperUtils.fields(aliases, this.rootType));
            return this;
        }

        public RootBuilder alias(String alias, String fieldName) {
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

    public static class RootManyToManyBuilder {

        Class<?> rootType;

        Map<Class<?>, SubGraph> graphsManyToMany = new HashMap<>();

        List<SubGraph> graphOneToEtc = new ArrayList<>(); //optional

        Map<String, Field> fieldNames = new HashMap<>();

        Map<String, Field> fields = new HashMap<>(); // required

        RelationType type;

        public RootManyToManyBuilder() {
            this.type = RelationType.manyToMany;
        }

        public RootManyToManyBuilder rootType(Class<?> rootType) {
            this.rootType = Objects.requireNonNull(rootType);
            this.fieldNames.putAll(Arrays.stream(rootType.getDeclaredFields())
                    .collect(Collectors.toMap(Field::getName, Function.identity())));
            return this;
        }

        public RootManyToManyBuilder graphsManyToMany(SubGraph outside) {
            this.graphsManyToMany.put(outside.currentType, outside);
            return this;
        }

        public RootManyToManyBuilder graphOneToEtc(SubGraph graph) {
            this.graphOneToEtc.add(graph);
            return this;
        }

        public RootManyToManyBuilder graphsOneToEtc(List<SubGraph> graphOneToEtc) {
            this.graphOneToEtc.addAll(graphOneToEtc);
            return this;
        }

        public RootManyToManyBuilder aliases(Map<String, String> aliases) {
            this.fields.putAll(MapperUtils.fields(aliases, this.rootType));
            return this;
        }

        public RootManyToManyBuilder alias(String alias, String fieldName) {
            if (!StringUtils.hasText(alias) || !StringUtils.hasText(fieldName)) {
                throw new IllegalArgumentException(String.format("Alias -> %s or field name -> %s", alias, fieldName));
            }
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

    public RootRound toRootRound(Map<String, Object> nonMappedValues, int lvl) {
        switch (this.type) {
            case oneToEtc:
                return restoreOneToEtc(nonMappedValues);
            case manyToMany:
                return restoreManyToMany(nonMappedValues, lvl);
            default:
                throw new IllegalArgumentException("Unsupported Relation type");
        }
    }

    private RootRound restoreOneToEtc(Map<String, Object> nonMappedValues) {
        return new RootRound(EntityFactory.ofEntity(nonMappedValues, this.fields, this.rootType), nonMappedValues) {
            @Override
            void putLeft(Round left, Object value) {
                throw new UnsupportedOperationException();
            }

            @Override
            void collectRoundLeft(Map<Object, Object> rightValues, Object roundRootVal, Map<Round, Set<Object>> newLefts) {
                throw new UnsupportedOperationException();
            }

            @Override
            boolean hashManyToMany() {
                return Boolean.FALSE;
            }
        };
    }

    private RootRound restoreManyToMany(Map<String, Object> nonMappedValues, int lvl) {
        Object value = EntityFactory.ofEntity(nonMappedValues, this.fields, this.rootType);
        RootRound right = manyToManyRoot(value, nonMappedValues);
        if (!this.graphsManyToMany.isEmpty()) {
            var defaultLvl = lvl + 1;
            this.graphsManyToMany.values().forEach(left -> {
                Round round = left.restore(nonMappedValues, lvl);
                List<SubGraph> leftGraphs = left.graphsOneToEtc;
                if (!leftGraphs.isEmpty()) {
                    for (SubGraph graph : leftGraphs) {
                        round.addRound(graph.restore(nonMappedValues, defaultLvl));
                    }
                }
                right.putLeft(round, right.value);
            });
        }
        return right;
    }

    private RootRound manyToManyRoot(Object value, Map<String, Object> nonMappedValues) {
        return new RootRound(value, nonMappedValues) {
            @Override
            void putLeft(Round left, Object value) {
                this.lefts.put(left, CollectionsUtils.genericSet(value));
            }

            @Override
            void collectRoundLeft(Map<Object, Object> rightValues, Object roundRootVal, Map<Round, Set<Object>> newLefts) {
                for (Round left : newLefts.keySet()) {
                    Set<Object> objects = newLefts.get(left).stream()
                            .map(rightValues::get)
                            .collect(Collectors.toSet());
                    if (this.lefts.containsKey(left)) {
                        this.lefts.get(left).addAll(objects);
                        findRound(this.lefts.keySet(), left)
                                .ifPresent(left::collectRounds);
                    } else {
                        if (this.value.equals(roundRootVal)) {
                            this.lefts.put(left, objects);
                        }
                    }
                }
            }

            @Override
            boolean hashManyToMany() {
                return Boolean.TRUE;
            }
        };
    }

    public void roundsManyToMany(RootRound round) {
        Object rightTarget = round.value;
        Map<Round, Set<Object>> lefts = round.lefts;
        Map<String, Object> manyToManyRightFields = new HashMap<>();
        lefts.forEach((leftKey, leftsValues) -> {
            leftsValues = MapperUtils.sameOrDefault(leftsValues, new HashSet<>());
            SubGraph leftGraph = this.graphsManyToMany.get(leftKey.type);
            if (Objects.nonNull(leftGraph)) {
                String rfn = leftGraph.rootFieldName;
                String cfn = leftGraph.currentFieldName;
                Class<?> cct = leftGraph.currentCollType;
                List<SubGraph> children = leftGraph.graphsOneToEtc;
                Object leftTarget = leftKey.value;
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
