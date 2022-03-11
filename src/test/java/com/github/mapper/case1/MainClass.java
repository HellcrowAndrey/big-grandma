package com.github.mapper.case1;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.SubGraph;

import java.util.*;

public class MainClass {

    public static void main(String[] args) {

        DependenciesGraph.Root root = new DependenciesGraph.Root(
                RootCase1.class,
                List.of(
                        // branch 0
                        new SubGraph.DefaultBuilder()
                                .rootType(RootCase1.class)
                                .currentType(SubGraphLvl2Case1.class)
                                .rootFieldName("subGraphLvl2Case1")
                                .currentFieldName("rootCase1")
                                .rootCollType(List.class)
                                .graphs(List.of(
                                        new SubGraph.DefaultBuilder()
                                                .rootType(SubGraphLvl2Case1.class)
                                                .currentType(SubGraphLvl3Case1.class)
                                                .rootFieldName("subGraphLvl3Case1s")
                                                .currentFieldName("subGraphLvl2Case1")
                                                .rootCollType(Set.class)
                                                .build()
                                )).build(),
                        // branch 1
                        new SubGraph.DefaultBuilder()
                                .rootType(RootCase1.class)
                                .currentType(Branch1Lvl1Case1.class)
                                .rootFieldName("branch1Lvl1Case1")
                                .build()

                )
        );

        DependenciesGraph dependencies = new DependenciesGraph(root);

        dependencies.many(data())
                .collectList()
                .subscribe(entity -> System.out.println(entity));

        dependencies.single(data())
                .subscribe(entity -> System.out.println(entity));

    }

