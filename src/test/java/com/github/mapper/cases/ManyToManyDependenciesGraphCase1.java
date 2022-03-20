package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.many.to.many.case1.RootManyToMany;
import com.github.mapper.models.many.to.many.case1.RoundLeft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManyToManyDependenciesGraphCase1 {

    public static DependenciesGraph graph() {
        DependenciesGraph.Root root =
                new DependenciesGraph.Root.RootManyToManyBuilder()
                        .rootType(RootManyToMany.class)
                        .relationship(new SubGraph.DefaultBuilder()
                                .rootType(RootManyToMany.class)
                                .rootFieldName("roundLefts")
                                .rootCollType(List.class)
                                .currentType(RoundLeft.class)
                                .currentFieldName("roundRights")
                                .currentCollType(List.class)
                                .build()
                        ).build();
        return new DependenciesGraph(root);
    }

    public static List<Map<String, Object>> tuples() {
        Map<String, Object> entity_1_1 = new HashMap<>();

        entity_1_1.put("ROOTMANYTOMANYID", 1L);
        entity_1_1.put("ROOTMANYTOMANYFIRSTNAME", "fn-1");
        entity_1_1.put("ROOTMANYTOMANYLASTNAME", "fn-1");

        entity_1_1.put("ROUNDLEFTID", 1L);
        entity_1_1.put("ROUNDLEFTTYPE", "t-1");

        Map<String, Object> entity_1_2 = new HashMap<>();

        entity_1_2.put("ROOTMANYTOMANYID", 1L);
        entity_1_2.put("ROOTMANYTOMANYFIRSTNAME", "fn-1");
        entity_1_2.put("ROOTMANYTOMANYLASTNAME", "fn-1");

        entity_1_2.put("ROUNDLEFTID", 2L);
        entity_1_2.put("ROUNDLEFTTYPE", "t-2");

        Map<String, Object> entity_1_3 = new HashMap<>();

        entity_1_3.put("ROOTMANYTOMANYID", 1L);
        entity_1_3.put("ROOTMANYTOMANYFIRSTNAME", "fn-1");
        entity_1_3.put("ROOTMANYTOMANYLASTNAME", "fn-1");

        entity_1_3.put("ROUNDLEFTID", 3L);
        entity_1_3.put("ROUNDLEFTTYPE", "t-3");

        Map<String, Object> entity_1_4 = new HashMap<>();

        entity_1_4.put("ROOTMANYTOMANYID", 2L);
        entity_1_4.put("ROOTMANYTOMANYFIRSTNAME", "fn-2");
        entity_1_4.put("ROOTMANYTOMANYLASTNAME", "fn-2");

        entity_1_4.put("ROUNDLEFTID", 1L);
        entity_1_4.put("ROUNDLEFTTYPE", "t-1");

        Map<String, Object> entity_1_5 = new HashMap<>();

        entity_1_5.put("ROOTMANYTOMANYID", 2L);
        entity_1_5.put("ROOTMANYTOMANYFIRSTNAME", "fn-2");
        entity_1_5.put("ROOTMANYTOMANYLASTNAME", "fn-2");

        entity_1_5.put("ROUNDLEFTID", 2L);
        entity_1_5.put("ROUNDLEFTTYPE", "t-2");

        Map<String, Object> entity_1_6 = new HashMap<>();

        entity_1_6.put("ROOTMANYTOMANYID", 3L);
        entity_1_6.put("ROOTMANYTOMANYFIRSTNAME", "fn-3");
        entity_1_6.put("ROOTMANYTOMANYLASTNAME", "fn-3");

        entity_1_6.put("ROUNDLEFTID", 3L);
        entity_1_6.put("ROUNDLEFTTYPE", "t-3");

        Map<String, Object> entity_1_7 = new HashMap<>();

        entity_1_7.put("ROOTMANYTOMANYID", 3L);
        entity_1_7.put("ROOTMANYTOMANYFIRSTNAME", "fn-3");
        entity_1_7.put("ROOTMANYTOMANYLASTNAME", "fn-3");

        entity_1_7.put("ROUNDLEFTID", 1L);
        entity_1_7.put("ROUNDLEFTTYPE", "t-1");

        return List.of(
                entity_1_1,
                entity_1_2,
                entity_1_3,
                entity_1_4,
                entity_1_5,
                entity_1_6,
                entity_1_7
        );
    }

    public static List<RootManyToMany> expect_many() {
        return new ArrayList<>();
    }

}