package com.github.mapper.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

class MapperUtilsTest {

    @Test
    void givenEntityWithGenericString_whenFindGenericOfColl_theReturnType() {
        Class<Entity> clz = Entity.class;
        Field[] fields = clz.getDeclaredFields();
        Class<?> act = MapperUtils.findGenericOfColl(fields[0]);
        Assertions.assertEquals(String.class, act);
    }

    @Test
    void givenEntityWithGenericPerson_whenFindGenericOfColl_theReturnType() {
        Class<Entity2> clz = Entity2.class;
        Field[] fields = clz.getDeclaredFields();
        Class<?> act = MapperUtils.findGenericOfColl(fields[0]);
        Assertions.assertEquals(Person.class, act);
    }

    public static class Entity {
        List<String> strings;
    }

    public static class Entity2 {
        List<Person> persons;
    }

    public static class Person {

    }

}