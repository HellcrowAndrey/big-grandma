package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependenciesGraphCase8 {


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
        entity_1_1.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_1_1.put("ROUND2LVL1TAG", 1);
        entity_1_1.put("ROUND2LVL1NAME", "name-1");

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
        entity_1_2.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_1_2.put("ROUND2LVL1TAG", 2);
        entity_1_2.put("ROUND2LVL1NAME", "name-2");

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
        entity_1_3.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_1_3.put("ROUND2LVL1TAG", 3);
        entity_1_3.put("ROUND2LVL1NAME", "name-3");

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
        entity_1_4.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_1_4.put("ROUND2LVL1TAG", 4);
        entity_1_4.put("ROUND2LVL1NAME", "name-4");

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
        entity_1_5.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_1_5.put("ROUND2LVL1TAG", 5);
        entity_1_5.put("ROUND2LVL1NAME", "name-5");

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
        entity_2_1.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_2_1.put("ROUND2LVL1TAG", 6);
        entity_2_1.put("ROUND2LVL1NAME", "name-6");

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
        entity_2_2.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_2_2.put("ROUND2LVL1TAG", 7);
        entity_2_2.put("ROUND2LVL1NAME", "name-7");

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
        entity_2_3.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_2_3.put("ROUND2LVL1TAG", 8);
        entity_2_3.put("ROUND2LVL1NAME", "name-8");

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
        entity_2_4.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_2_4.put("ROUND2LVL1TAG", 9);
        entity_2_4.put("ROUND2LVL1NAME", "name-9");

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
        entity_2_5.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_2_5.put("ROUND2LVL1TAG", 10);
        entity_2_5.put("ROUND2LVL1NAME", "name-10");

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
        entity_3_1.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_3_1.put("ROUND2LVL1TAG", 11);
        entity_3_1.put("ROUND2LVL1NAME", "name-11");

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
        entity_3_2.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_3_2.put("ROUND2LVL1TAG", 12);
        entity_3_2.put("ROUND2LVL1NAME", "name-12");

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
        entity_3_3.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_3_3.put("ROUND2LVL1TAG", 13);
        entity_3_3.put("ROUND2LVL1NAME", "name-13");

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
        entity_3_4.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_3_4.put("ROUND2LVL1TAG", 14);
        entity_3_4.put("ROUND2LVL1NAME", "name-14");

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
        entity_3_5.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_3_5.put("ROUND2LVL1TAG", 15);
        entity_3_5.put("ROUND2LVL1NAME", "name-15");

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
        entity_4_1.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_4_1.put("ROUND2LVL1TAG", 16);
        entity_4_1.put("ROUND2LVL1NAME", "name-16");

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
        entity_4_2.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_4_2.put("ROUND2LVL1TAG", 17);
        entity_4_2.put("ROUND2LVL1NAME", "name-17");

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
        entity_4_3.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_4_3.put("ROUND2LVL1TAG", 18);
        entity_4_3.put("ROUND2LVL1NAME", "name-18");

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
        entity_4_4.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_4_4.put("ROUND2LVL1TAG", 19);
        entity_4_4.put("ROUND2LVL1NAME", "name-19");

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
        entity_4_5.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_4_5.put("ROUND2LVL1TAG", 20);
        entity_4_5.put("ROUND2LVL1NAME", "name-20");

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
        entity_5_1.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_5_1.put("ROUND2LVL1TAG", 21);
        entity_5_1.put("ROUND2LVL1NAME", "name-21");

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
        entity_5_2.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_5_2.put("ROUND2LVL1TAG", 22);
        entity_5_2.put("ROUND2LVL1NAME", "name-22");

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
        entity_5_3.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_5_3.put("ROUND2LVL1TAG", 23);
        entity_5_3.put("ROUND2LVL1NAME", "name-23");

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
        entity_5_4.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_5_4.put("ROUND2LVL1TAG", 24);
        entity_5_4.put("ROUND2LVL1NAME", "name-24");

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
        entity_5_5.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_5_5.put("ROUND2LVL1TAG", 25);
        entity_5_5.put("ROUND2LVL1NAME", "name-25");

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
        entity_1_1.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_1_1.put("ROUND2LVL1TAG", 1);
        entity_1_1.put("ROUND2LVL1NAME", "name-1");

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
        entity_1_2.put("ROUND2LVL1ISFLAG", Boolean.FALSE);
        entity_1_2.put("ROUND2LVL1TAG", 2);
        entity_1_2.put("ROUND2LVL1NAME", "name-2");

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
        entity_1_3.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_1_3.put("ROUND2LVL1TAG", 3);
        entity_1_3.put("ROUND2LVL1NAME", "name-3");

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
        entity_1_4.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_1_4.put("ROUND2LVL1TAG", 4);
        entity_1_4.put("ROUND2LVL1NAME", "name-4");

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
        entity_1_5.put("ROUND2LVL1ISFLAG", Boolean.TRUE);
        entity_1_5.put("ROUND2LVL1TAG", 5);
        entity_1_5.put("ROUND2LVL1NAME", "name-5");

        return List.of(
                entity_1_1,
                entity_1_2,
                entity_1_3,
                entity_1_4,
                entity_1_5
        );
    }

    public static DependenciesGraph graph() {
        DependenciesGraph.Root root = new DependenciesGraph.Root(
                RootLvl.class,
                List.of(
                        new SubGraph.DefaultBuilder()
                                .rootType(RootLvl.class)
                                .currentType(Round1Lvl1.class)
                                .currentFieldName("rootLvl")
                                .rootFieldName("round1Lvl1")
                                .build(),
                        new SubGraph.DefaultBuilder()
                                .rootType(RootLvl.class)
                                .currentType(Round2Lvl1.class)
                                .currentFieldName("rootLvl")
                                .rootFieldName("round2Lvl1s")
                                .collType(List.class)
                                .build(),
                        new SubGraph.DefaultBuilder()
                                .rootType(RootLvl.class)
                                .currentType(Round3ManyToManyLvl1.class)
                                .rootFieldName("round3ManyToManyLvl1s")
                                .collType(List.class)
                                .graphs(List.of(
                                        new SubGraph.DefaultBuilder()
                                                .rootType(Round3ManyToManyLvl1.class)
                                                .currentType(Round4ManyToManyLvl2.class)
                                                .rootFieldName("round4ManyToManyLvl2s")
                                                .collType(List.class)
                                                .build()
                                )).build()
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
                        .round2Lvl1s(new Round2Lvl1(
                                1L,
                                Boolean.FALSE,
                                1,
                                "name-1"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                2L,
                                Boolean.FALSE,
                                2,
                                "name-2"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                3L,
                                Boolean.TRUE,
                                3,
                                "name-3"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                4L,
                                Boolean.TRUE,
                                4,
                                "name-4"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                5L,
                                Boolean.FALSE,
                                5,
                                "name-5"
                        )),
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
                        .round2Lvl1s(new Round2Lvl1(
                                6L,
                                Boolean.TRUE,
                                6,
                                "name-6"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                7L,
                                Boolean.FALSE,
                                7,
                                "name-7"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                8L,
                                Boolean.TRUE,
                                8,
                                "name-8"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                9L,
                                Boolean.FALSE,
                                9,
                                "name-9"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                10L,
                                Boolean.FALSE,
                                10,
                                "name-10"
                        )),
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
                        .round2Lvl1s(new Round2Lvl1(
                                11L,
                                Boolean.TRUE,
                                11,
                                "name-11"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                12L,
                                Boolean.TRUE,
                                12,
                                "name-12"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                13L,
                                Boolean.TRUE,
                                13,
                                "name-13"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                14L,
                                Boolean.TRUE,
                                14,
                                "name-14"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                15L,
                                Boolean.TRUE,
                                15,
                                "name-15"
                        )),
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
                        .round2Lvl1s(new Round2Lvl1(
                                16L,
                                Boolean.TRUE,
                                16,
                                "name-16"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                17L,
                                Boolean.TRUE,
                                17,
                                "name-17"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                18L,
                                Boolean.TRUE,
                                18,
                                "name-18"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                19L,
                                Boolean.TRUE,
                                19,
                                "name-19"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                20L,
                                Boolean.FALSE,
                                20,
                                "name-20"
                        )),
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
                        .round2Lvl1s(new Round2Lvl1(
                                21L,
                                Boolean.FALSE,
                                21,
                                "name-21"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                22L,
                                Boolean.TRUE,
                                22,
                                "name-22"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                23L,
                                Boolean.TRUE,
                                23,
                                "name-23"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                24L,
                                Boolean.TRUE,
                                24,
                                "name-24"
                        ))
                        .round2Lvl1s(new Round2Lvl1(
                                25L,
                                Boolean.FALSE,
                                25,
                                "name-25"
                        ))
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
                .round2Lvl1s(new Round2Lvl1(
                        1L,
                        Boolean.FALSE,
                        1,
                        "name-1"
                ))
                .round2Lvl1s(new Round2Lvl1(
                        2L,
                        Boolean.FALSE,
                        2,
                        "name-2"
                ))
                .round2Lvl1s(new Round2Lvl1(
                        3L,
                        Boolean.TRUE,
                        3,
                        "name-3"
                ))
                .round2Lvl1s(new Round2Lvl1(
                        4L,
                        Boolean.TRUE,
                        4,
                        "name-4"
                ))
                .round2Lvl1s(new Round2Lvl1(
                        5L,
                        Boolean.TRUE,
                        5,
                        "name-5"
                ));
    }

}
