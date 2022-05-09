package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.RootLvl;
import com.github.mapper.models.Round1Lvl1;
import com.github.mapper.models.Round2Lvl1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependenciesGraphCase5 {

    public static List<Map<String, Object>> tuples() {

        Map<String, Object> entity_1_1 = new HashMap<>();

        entity_1_1.put("ROOTLVLID", 1L);
        entity_1_1.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_1.put("ROOTLVLNAME", "test-name-1");
        entity_1_1.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_1.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_1.put("ROUND1LVL1ID", 1L);
        entity_1_1.put("ROUND1LVL1TITLE", "title-1");
        entity_1_1.put("ROUND1LVL1AMOUNT", 111);

        entity_1_1.put("ROUND2LVL1ID", 1L);

        Map<String, Object> entity_1_2 = new HashMap<>();

        entity_1_2.put("ROOTLVLID", 1L);
        entity_1_2.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_2.put("ROOTLVLNAME", "test-name-1");
        entity_1_2.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_2.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_2.put("ROUND1LVL1ID", 1L);
        entity_1_2.put("ROUND1LVL1TITLE", "title-1");
        entity_1_2.put("ROUND1LVL1AMOUNT", 111);

        entity_1_2.put("ROUND2LVL1ID", 2L);

        Map<String, Object> entity_1_3 = new HashMap<>();

        entity_1_3.put("ROOTLVLID", 1L);
        entity_1_3.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_3.put("ROOTLVLNAME", "test-name-1");
        entity_1_3.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_3.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_3.put("ROUND1LVL1ID", 1L);
        entity_1_3.put("ROUND1LVL1TITLE", "title-1");
        entity_1_3.put("ROUND1LVL1AMOUNT", 111);

        entity_1_3.put("ROUND2LVL1ID", 3L);

        Map<String, Object> entity_1_4 = new HashMap<>();

        entity_1_4.put("ROOTLVLID", 1L);
        entity_1_4.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_4.put("ROOTLVLNAME", "test-name-1");
        entity_1_4.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_4.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_4.put("ROUND1LVL1ID", 1L);
        entity_1_4.put("ROUND1LVL1TITLE", "title-1");
        entity_1_4.put("ROUND1LVL1AMOUNT", 111);

        entity_1_4.put("ROUND2LVL1ID", 4L);

        Map<String, Object> entity_1_5 = new HashMap<>();

        entity_1_5.put("ROOTLVLID", 1L);
        entity_1_5.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_5.put("ROOTLVLNAME", "test-name-1");
        entity_1_5.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_5.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_5.put("ROUND1LVL1ID", 1L);
        entity_1_5.put("ROUND1LVL1TITLE", "title-1");
        entity_1_5.put("ROUND1LVL1AMOUNT", 111);

        entity_1_5.put("ROUND2LVL1ID", 5L);

        Map<String, Object> entity_2_1 = new HashMap<>();

        entity_2_1.put("ROOTLVLID", 2L);
        entity_2_1.put("ROOTLVLVINCODE", "5436475-1231234-321324");
        entity_2_1.put("ROOTLVLNAME", "test-name-2");
        entity_2_1.put("ROOTLVLFIRSTNAME", "first-test-name-2");
        entity_2_1.put("ROOTLVLLASTNAME", "last-test-name-2");

        entity_2_1.put("ROUND1LVL1ID", 2L);
        entity_2_1.put("ROUND1LVL1TITLE", "title-2");
        entity_2_1.put("ROUND1LVL1AMOUNT", 222);

        entity_2_1.put("ROUND2LVL1ID", 6L);

        Map<String, Object> entity_2_2 = new HashMap<>();

        entity_2_2.put("ROOTLVLID", 2L);
        entity_2_2.put("ROOTLVLVINCODE", "5436475-1231234-321324");
        entity_2_2.put("ROOTLVLNAME", "test-name-2");
        entity_2_2.put("ROOTLVLFIRSTNAME", "first-test-name-2");
        entity_2_2.put("ROOTLVLLASTNAME", "last-test-name-2");

        entity_2_2.put("ROUND1LVL1ID", 2L);
        entity_2_2.put("ROUND1LVL1TITLE", "title-2");
        entity_2_2.put("ROUND1LVL1AMOUNT", 222);

        entity_2_2.put("ROUND2LVL1ID", 7L);

        Map<String, Object> entity_2_3 = new HashMap<>();

        entity_2_3.put("ROOTLVLID", 2L);
        entity_2_3.put("ROOTLVLVINCODE", "5436475-1231234-321324");
        entity_2_3.put("ROOTLVLNAME", "test-name-2");
        entity_2_3.put("ROOTLVLFIRSTNAME", "first-test-name-2");
        entity_2_3.put("ROOTLVLLASTNAME", "last-test-name-2");

        entity_2_3.put("ROUND1LVL1ID", 2L);
        entity_2_3.put("ROUND1LVL1TITLE", "title-2");
        entity_2_3.put("ROUND1LVL1AMOUNT", 222);

        entity_2_3.put("ROUND2LVL1ID", 8L);

        Map<String, Object> entity_2_4 = new HashMap<>();

        entity_2_4.put("ROOTLVLID", 2L);
        entity_2_4.put("ROOTLVLVINCODE", "5436475-1231234-321324");
        entity_2_4.put("ROOTLVLNAME", "test-name-2");
        entity_2_4.put("ROOTLVLFIRSTNAME", "first-test-name-2");
        entity_2_4.put("ROOTLVLLASTNAME", "last-test-name-2");

        entity_2_4.put("ROUND1LVL1ID", 2L);
        entity_2_4.put("ROUND1LVL1TITLE", "title-2");
        entity_2_4.put("ROUND1LVL1AMOUNT", 222);

        entity_2_4.put("ROUND2LVL1ID", 9L);

        Map<String, Object> entity_2_5 = new HashMap<>();

        entity_2_5.put("ROOTLVLID", 2L);
        entity_2_5.put("ROOTLVLVINCODE", "5436475-1231234-321324");
        entity_2_5.put("ROOTLVLNAME", "test-name-2");
        entity_2_5.put("ROOTLVLFIRSTNAME", "first-test-name-2");
        entity_2_5.put("ROOTLVLLASTNAME", "last-test-name-2");

        entity_2_5.put("ROUND1LVL1ID", 2L);
        entity_2_5.put("ROUND1LVL1TITLE", "title-2");
        entity_2_5.put("ROUND1LVL1AMOUNT", 222);

        entity_2_5.put("ROUND2LVL1ID", 10L);

        Map<String, Object> entity_3_1 = new HashMap<>();

        entity_3_1.put("ROOTLVLID", 3L);
        entity_3_1.put("ROOTLVLVINCODE", "432556456-1234324-488796");
        entity_3_1.put("ROOTLVLNAME", "test-name-3");
        entity_3_1.put("ROOTLVLFIRSTNAME", "first-test-name-3");
        entity_3_1.put("ROOTLVLLASTNAME", "last-test-name-3");

        entity_3_1.put("ROUND1LVL1ID", 3L);
        entity_3_1.put("ROUND1LVL1TITLE", "title-3");
        entity_3_1.put("ROUND1LVL1AMOUNT", 333);

        entity_3_1.put("ROUND2LVL1ID", 11L);

        Map<String, Object> entity_3_2 = new HashMap<>();

        entity_3_2.put("ROOTLVLID", 3L);
        entity_3_2.put("ROOTLVLVINCODE", "432556456-1234324-488796");
        entity_3_2.put("ROOTLVLNAME", "test-name-3");
        entity_3_2.put("ROOTLVLFIRSTNAME", "first-test-name-3");
        entity_3_2.put("ROOTLVLLASTNAME", "last-test-name-3");

        entity_3_2.put("ROUND1LVL1ID", 3L);
        entity_3_2.put("ROUND1LVL1TITLE", "title-3");
        entity_3_2.put("ROUND1LVL1AMOUNT", 333);

        entity_3_2.put("ROUND2LVL1ID", 12L);

        Map<String, Object> entity_3_3 = new HashMap<>();

        entity_3_3.put("ROOTLVLID", 3L);
        entity_3_3.put("ROOTLVLVINCODE", "432556456-1234324-488796");
        entity_3_3.put("ROOTLVLNAME", "test-name-3");
        entity_3_3.put("ROOTLVLFIRSTNAME", "first-test-name-3");
        entity_3_3.put("ROOTLVLLASTNAME", "last-test-name-3");

        entity_3_3.put("ROUND1LVL1ID", 3L);
        entity_3_3.put("ROUND1LVL1TITLE", "title-3");
        entity_3_3.put("ROUND1LVL1AMOUNT", 333);

        entity_3_3.put("ROUND2LVL1ID", 13L);

        Map<String, Object> entity_3_4 = new HashMap<>();

        entity_3_4.put("ROOTLVLID", 3L);
        entity_3_4.put("ROOTLVLVINCODE", "432556456-1234324-488796");
        entity_3_4.put("ROOTLVLNAME", "test-name-3");
        entity_3_4.put("ROOTLVLFIRSTNAME", "first-test-name-3");
        entity_3_4.put("ROOTLVLLASTNAME", "last-test-name-3");

        entity_3_4.put("ROUND1LVL1ID", 3L);
        entity_3_4.put("ROUND1LVL1TITLE", "title-3");
        entity_3_4.put("ROUND1LVL1AMOUNT", 333);

        entity_3_4.put("ROUND2LVL1ID", 14L);

        Map<String, Object> entity_3_5 = new HashMap<>();

        entity_3_5.put("ROOTLVLID", 3L);
        entity_3_5.put("ROOTLVLVINCODE", "432556456-1234324-488796");
        entity_3_5.put("ROOTLVLNAME", "test-name-3");
        entity_3_5.put("ROOTLVLFIRSTNAME", "first-test-name-3");
        entity_3_5.put("ROOTLVLLASTNAME", "last-test-name-3");

        entity_3_5.put("ROUND1LVL1ID", 3L);
        entity_3_5.put("ROUND1LVL1TITLE", "title-3");
        entity_3_5.put("ROUND1LVL1AMOUNT", 333);

        entity_3_5.put("ROUND2LVL1ID", 15L);

        Map<String, Object> entity_4_1 = new HashMap<>();

        entity_4_1.put("ROOTLVLID", 4L);
        entity_4_1.put("ROOTLVLVINCODE", "858888-4777666-2355588");
        entity_4_1.put("ROOTLVLNAME", "test-name-4");
        entity_4_1.put("ROOTLVLFIRSTNAME", "first-test-name-4");
        entity_4_1.put("ROOTLVLLASTNAME", "last-test-name-4");

        entity_4_1.put("ROUND1LVL1ID", 4L);
        entity_4_1.put("ROUND1LVL1TITLE", "title-4");
        entity_4_1.put("ROUND1LVL1AMOUNT", 444);

        entity_4_1.put("ROUND2LVL1ID", 16L);

        Map<String, Object> entity_4_2 = new HashMap<>();

        entity_4_2.put("ROOTLVLID", 4L);
        entity_4_2.put("ROOTLVLVINCODE", "858888-4777666-2355588");
        entity_4_2.put("ROOTLVLNAME", "test-name-4");
        entity_4_2.put("ROOTLVLFIRSTNAME", "first-test-name-4");
        entity_4_2.put("ROOTLVLLASTNAME", "last-test-name-4");

        entity_4_2.put("ROUND1LVL1ID", 4L);
        entity_4_2.put("ROUND1LVL1TITLE", "title-4");
        entity_4_2.put("ROUND1LVL1AMOUNT", 444);

        entity_4_2.put("ROUND2LVL1ID", 17L);

        Map<String, Object> entity_4_3 = new HashMap<>();

        entity_4_3.put("ROOTLVLID", 4L);
        entity_4_3.put("ROOTLVLVINCODE", "858888-4777666-2355588");
        entity_4_3.put("ROOTLVLNAME", "test-name-4");
        entity_4_3.put("ROOTLVLFIRSTNAME", "first-test-name-4");
        entity_4_3.put("ROOTLVLLASTNAME", "last-test-name-4");

        entity_4_3.put("ROUND1LVL1ID", 4L);
        entity_4_3.put("ROUND1LVL1TITLE", "title-4");
        entity_4_3.put("ROUND1LVL1AMOUNT", 444);

        entity_4_3.put("ROUND2LVL1ID", 18L);

        Map<String, Object> entity_4_4 = new HashMap<>();

        entity_4_4.put("ROOTLVLID", 4L);
        entity_4_4.put("ROOTLVLVINCODE", "858888-4777666-2355588");
        entity_4_4.put("ROOTLVLNAME", "test-name-4");
        entity_4_4.put("ROOTLVLFIRSTNAME", "first-test-name-4");
        entity_4_4.put("ROOTLVLLASTNAME", "last-test-name-4");

        entity_4_4.put("ROUND1LVL1ID", 4L);
        entity_4_4.put("ROUND1LVL1TITLE", "title-4");
        entity_4_4.put("ROUND1LVL1AMOUNT", 444);

        entity_4_4.put("ROUND2LVL1ID", 19L);

        Map<String, Object> entity_4_5 = new HashMap<>();

        entity_4_5.put("ROOTLVLID", 4L);
        entity_4_5.put("ROOTLVLVINCODE", "858888-4777666-2355588");
        entity_4_5.put("ROOTLVLNAME", "test-name-4");
        entity_4_5.put("ROOTLVLFIRSTNAME", "first-test-name-4");
        entity_4_5.put("ROOTLVLLASTNAME", "last-test-name-4");

        entity_4_5.put("ROUND1LVL1ID", 4L);
        entity_4_5.put("ROUND1LVL1TITLE", "title-4");
        entity_4_5.put("ROUND1LVL1AMOUNT", 444);

        entity_4_5.put("ROUND2LVL1ID", 20L);

        Map<String, Object> entity_5_1 = new HashMap<>();

        entity_5_1.put("ROOTLVLID", 5L);
        entity_5_1.put("ROOTLVLVINCODE", "54566654-1112333-0099977");
        entity_5_1.put("ROOTLVLNAME", "test-name-5");
        entity_5_1.put("ROOTLVLFIRSTNAME", "first-test-name-5");
        entity_5_1.put("ROOTLVLLASTNAME", "last-test-name-5");

        entity_5_1.put("ROUND1LVL1ID", 5L);
        entity_5_1.put("ROUND1LVL1TITLE", "title-5");
        entity_5_1.put("ROUND1LVL1AMOUNT", 555);

        entity_5_1.put("ROUND2LVL1ID", 21L);

        Map<String, Object> entity_5_2 = new HashMap<>();

        entity_5_2.put("ROOTLVLID", 5L);
        entity_5_2.put("ROOTLVLVINCODE", "54566654-1112333-0099977");
        entity_5_2.put("ROOTLVLNAME", "test-name-5");
        entity_5_2.put("ROOTLVLFIRSTNAME", "first-test-name-5");
        entity_5_2.put("ROOTLVLLASTNAME", "last-test-name-5");

        entity_5_2.put("ROUND1LVL1ID", 5L);
        entity_5_2.put("ROUND1LVL1TITLE", "title-5");
        entity_5_2.put("ROUND1LVL1AMOUNT", 555);

        entity_5_2.put("ROUND2LVL1ID", 22L);

        Map<String, Object> entity_5_3 = new HashMap<>();

        entity_5_3.put("ROOTLVLID", 5L);
        entity_5_3.put("ROOTLVLVINCODE", "54566654-1112333-0099977");
        entity_5_3.put("ROOTLVLNAME", "test-name-5");
        entity_5_3.put("ROOTLVLFIRSTNAME", "first-test-name-5");
        entity_5_3.put("ROOTLVLLASTNAME", "last-test-name-5");

        entity_5_3.put("ROUND1LVL1ID", 5L);
        entity_5_3.put("ROUND1LVL1TITLE", "title-5");
        entity_5_3.put("ROUND1LVL1AMOUNT", 555);

        entity_5_3.put("ROUND2LVL1ID", 23L);

        Map<String, Object> entity_5_4 = new HashMap<>();

        entity_5_4.put("ROOTLVLID", 5L);
        entity_5_4.put("ROOTLVLVINCODE", "54566654-1112333-0099977");
        entity_5_4.put("ROOTLVLNAME", "test-name-5");
        entity_5_4.put("ROOTLVLFIRSTNAME", "first-test-name-5");
        entity_5_4.put("ROOTLVLLASTNAME", "last-test-name-5");

        entity_5_4.put("ROUND1LVL1ID", 5L);
        entity_5_4.put("ROUND1LVL1TITLE", "title-5");
        entity_5_4.put("ROUND1LVL1AMOUNT", 555);

        entity_5_4.put("ROUND2LVL1ID", 24L);

        Map<String, Object> entity_5_5 = new HashMap<>();

        entity_5_5.put("ROOTLVLID", 5L);
        entity_5_5.put("ROOTLVLVINCODE", "54566654-1112333-0099977");
        entity_5_5.put("ROOTLVLNAME", "test-name-5");
        entity_5_5.put("ROOTLVLFIRSTNAME", "first-test-name-5");
        entity_5_5.put("ROOTLVLLASTNAME", "last-test-name-5");

        entity_5_5.put("ROUND1LVL1ID", 5L);
        entity_5_5.put("ROUND1LVL1TITLE", "title-5");
        entity_5_5.put("ROUND1LVL1AMOUNT", 555);

        entity_5_5.put("ROUND2LVL1ID", 25L);

        return List.of(
                entity_1_1,
                entity_1_2,
                entity_1_3,
                entity_1_4,
                entity_1_5,

                entity_2_1,
                entity_2_2,
                entity_2_3,
                entity_2_4,
                entity_2_5,

                entity_3_1,
                entity_3_2,
                entity_3_3,
                entity_3_4,
                entity_3_5,

                entity_4_1,
                entity_4_2,
                entity_4_3,
                entity_4_4,
                entity_4_5,

                entity_5_1,
                entity_5_2,
                entity_5_3,
                entity_5_4,
                entity_5_5
        );
    }

    public static List<Map<String, Object>> tuple() {

        Map<String, Object> entity_1_1 = new HashMap<>();

        entity_1_1.put("ROOTLVLID", 1L);
        entity_1_1.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_1.put("ROOTLVLNAME", "test-name-1");
        entity_1_1.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_1.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_1.put("ROUND1LVL1ID", 1L);
        entity_1_1.put("ROUND1LVL1TITLE", "title-1");
        entity_1_1.put("ROUND1LVL1AMOUNT", 111);

        entity_1_1.put("ROUND2LVL1ID", 1L);

        Map<String, Object> entity_1_2 = new HashMap<>();

        entity_1_2.put("ROOTLVLID", 1L);
        entity_1_2.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_2.put("ROOTLVLNAME", "test-name-1");
        entity_1_2.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_2.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_2.put("ROUND1LVL1ID", 1L);
        entity_1_2.put("ROUND1LVL1TITLE", "title-1");
        entity_1_2.put("ROUND1LVL1AMOUNT", 111);

        entity_1_2.put("ROUND2LVL1ID", 2L);

        Map<String, Object> entity_1_3 = new HashMap<>();

        entity_1_3.put("ROOTLVLID", 1L);
        entity_1_3.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_3.put("ROOTLVLNAME", "test-name-1");
        entity_1_3.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_3.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_3.put("ROUND1LVL1ID", 1L);
        entity_1_3.put("ROUND1LVL1TITLE", "title-1");
        entity_1_3.put("ROUND1LVL1AMOUNT", 111);

        entity_1_3.put("ROUND2LVL1ID", 3L);

        Map<String, Object> entity_1_4 = new HashMap<>();

        entity_1_4.put("ROOTLVLID", 1L);
        entity_1_4.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_4.put("ROOTLVLNAME", "test-name-1");
        entity_1_4.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_4.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_4.put("ROUND1LVL1ID", 1L);
        entity_1_4.put("ROUND1LVL1TITLE", "title-1");
        entity_1_4.put("ROUND1LVL1AMOUNT", 111);

        entity_1_4.put("ROUND2LVL1ID", 4L);

        Map<String, Object> entity_1_5 = new HashMap<>();

        entity_1_5.put("ROOTLVLID", 1L);
        entity_1_5.put("ROOTLVLVINCODE", "4324235-5435345-7657567");
        entity_1_5.put("ROOTLVLNAME", "test-name-1");
        entity_1_5.put("ROOTLVLFIRSTNAME", "first-test-name-1");
        entity_1_5.put("ROOTLVLLASTNAME", "last-test-name-1");

        entity_1_5.put("ROUND1LVL1ID", 1L);
        entity_1_5.put("ROUND1LVL1TITLE", "title-1");
        entity_1_5.put("ROUND1LVL1AMOUNT", 111);

        entity_1_5.put("ROUND2LVL1ID", 5L);

        return List.of(
                entity_1_1,
                entity_1_2,
                entity_1_3,
                entity_1_4,
                entity_1_5
        );
    }

    public static DependenciesGraph graph() {
        Root root = new Root(
                RootLvl.class,
                List.of(
                        new SubGraph.Builder()
                                .rootType(RootLvl.class)
                                .currentType(Round1Lvl1.class)
                                .currentFieldName("rootLvl")
                                .rootFieldName("round1Lvl1")
                                .build(),
                        new SubGraph.Builder()
                                .rootType(RootLvl.class)
                                .currentType(Round2Lvl1.class)
                                .rootFieldName("round2Lvl1s")
                                .rootCollType(List.class)
                                .build()
                )
        );
        return new DependenciesGraph(root);
    }

    public static List<RootLvl> expect_many() {
        return List.of(
                new RootLvl(
                        1L,
                        "4324235-5435345-7657567",
                        "test-name-1",
                        "first-test-name-1",
                        "last-test-name-1"
                ).round1Lvl1(new Round1Lvl1(
                                1L,
                                "title-1",
                                111
                        ))
                        .round2Lvl1s(new Round2Lvl1(1L))
                        .round2Lvl1s(new Round2Lvl1(2L))
                        .round2Lvl1s(new Round2Lvl1(3L))
                        .round2Lvl1s(new Round2Lvl1(4L))
                        .round2Lvl1s(new Round2Lvl1(5L)),
                new RootLvl(
                        2L,
                        "5436475-1231234-321324",
                        "test-name-2",
                        "first-test-name-2",
                        "last-test-name-2"
                ).round1Lvl1(new Round1Lvl1(
                                2L,
                                "title-2",
                                222
                        ))
                        .round2Lvl1s(new Round2Lvl1(6L))
                        .round2Lvl1s(new Round2Lvl1(7L))
                        .round2Lvl1s(new Round2Lvl1(8L))
                        .round2Lvl1s(new Round2Lvl1(9L))
                        .round2Lvl1s(new Round2Lvl1(10L)),
                new RootLvl(
                        3L,
                        "432556456-1234324-488796",
                        "test-name-3",
                        "first-test-name-3",
                        "last-test-name-3"
                ).round1Lvl1(new Round1Lvl1(
                                3L,
                                "title-3",
                                333
                        ))
                        .round2Lvl1s(new Round2Lvl1(11L))
                        .round2Lvl1s(new Round2Lvl1(12L))
                        .round2Lvl1s(new Round2Lvl1(13L))
                        .round2Lvl1s(new Round2Lvl1(14L))
                        .round2Lvl1s(new Round2Lvl1(15L)),
                new RootLvl(
                        4L,
                        "858888-4777666-2355588",
                        "test-name-4",
                        "first-test-name-4",
                        "last-test-name-4"
                ).round1Lvl1(new Round1Lvl1(
                                4L,
                                "title-4",
                                444
                        ))
                        .round2Lvl1s(new Round2Lvl1(16L))
                        .round2Lvl1s(new Round2Lvl1(17L))
                        .round2Lvl1s(new Round2Lvl1(18L))
                        .round2Lvl1s(new Round2Lvl1(19L))
                        .round2Lvl1s(new Round2Lvl1(20L)),
                new RootLvl(
                        5L,
                        "54566654-1112333-0099977",
                        "test-name-5",
                        "first-test-name-5",
                        "last-test-name-5"
                ).round1Lvl1(new Round1Lvl1(
                                5L,
                                "title-5",
                                555
                        ))
                        .round2Lvl1s(new Round2Lvl1(21L))
                        .round2Lvl1s(new Round2Lvl1(22L))
                        .round2Lvl1s(new Round2Lvl1(23L))
                        .round2Lvl1s(new Round2Lvl1(24L))
                        .round2Lvl1s(new Round2Lvl1(25L))
        );
    }

    public static RootLvl expect() {
        return new RootLvl(
                1L,
                "4324235-5435345-7657567",
                "test-name-1",
                "first-test-name-1",
                "last-test-name-1"
        ).round1Lvl1(new Round1Lvl1(
                        1L,
                        "title-1",
                        111
                ))
                .round2Lvl1s(new Round2Lvl1(1L))
                .round2Lvl1s(new Round2Lvl1(2L))
                .round2Lvl1s(new Round2Lvl1(3L))
                .round2Lvl1s(new Round2Lvl1(4L))
                .round2Lvl1s(new Round2Lvl1(5L));
    }


}
