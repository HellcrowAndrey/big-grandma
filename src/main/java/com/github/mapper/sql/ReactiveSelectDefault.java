package com.github.mapper.sql;

import com.github.mapper.SqlDependenciesMapper;
import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.sql.key.worlds.*;
import com.github.mapper.utils.MapperUtils;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ReactiveSelectDefault implements ReactiveSelect {
    public ReactiveSelectDefault() {
    }

    @Override
    public <T> Mono<T> one() {
        KeyWorld it = collect();
        String sql = it.asString();
        QueryContext context = context();
        DatabaseClient client = context.getClient();
        Root root = buildRoot(context);
        DependenciesGraph dp = new DependenciesGraph(root);
        return client.sql(sql)
                .fetch()
                .all()
                .collectList()
                .flatMap(tuples -> SqlDependenciesMapper.defaultMap(dp).single(tuples));
    }

    @Override
    public <T> Flux<T> any() {
        KeyWorld it = collect();
        String sql = it.asString();
        QueryContext context = context();
        DatabaseClient client = context.getClient();
        Root root = buildRoot(context);
        DependenciesGraph dp = new DependenciesGraph(root);
        return client.sql(sql)
                .fetch()
                .all()
                .collectList()
                .flatMapMany(tuples -> SqlDependenciesMapper.defaultMap(dp).many(tuples));
    }

    private Root buildRoot(QueryContext context) {
        Map<QueryContext.Table, List<QueryContext.Table>> tablesLinks = context.getTableLinks();
        Root.Builder rb = new Root.Builder();
        QueryContext.Table it = tablesLinks.keySet().stream().filter(Objects::nonNull).findFirst().orElse(null);
        if (Objects.nonNull(it)) {
            rb.rootType(it.getClz())
                    .aliases(aliases(context, it));
            rb.graphsOneToEtc(buildSubGraph(context, it.getClz(), tablesLinks.get(it)));
        }
        return rb.build();
    }

    private Map<String, String> aliases(QueryContext context, QueryContext.Table next) {
        return context.getColumnByTable(next).stream()
                .collect(Collectors.toMap(QueryContext.Column::getAlias, QueryContext.Column::getFieldName));
    }

    private Predicate<QueryContext.Table> isTypeTableAndFieldEq(Field f) {
        return t -> t.getClz().equals(f.getType()) || (MapperUtils.isColl(f.getType()) && t.getClz().equals(MapperUtils.findGenericOfColl(f)));
    }

    private List<SubGraph> buildSubGraph(QueryContext context, Class<?> prevRootType, List<QueryContext.Table> tableLinks) {
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
                sb.graphs(buildSubGraph(context, currentType, lstOfLinks));
            }
            if (!secondLinksTables.isEmpty()) {
                sb.graphs(buildSubGraph(context, currentType, secondLinksTables));
            }
            subGraphs.add(sb.build());
        }
        return subGraphs;
    }

    private Field findField(List<Field> fields, Class<?> clz) {
        return fields.stream()
                .filter(field -> field.getType().equals(clz))
                .findFirst()
                .orElse(null);
    }

    private Field findByCollField(List<Field> fields, Class<?> clz) {
        return fields.stream()
                .filter(field -> {
                    Class<?> genClz = MapperUtils.findGenericOfColl(field);
                    return Objects.nonNull(genClz) && genClz.equals(clz);
                }).findFirst()
                .orElse(null);
    }

    protected abstract KeyWorld collect();

    protected abstract QueryContext context();

}
