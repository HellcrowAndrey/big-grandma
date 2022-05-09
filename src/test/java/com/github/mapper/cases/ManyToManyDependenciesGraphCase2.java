package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.many.to.many.case2.RootMTMCase2;
import com.github.mapper.models.many.to.many.case2.Round1Lvl1;
import com.github.mapper.models.many.to.many.case2.Round2Lvl1;

import java.util.*;

public class ManyToManyDependenciesGraphCase2 {

    public static DependenciesGraph graph() {
        Root root =
                new Root.Builder()
                        .rootType(RootMTMCase2.class)
                        .graphOneToEtc(
                                new SubGraph.Builder()
                                        .rootType(RootMTMCase2.class)
                                        .rootFieldName("round1Lvl1s")
                                        .rootCollType(List.class)
                                        .currentType(Round1Lvl1.class)
                                        .currentFieldName("rootMTMCase2")
                                        .currentCollType(List.class)
                                        .graphManyToMany(
                                                new SubGraph.Builder()
                                                        .rootType(Round1Lvl1.class)
                                                        .rootFieldName("round2Lvl1s")
                                                        .rootCollType(List.class)
                                                        .currentType(Round2Lvl1.class)
                                                        .currentFieldName("round1Lvl1s")
                                                        .currentCollType(List.class)
                                                        .build()
                                        ).build()
                        ).build();
        return new DependenciesGraph(root);
    }

    public static List<Map<String, Object>> tuples() {
        Map<String, Object> entity_1_1 = new HashMap<>();

        entity_1_1.put("ROOTMTMCASE2ID", 1L);
        entity_1_1.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_1.put("ROUND1LVL1ID", 1L);
        entity_1_1.put("ROUND1LVL1TYPE", "T-1");

        entity_1_1.put("ROUND2LVL1ID", 1L);
        entity_1_1.put("ROUND2LVL1CATEGORY", "C-1");

        Map<String, Object> entity_1_2 = new HashMap<>();

        entity_1_2.put("ROOTMTMCASE2ID", 1L);
        entity_1_2.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_2.put("ROUND1LVL1ID", 1L);
        entity_1_2.put("ROUND1LVL1TYPE", "T-1");

        entity_1_2.put("ROUND2LVL1ID", 2L);
        entity_1_2.put("ROUND2LVL1CATEGORY", "C-2");

        Map<String, Object> entity_1_3 = new HashMap<>();

        entity_1_3.put("ROOTMTMCASE2ID", 1L);
        entity_1_3.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_3.put("ROUND1LVL1ID", 2L);
        entity_1_3.put("ROUND1LVL1TYPE", "T-2");

        entity_1_3.put("ROUND2LVL1ID", 1L);
        entity_1_3.put("ROUND2LVL1CATEGORY", "C-1");

        Map<String, Object> entity_1_4 = new HashMap<>();

        entity_1_4.put("ROOTMTMCASE2ID", 1L);
        entity_1_4.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_4.put("ROUND1LVL1ID", 2L);
        entity_1_4.put("ROUND1LVL1TYPE", "T-2");

        entity_1_4.put("ROUND2LVL1ID", 2L);
        entity_1_4.put("ROUND2LVL1CATEGORY", "C-2");

        Map<String, Object> entity_1_5 = new HashMap<>();

        entity_1_5.put("ROOTMTMCASE2ID", 1L);
        entity_1_5.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_5.put("ROUND1LVL1ID", 2L);
        entity_1_5.put("ROUND1LVL1TYPE", "T-2");

        entity_1_5.put("ROUND2LVL1ID", 3L);
        entity_1_5.put("ROUND2LVL1CATEGORY", "C-3");

        Map<String, Object> entity_1_6 = new HashMap<>();

        entity_1_6.put("ROOTMTMCASE2ID", 1L);
        entity_1_6.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_6.put("ROUND1LVL1ID", 3L);
        entity_1_6.put("ROUND1LVL1TYPE", "T-3");

        entity_1_6.put("ROUND2LVL1ID", 2L);
        entity_1_6.put("ROUND2LVL1CATEGORY", "C-2");

        Map<String, Object> entity_1_7 = new HashMap<>();

        entity_1_7.put("ROOTMTMCASE2ID", 1L);
        entity_1_7.put("ROOTMTMCASE2NAME", "R-1");

        entity_1_7.put("ROUND1LVL1ID", 3L);
        entity_1_7.put("ROUND1LVL1TYPE", "T-3");

        entity_1_7.put("ROUND2LVL1ID", 3L);
        entity_1_7.put("ROUND2LVL1CATEGORY", "C-3");

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

    public static List<RootMTMCase2> expect_many() {
        return new ArrayList<>();
    }

}
