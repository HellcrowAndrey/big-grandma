package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.many.to.many.case1.RootManyToMany;
import com.github.mapper.models.many.to.many.case1.Round1Lvl1MTM;
import com.github.mapper.models.many.to.many.case1.RoundLeft;
import com.github.mapper.models.many.to.many.case1.RoundLeft2;

import java.util.*;

public class ManyToManyDependenciesGraphCase1 {

    public static DependenciesGraph graph() {
        Root root =
                new Root.Builder()
                        .rootType(RootManyToMany.class)
                        .alias("ROOTMANYTOMANYID", "id")
                        .alias("ROOTMANYTOMANYFIRSTNAME", "firstName")
                        .alias("ROOTMANYTOMANYLASTNAME", "lastName")
                        .graphsManyToMany(new SubGraph.Builder()
                                .rootType(RootManyToMany.class)
                                .rootFieldName("roundLefts")
                                .rootCollType(List.class)
                                .currentType(RoundLeft.class)
                                .currentFieldName("roundRights")
                                .currentCollType(List.class)
                                .alias("ROUNDLEFTID", "id")
                                .alias("ROUNDLEFTTYPE", "type")
                                .build()
                        ).graphsManyToMany(new SubGraph.Builder()
                                .rootType(RootManyToMany.class)
                                .rootFieldName("roundLeft2s")
                                .rootCollType(List.class)
                                .currentType(RoundLeft2.class)
                                .currentFieldName("roundRights")
                                .currentCollType(Set.class)
                                .alias("ROUNDLEFT2ID", "id")
                                .alias("ROUNDLEFT2ISTYPE", "isType")
                                .build()
                        ).graphOneToEtc(new SubGraph.Builder()
                                .rootType(RootManyToMany.class)
                                .rootFieldName("round1Lvl1MTMS")
                                .rootCollType(List.class)
                                .currentType(Round1Lvl1MTM.class)
                                .alias("ROUND1LVL1MTMID", "id")
                                .alias("ROUND1LVL1MTMNAME", "name")
                                .alias("ROUND1LVL1MTMNUMBER", "number")
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

        entity_1_1.put("ROUNDLEFT2ID", 1L);
        entity_1_1.put("ROUNDLEFT2ISTYPE", true);

        entity_1_1.put("ROUND1LVL1MTMID", 1L);
        entity_1_1.put("ROUND1LVL1MTMNAME", "n-1");
        entity_1_1.put("ROUND1LVL1MTMNUMBER", 1);

        Map<String, Object> entity_1_2 = new HashMap<>();

        entity_1_2.put("ROOTMANYTOMANYID", 1L);
        entity_1_2.put("ROOTMANYTOMANYFIRSTNAME", "fn-1");
        entity_1_2.put("ROOTMANYTOMANYLASTNAME", "fn-1");

        entity_1_2.put("ROUNDLEFTID", 2L);
        entity_1_2.put("ROUNDLEFTTYPE", "t-2");

        entity_1_2.put("ROUNDLEFT2ID", 2L);
        entity_1_2.put("ROUNDLEFT2ISTYPE", false);

        entity_1_2.put("ROUND1LVL1MTMID", 2L);
        entity_1_2.put("ROUND1LVL1MTMNAME", "n-2");
        entity_1_2.put("ROUND1LVL1MTMNUMBER", 12);

        Map<String, Object> entity_1_3 = new HashMap<>();

        entity_1_3.put("ROOTMANYTOMANYID", 1L);
        entity_1_3.put("ROOTMANYTOMANYFIRSTNAME", "fn-1");
        entity_1_3.put("ROOTMANYTOMANYLASTNAME", "fn-1");

        entity_1_3.put("ROUNDLEFTID", 3L);
        entity_1_3.put("ROUNDLEFTTYPE", "t-3");

        entity_1_3.put("ROUNDLEFT2ID", 3L);
        entity_1_3.put("ROUNDLEFT2ISTYPE", true);

        entity_1_3.put("ROUND1LVL1MTMID", 3L);
        entity_1_3.put("ROUND1LVL1MTMNAME", "n-3");
        entity_1_3.put("ROUND1LVL1MTMNUMBER", 100);

        Map<String, Object> entity_1_4 = new HashMap<>();

        entity_1_4.put("ROOTMANYTOMANYID", 2L);
        entity_1_4.put("ROOTMANYTOMANYFIRSTNAME", "fn-2");
        entity_1_4.put("ROOTMANYTOMANYLASTNAME", "fn-2");

        entity_1_4.put("ROUNDLEFTID", 1L);
        entity_1_4.put("ROUNDLEFTTYPE", "t-1");

        entity_1_4.put("ROUNDLEFT2ID", 3L);
        entity_1_4.put("ROUNDLEFT2ISTYPE", true);

        entity_1_4.put("ROUND1LVL1MTMID", 4L);
        entity_1_4.put("ROUND1LVL1MTMNAME", "n-4");
        entity_1_4.put("ROUND1LVL1MTMNUMBER", 200);

        Map<String, Object> entity_1_5 = new HashMap<>();

        entity_1_5.put("ROOTMANYTOMANYID", 2L);
        entity_1_5.put("ROOTMANYTOMANYFIRSTNAME", "fn-2");
        entity_1_5.put("ROOTMANYTOMANYLASTNAME", "fn-2");

        entity_1_5.put("ROUNDLEFTID", 2L);
        entity_1_5.put("ROUNDLEFTTYPE", "t-2");

        entity_1_5.put("ROUNDLEFT2ID", 2L);
        entity_1_5.put("ROUNDLEFT2ISTYPE", false);

        entity_1_5.put("ROUND1LVL1MTMID", 5L);
        entity_1_5.put("ROUND1LVL1MTMNAME", "n-5");
        entity_1_5.put("ROUND1LVL1MTMNUMBER", 400);

        Map<String, Object> entity_1_6 = new HashMap<>();

        entity_1_6.put("ROOTMANYTOMANYID", 3L);
        entity_1_6.put("ROOTMANYTOMANYFIRSTNAME", "fn-3");
        entity_1_6.put("ROOTMANYTOMANYLASTNAME", "fn-3");

        entity_1_6.put("ROUNDLEFTID", 3L);
        entity_1_6.put("ROUNDLEFTTYPE", "t-3");

        entity_1_6.put("ROUNDLEFT2ID", 1L);
        entity_1_6.put("ROUNDLEFT2ISTYPE", true);

        entity_1_6.put("ROUND1LVL1MTMID", 6L);
        entity_1_6.put("ROUND1LVL1MTMNAME", "n-6");
        entity_1_6.put("ROUND1LVL1MTMNUMBER", 600);

        Map<String, Object> entity_1_7 = new HashMap<>();

        entity_1_7.put("ROOTMANYTOMANYID", 3L);
        entity_1_7.put("ROOTMANYTOMANYFIRSTNAME", "fn-3");
        entity_1_7.put("ROOTMANYTOMANYLASTNAME", "fn-3");

        entity_1_7.put("ROUNDLEFTID", 1L);
        entity_1_7.put("ROUNDLEFTTYPE", "t-1");

        entity_1_7.put("ROUNDLEFT2ID", 2L);
        entity_1_7.put("ROUNDLEFT2ISTYPE", false);

        entity_1_7.put("ROUND1LVL1MTMID", 7L);
        entity_1_7.put("ROUND1LVL1MTMNAME", "n-7");
        entity_1_7.put("ROUND1LVL1MTMNUMBER", 1200);

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
