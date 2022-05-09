package com.github.mapper.factories;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.sql.QueryContext;
import com.github.mapper.utils.MapperUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DependenciesGraphFactory {

    public static final Map<String, DependenciesGraph> graphs = new HashMap<>();

    public static DependenciesGraph dependenciesGraph(QueryContext context) {
        var hash = context.name();
        DependenciesGraph graph = graphs.get(hash);
        if (Objects.isNull(graph)) {
            graph = new DependenciesGraph(buildRoot(context));
            graphs.put(hash, graph);
        }
        return graph;
    }

    private static Root buildRoot(QueryContext context) {
        Map<QueryContext.Table, List<QueryContext.Table>> tablesLinks = context.getTableLinks();
        Root.Builder rb = new Root.Builder();
        QueryContext.Table rootTable = context.getRootTable();
        if (Objects.nonNull(rootTable)) {
            rb.rootType(rootTable.getClz()).aliases(aliases(context, rootTable));
            List<SubGraph> graphs = buildSubGraph(context, rootTable.getClz(), tablesLinks.get(rootTable));
            graphs.stream()
                    .filter(g -> Objects.nonNull(g.currentCollType()))
                    .forEach(rb::graphsManyToMany);
            graphs.stream()
                    .filter(g -> Objects.isNull(g.currentCollType()))
                    .forEach(rb::graphOneToEtc);
        }
        return rb.build();
    }

    private static Map<String, String> aliases(QueryContext context, QueryContext.Table next) {
        return context.getColumnByTable(next).stream()
                .collect(Collectors.toMap(QueryContext.Column::getAlias, QueryContext.Column::getFieldName));
    }

    private static Predicate<QueryContext.Table> isTypeTableAndFieldEq(Field f) {
        return t -> t.getClz().equals(f.getType()) || (MapperUtils.isColl(f.getType()) && t.getClz().equals(MapperUtils.findGenericOfColl(f)));
    }

    private static List<SubGraph> buildSubGraph(QueryContext context, Class<?> prevRootType, List<QueryContext.Table> tableLinks) {
        Map<QueryContext.Table, List<QueryContext.Table>> tablesLinks = context.getTableLinks();
        List<SubGraph> subGraphs = new LinkedList<>();
        for (QueryContext.Table link : tableLinks) {
            SubGraph.Builder sb = new SubGraph.Builder();
            List<Field> prevFields = MapperUtils.fieldFields(prevRootType);
            Class<?> currentType = link.getClz();
            Field prevField = findField(prevFields, currentType);
            if (Objects.isNull(prevField)) {
                prevField = findByCollField(prevFields, currentType);
                sb.rootCollType(prevField.getType());
            }
            List<Field> currentFields = MapperUtils.fieldFields(currentType);
            Field currentField = findField(currentFields, prevRootType);
            if (Objects.isNull(currentField)) {
                currentField = findByCollField(currentFields, prevRootType);
                if (Objects.nonNull(currentField)) {
                    sb.currentCollType(prevField.getType());
                }
            }
            List<QueryContext.Table> secondLinksTables = currentFields.stream()
                    .flatMap(f -> tablesLinks.values().stream()
                            .flatMap(Collection::stream)
                            .filter(t -> isTypeTableAndFieldEq(f).test(t)))
                    .collect(Collectors.toList());
            sb.rootType(prevRootType)
                    .rootFieldName(prevField.getName())
                    .currentType(currentType)
                    .currentFieldName(Objects.nonNull(currentField) ? currentField.getName() : "")
                    .aliases(aliases(context, link));
            List<QueryContext.Table> lstOfLinks = tablesLinks.get(link);
            if (Objects.nonNull(lstOfLinks)) {
                List<SubGraph> graphs = buildSubGraph(context, currentType, lstOfLinks);
                graphs.stream()
                        .filter(g -> Objects.nonNull(g.currentCollType()))
                        .forEach(sb::graphManyToMany);
                graphs.stream()
                        .filter(g -> Objects.isNull(g.currentCollType()))
                        .forEach(sb::graphOneToEtc);
                sb.graphs(buildSubGraph(context, currentType, lstOfLinks));
            }
            if (!secondLinksTables.isEmpty()) {
                sb.graphs(buildSubGraph(context, currentType, secondLinksTables));
            }
            subGraphs.add(sb.build());
        }
        return subGraphs;
    }

    private static Field findField(List<Field> fields, Class<?> clz) {
        return fields.stream()
                .filter(field -> field.getType().equals(clz))
                .findFirst()
                .orElse(null);
    }

    private static Field findByCollField(List<Field> fields, Class<?> clz) {
        return fields.stream()
                .filter(field -> {
                    Class<?> genClz = MapperUtils.findGenericOfColl(field);
                    return Objects.nonNull(genClz) && genClz.equals(clz);
                }).findFirst()
                .orElse(null);
    }

}
