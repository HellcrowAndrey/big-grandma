package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.RootLvl;
import com.github.mapper.models.Round1Lvl1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependenciesGraphCase2 {

    public static List<Map<String, Object>> tuples() {

        Map<String, Object> entity_1 = new HashMap<>();

        entity_1.put("ROOTLVLID", 1L);
        entity_1.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1.put("ROOTLVLNAME", "test-name-1");
        entity_1.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1.put("ROUND1LVL1ID", 1L);

        Map<String, Object> entity_2 = new HashMap<>();

        entity_2.put("ROOTLVLID", 2L);
        entity_2.put("ROOTLVLVINCODE", "5436475-1231234-321324");
        entity_2.put("ROOTLVLNAME", "test-name-2");
        entity_2.put("ROOTLVLFIRSTNAME", "first-test-name-2");
        entity_2.put("ROOTLVLLASTNAME", "last-test-name-2");

        entity_2.put("ROUND1LVL1ID", 2L);

        Map<String, Object> entity_3 = new HashMap<>();

        entity_3.put("ROOTLVLID", 3L);
        entity_3.put("ROOTLVLVINCODE", "432556456-1234324-488796");
        entity_3.put("ROOTLVLNAME", "test-name-3");
        entity_3.put("ROOTLVLFIRSTNAME", "first-test-name-3");
        entity_3.put("ROOTLVLLASTNAME", "last-test-name-3");

        entity_3.put("ROUND1LVL1ID", 3L);

        Map<String, Object> entity_4 = new HashMap<>();

        entity_4.put("ROOTLVLID", 4L);
        entity_4.put("ROOTLVLVINCODE", "858888-4777666-2355588");
        entity_4.put("ROOTLVLNAME", "test-name-4");
        entity_4.put("ROOTLVLFIRSTNAME", "first-test-name-4");
        entity_4.put("ROOTLVLLASTNAME", "last-test-name-4");

        entity_4.put("ROUND1LVL1ID", 4L);

        Map<String, Object> entity_5 = new HashMap<>();

        entity_5.put("ROOTLVLID", 5L);
        entity_5.put("ROOTLVLVINCODE", "54566654-1112333-0099977");
        entity_5.put("ROOTLVLNAME", "test-name-5");
        entity_5.put("ROOTLVLFIRSTNAME", "first-test-name-5");
        entity_5.put("ROOTLVLLASTNAME", "last-test-name-5");

        entity_5.put("ROUND1LVL1ID", 5L);

        return List.of(
                entity_1,
                entity_2,
                entity_3,
                entity_4,
                entity_5
        );
    }

    public static List<Map<String, Object>> tuple() {
        Map<String, Object> entity_1 = new HashMap<>();

        entity_1.put("ROOTLVLID", 1L);
        entity_1.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1.put("ROOTLVLNAME", "test-name-1");
        entity_1.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1.put("ROUND1LVL1ID", 1L);

        return List.of(
                entity_1
        );
    }

    public static DependenciesGraph graph() {
        Root root = new Root.Builder()
                .rootType(RootLvl.class)
                .graphsOneToEtc(List.of(
                        new SubGraph.Builder()
                                .rootType(RootLvl.class)
                                .currentType(Round1Lvl1.class)
                                .rootFieldName("round1Lvl1")
                                .alias("ROUND1LVL1ID", "id")
                                .build()
                ))
                .alias("ROOTLVLID", "id")
                .alias("ROOTLVLVINCODE", "vinCode")
                .alias("ROOTLVLNAME", "name")
                .alias("ROOTLVLFIRSTNAME", "firstName")
                .alias("ROOTLVLLASTNAME", "lastName")
                .build();
        return new DependenciesGraph(root);
    }

    public static List<RootLvl> expect_many() {
        return List.of(
                new RootLvl(
                        1L,
                        "4324235-5435345-7657567",
                        "test-name-1",
                        "first-test-name-1",
                        "last-test-name-1",
                        new Round1Lvl1(1L)
                ),
                new RootLvl(
                        2L,
                        "5436475-1231234-321324",
                        "test-name-2",
                        "first-test-name-2",
                        "last-test-name-2",
                        new Round1Lvl1(2L)
                ),
                new RootLvl(
                        3L,
                        "432556456-1234324-488796",
                        "test-name-3",
                        "first-test-name-3",
                        "last-test-name-3",
                        new Round1Lvl1(3L)
                ),
                new RootLvl(
                        4L,
                        "858888-4777666-2355588",
                        "test-name-4",
                        "first-test-name-4",
                        "last-test-name-4",
                        new Round1Lvl1(4L)
                ),
                new RootLvl(
                        5L,
                        "54566654-1112333-0099977",
                        "test-name-5",
                        "first-test-name-5",
                        "last-test-name-5",
                        new Round1Lvl1(5L)
                )
        );
    }

    public static RootLvl expect() {
        return new RootLvl(
                1L,
                "4324235-5435345-7657567",
                "test-name-1",
                "first-test-name-1",
                "last-test-name-1",
                new Round1Lvl1(1L)
        );
    }

}
