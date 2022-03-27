package com.github.mapper.utils;

import com.github.mapper.graph.Round;

import java.util.*;

public class CollectionsUtils {

    public static Set<Object> singleSet(Object round) {
        Set<Object> result = new HashSet<>();
        result.add(round);
        return result;
    }

    public static Set<Round> singleSet(Round... rounds) {
        return new HashSet<>(Arrays.asList(rounds));
    }

    public static List<Round> singleList(Round round) {
        List<Round> result = new ArrayList<>();
        result.add(round);
        return result;
    }

    public static List<Round> singleList(Round... rounds) {
        return Arrays.asList(rounds);
    }

    public static Set<Object> emptySet() {
        return new HashSet<>();
    }

    public static Set<Round> setOfRounds(Round... rounds) {
        return new HashSet<>(Arrays.asList(rounds));
    }

    public static <T> Set<T> genericSet(T ... rounds) {
        return new HashSet<>(Arrays.asList(rounds));
    }

}