    static List<Map<String, Object>> data() {

        //*********************************
        // ROUND 1
        //*********************************

        Map<String, Object> round_1_entity_1 = new HashMap<>();

        round_1_entity_1.put("ROOTCASE1ID", 1L);
        round_1_entity_1.put("ROOTCASE1LASTNAME", "last-name-1");
        round_1_entity_1.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_1_entity_1.put("SUBGRAPHLVL2CASE1ID", 1L);
        round_1_entity_1.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name1");
        round_1_entity_1.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one1");

        round_1_entity_1.put("SUBGRAPHLVL3CASE1ID", 16L);
        round_1_entity_1.put("SUBGRAPHLVL3CASE1AMOUNT", 12222);
        round_1_entity_1.put("SUBGRAPHLVL3CASE1isaccount", true);

        round_1_entity_1.put("BRANCH1LVL1CASE1ID", 1L);
        round_1_entity_1.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-1");
        round_1_entity_1.put("BRANCH1LVL1CASE1GRAPHLVLONE", 12324);

        Map<String, Object> round_1_entity_2 = new HashMap<>();

        round_1_entity_2.put("ROOTCASE1ID", 1L);
        round_1_entity_2.put("ROOTCASE1LASTNAME", "last-name-1");
        round_1_entity_2.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_1_entity_2.put("SUBGRAPHLVL2CASE1ID", 1L);
        round_1_entity_2.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name1");
        round_1_entity_2.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one1");

        round_1_entity_2.put("SUBGRAPHLVL3CASE1ID", 1666666L);
        round_1_entity_2.put("SUBGRAPHLVL3CASE1AMOUNT", 7876688);
        round_1_entity_2.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_1_entity_2.put("BRANCH1LVL1CASE1ID", 1L);
        round_1_entity_2.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-1");
        round_1_entity_2.put("BRANCH1LVL1CASE1GRAPHLVLONE", 12324);

        Map<String, Object> round_1_entity_3 = new HashMap<>();

        round_1_entity_3.put("ROOTCASE1ID", 1L);
        round_1_entity_3.put("ROOTCASE1LASTNAME", "last-name-1");
        round_1_entity_3.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_1_entity_3.put("SUBGRAPHLVL2CASE1ID", 2L);
        round_1_entity_3.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name2");
        round_1_entity_3.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one2");

        round_1_entity_3.put("SUBGRAPHLVL3CASE1ID", 33L);
        round_1_entity_3.put("SUBGRAPHLVL3CASE1AMOUNT", 12222);
        round_1_entity_3.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_1_entity_3.put("BRANCH1LVL1CASE1ID", 1L);
        round_1_entity_3.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-1");
        round_1_entity_3.put("BRANCH1LVL1CASE1GRAPHLVLONE", 12324);

        Map<String, Object> round_1_entity_4 = new HashMap<>();

        round_1_entity_4.put("ROOTCASE1ID", 1L);
        round_1_entity_4.put("ROOTCASE1LASTNAME", "last-name-1");
        round_1_entity_4.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_1_entity_4.put("SUBGRAPHLVL2CASE1ID", 3L);
        round_1_entity_4.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name3");
        round_1_entity_4.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one3");

        round_1_entity_4.put("SUBGRAPHLVL3CASE1ID", 444L);
        round_1_entity_4.put("SUBGRAPHLVL3CASE1AMOUNT", 2256);
        round_1_entity_4.put("SUBGRAPHLVL3CASE1isaccount", true);

        round_1_entity_4.put("BRANCH1LVL1CASE1ID", 1L);
        round_1_entity_4.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-1");
        round_1_entity_4.put("BRANCH1LVL1CASE1GRAPHLVLONE", 12324);

        Map<String, Object> round_1_entity_5 = new HashMap<>();

        round_1_entity_5.put("ROOTCASE1ID", 1L);
        round_1_entity_5.put("ROOTCASE1LASTNAME", "last-name-1");
        round_1_entity_5.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_1_entity_5.put("SUBGRAPHLVL2CASE1ID", 4L);
        round_1_entity_5.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name4");
        round_1_entity_5.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one4");

        round_1_entity_5.put("SUBGRAPHLVL3CASE1ID", 145L);
        round_1_entity_5.put("SUBGRAPHLVL3CASE1AMOUNT", 654);
        round_1_entity_5.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_1_entity_5.put("BRANCH1LVL1CASE1ID", 1L);
        round_1_entity_5.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-1");
        round_1_entity_5.put("BRANCH1LVL1CASE1GRAPHLVLONE", 12324);

        Map<String, Object> round_1_entity_6 = new HashMap<>();

        round_1_entity_6.put("ROOTCASE1ID", 1L);
        round_1_entity_6.put("ROOTCASE1LASTNAME", "last-name-1");
        round_1_entity_6.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_1_entity_6.put("SUBGRAPHLVL2CASE1ID", 4L);
        round_1_entity_6.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name4");
        round_1_entity_6.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one4");

        round_1_entity_6.put("SUBGRAPHLVL3CASE1ID", 144444333L);
        round_1_entity_6.put("SUBGRAPHLVL3CASE1AMOUNT", 565478456);
        round_1_entity_6.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_1_entity_6.put("BRANCH1LVL1CASE1ID", 1L);
        round_1_entity_6.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-1");
        round_1_entity_6.put("BRANCH1LVL1CASE1GRAPHLVLONE", 12324);

        //*********************************
        // ROUND 2
        //*********************************

        Map<String, Object> round_2_entity_1 = new HashMap<>();

        round_2_entity_1.put("ROOTCASE1ID", 2L);
        round_2_entity_1.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_1.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_1.put("SUBGRAPHLVL2CASE1ID", 1L);
        round_2_entity_1.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name1");
        round_2_entity_1.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one1");

        round_2_entity_1.put("SUBGRAPHLVL3CASE1ID", 16L);
        round_2_entity_1.put("SUBGRAPHLVL3CASE1AMOUNT", 12222);
        round_2_entity_1.put("SUBGRAPHLVL3CASE1isaccount", true);

        round_2_entity_1.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_1.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_1.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        Map<String, Object> round_2_entity_2 = new HashMap<>();

        round_2_entity_2.put("ROOTCASE1ID", 2L);
        round_2_entity_2.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_2.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_2.put("SUBGRAPHLVL2CASE1ID", 1L);
        round_2_entity_2.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name1");
        round_2_entity_2.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one1");

        round_2_entity_2.put("SUBGRAPHLVL3CASE1ID", 16L);
        round_2_entity_2.put("SUBGRAPHLVL3CASE1AMOUNT", 12222);
        round_2_entity_2.put("SUBGRAPHLVL3CASE1ISACCOUNT", true);

        round_2_entity_2.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_2.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_2.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        Map<String, Object> round_2_entity_3 = new HashMap<>();

        round_2_entity_3.put("ROOTCASE1ID", 2L);
        round_2_entity_3.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_3.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_3.put("SUBGRAPHLVL2CASE1ID", 1L);
        round_2_entity_3.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name1");
        round_2_entity_3.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one1");

        round_2_entity_3.put("SUBGRAPHLVL3CASE1ID", 1666666L);
        round_2_entity_3.put("SUBGRAPHLVL3CASE1AMOUNT", 7876688);
        round_2_entity_3.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_2_entity_3.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_3.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_3.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        Map<String, Object> round_2_entity_4 = new HashMap<>();

        round_2_entity_4.put("ROOTCASE1ID", 2L);
        round_2_entity_4.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_4.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_4.put("SUBGRAPHLVL2CASE1ID", 444L);
        round_2_entity_4.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name444");
        round_2_entity_4.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one444");

        round_2_entity_4.put("SUBGRAPHLVL3CASE1ID", 335L);
        round_2_entity_4.put("SUBGRAPHLVL3CASE1AMOUNT", 123214);
        round_2_entity_4.put("SUBGRAPHLVL3CASE1isaccount", true);

        round_2_entity_4.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_4.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_4.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        Map<String, Object> round_2_entity_5 = new HashMap<>();

        round_2_entity_5.put("ROOTCASE1ID", 2L);
        round_2_entity_5.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_5.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_5.put("SUBGRAPHLVL2CASE1ID", 36L);
        round_2_entity_5.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name36");
        round_2_entity_5.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one36");

        round_2_entity_5.put("SUBGRAPHLVL3CASE1ID", 41233L);
        round_2_entity_5.put("SUBGRAPHLVL3CASE1AMOUNT", 22765);
        round_2_entity_5.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_2_entity_5.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_5.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_5.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        Map<String, Object> round_2_entity_6 = new HashMap<>();

        round_2_entity_6.put("ROOTCASE1ID", 2L);
        round_2_entity_6.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_6.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_6.put("SUBGRAPHLVL2CASE1ID", 4543L);
        round_2_entity_6.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name4543");
        round_2_entity_6.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one4543");

        round_2_entity_6.put("SUBGRAPHLVL3CASE1ID", 145L);
        round_2_entity_6.put("SUBGRAPHLVL3CASE1AMOUNT", 654);
        round_2_entity_6.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_2_entity_6.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_6.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_6.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        Map<String, Object> round_2_entity_7 = new HashMap<>();

        round_2_entity_7.put("ROOTCASE1ID", 2L);
        round_2_entity_7.put("ROOTCASE1LASTNAME", "last-name-1");
        round_2_entity_7.put("ROOTCASE1FIRSTNAME", "first-name-1");

        round_2_entity_7.put("SUBGRAPHLVL2CASE1ID", 4543L);
        round_2_entity_7.put("SUBGRAPHLVL2CASE1NAME", "sub-graph-case1-name4543");
        round_2_entity_7.put("SUBGRAPHLVL2CASE1PLUSONE", "sub-graph-case1-plus-one4543");

        round_2_entity_7.put("SUBGRAPHLVL3CASE1ID", 144333L);
        round_2_entity_7.put("SUBGRAPHLVL3CASE1AMOUNT", 2132432);
        round_2_entity_7.put("SUBGRAPHLVL3CASE1isaccount", false);

        round_2_entity_7.put("BRANCH1LVL1CASE1ID", 2L);
        round_2_entity_7.put("BRANCH1LVL1CASE1BRANCHNAME", "branch-name-2");
        round_2_entity_7.put("BRANCH1LVL1CASE1GRAPHLVLONE", 15);

        return List.of(

                //*********************************
                // ROUND 1
                //*********************************

                round_1_entity_1, round_1_entity_2,
                round_1_entity_3, round_1_entity_4,
                round_1_entity_5, round_1_entity_6,

                //*********************************
                // ROUND 2
                //*********************************

                round_2_entity_1, round_2_entity_2,
                round_2_entity_3, round_2_entity_4,
                round_2_entity_5, round_2_entity_6,
                round_2_entity_7

        );
    }


}
