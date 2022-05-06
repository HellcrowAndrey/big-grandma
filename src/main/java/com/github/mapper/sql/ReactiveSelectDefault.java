package com.github.mapper.sql;

import com.github.mapper.entityies.Post;
import com.github.mapper.SqlDependenciesMapper;
import com.github.mapper.StringSqlUtils;
import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.sql.key.worlds.*;
import com.github.mapper.utils.MapperUtils;
import org.springframework.data.util.Pair;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ReactiveSelectDefault implements ReactiveSelect {

    private final DatabaseClient client;

    public ReactiveSelectDefault(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public <T> Mono<T> one() {
//        KeyWorld it = collect();
//        StringBuilder start = new StringBuilder(it.getText());
//        if (it instanceof DistinctDefault || it instanceof TopDefault) {
//            start.append(it.getText())
//                    .append(StringSqlUtils.SPACE);
//        }
//        it = it.next();
//        StringBuilder end = new StringBuilder();
//        Root.RootBuilder root = new Root.RootBuilder();
//        Map<String, Pair<String, Field>> allFields = new LinkedHashMap<>();
//        while (it != null) {
//            if (it instanceof FromDefault) {
//                FromDefault fd = (FromDefault) it;
//                Class<?> clz = fd.getPojoType();
//                allFields.putAll(MapperUtils.fieldNames(clz));
//                Map<String, String> fields = MapperUtils.fieldAsAliases(clz);
//                root.rootType(fd.getPojoType());
//                Map<String, String> aliases = fields.keySet().stream()
//                        .collect(Collectors.toMap(fields::get, Function.identity()));
//                root.aliases(aliases);
//            }
//            if (it instanceof JoinDefault) {
//                JoinDefault jd = (JoinDefault) it;
//                Class<?> from = jd.getFromPojoType();
//                allFields.putAll(MapperUtils.fieldNames(from));
//                Map<String, String> fields = MapperUtils.fieldAsAliases(from);
//                Map<String, String> aliases = fields.keySet().stream()
//                        .collect(Collectors.toMap(fields::get, Function.identity()));
//                root.graphOneToEtc(
//                        new SubGraph.OneToEtcBuilder()
//                                .rootType(Post.class)
//                                .rootFieldName("user")
//                                .currentType(from)
//                                .aliases(aliases)
//                                .build()
//                );
//            }
//            if (it instanceof LeftJoinDefault) {
//                System.out.println(((LeftJoinDefault) it).getToPojoType());
//            }
//            end.append(it.getText()).append(StringSqlUtils.SPACE);
//            it = it.next();
//        }
//        String sql = start.append(
//                StringSqlUtils.toStringSeparatorComa(
//                        allFields.keySet().stream().map(key -> key + " as " + allFields.get(key).getFirst()).toArray())
//        ).append(StringSqlUtils.SPACE).append(end).toString();
//        DependenciesGraph dp = new DependenciesGraph(root.build());
//        return this.client.sql(sql)
//                .fetch()
//                .all()
//                .collectList()
//                .flatMap(tuples -> SqlDependenciesMapper.defaultMap(dp).single(tuples));
        return Mono.empty();
    }

    @Override
    public <T> Flux<T> any() {
//        KeyWorld it = collect();
//        StringBuilder start = new StringBuilder(it.getText());
//        if (it instanceof DistinctDefault || it instanceof TopDefault) {
//            start.append(it.getText())
//                    .append(StringSqlUtils.SPACE);
//        }
//        it = it.next();
//        StringBuilder end = new StringBuilder();
//        Root.RootBuilder root = new Root.RootBuilder();
//        Map<String, Pair<String, Field>> allFields = new LinkedHashMap<>();
//        while (it != null) {
//            if (it instanceof FromDefault) {
//                FromDefault fd = (FromDefault) it;
//                Class<?> clz = fd.getPojoType();
//                allFields.putAll(MapperUtils.fieldNames(clz));
//                Map<String, String> fields = MapperUtils.fieldAsAliases(clz);
//                root.rootType(fd.getPojoType());
//                Map<String, String> aliases = fields.keySet().stream()
//                        .collect(Collectors.toMap(fields::get, Function.identity()));
//                root.aliases(aliases);
//            }
//            if (it instanceof JoinDefault) {
//                JoinDefault jd = (JoinDefault) it;
//                Class<?> from = jd.getFromPojoType();
//                allFields.putAll(MapperUtils.fieldNames(from));
//                Map<String, String> fields = MapperUtils.fieldAsAliases(from);
//                Map<String, String> aliases = fields.keySet().stream()
//                        .collect(Collectors.toMap(fields::get, Function.identity()));
//                if (count == 0) {
//                    root.graphOneToEtc(
//                            new SubGraph.OneToEtcBuilder()
//                                    .rootType(Post.class)
//                                    .rootFieldName("user")
//                                    .currentType(from)
//                                    .aliases(aliases)
//                                    .build()
//                    );
//                }
//                if (count == 1) {
//                    allFields.putAll(MapperUtils.fieldNames(jd.getToPojoType()));
//                    Map<String, String> r = MapperUtils.fieldAsAliases(jd.getToPojoType());
//                    Map<String, String> aa = MapperUtils.fieldAsAliases(jd.getToPojoType())
//                            .keySet().stream()
//                            .collect(Collectors.toMap(r::get, Function.identity()));
//                    root.graphOneToEtc(
//                            new SubGraph.OneToEtcBuilder()
//                                    .rootType(Post.class)
//                                    .rootFieldName("comments")
//                                    .rootCollType(List.class)
//                                    .currentType(jd.getToPojoType())
//                                    .currentFieldName("post")
//                                    .aliases(aa)
//                                    .build()
//                    );
//                }
//                ++count;
//            }
//            if (it instanceof LeftJoinDefault) {
//                System.out.println(((LeftJoinDefault) it).getToPojoType());
//            }
//            end.append(it.getText()).append(StringSqlUtils.SPACE);
//            it = it.next();
//        }
//        String sql = start.append(
//                StringSqlUtils.toStringSeparatorComa(
//                        allFields.keySet().stream().map(key -> key + " as " + allFields.get(key).getFirst()).toArray())
//        ).append(StringSqlUtils.SPACE).append(end).toString();
//        System.out.println(sql);
//        DependenciesGraph dp = new DependenciesGraph(root.build());
//        return this.client.sql(sql)
//                .fetch()
//                .all()
//                .collectList()
//                .flatMapMany(tuples -> SqlDependenciesMapper.defaultMap(dp).many(tuples));
        return Flux.empty();
    }

    protected abstract KeyWorld collect();

}
