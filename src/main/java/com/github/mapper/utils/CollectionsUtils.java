package com.github.mapper.utils;

import com.github.mapper.graph.GeneralRounds;

import java.util.*;

public class CollectionsUtils {

    public static Set<Object> singleSet(Object round) {
        Set<Object> result = new HashSet<>();
        result.add(round);
        return result;
    }

    public static Set<GeneralRounds> singleSet(GeneralRounds... rounds) {
        return new HashSet<>(Arrays.asList(rounds));
    }

    public static List<GeneralRounds> singleList(GeneralRounds round) {
        List<GeneralRounds> result = new ArrayList<>();
        result.add(round);
        return result;
    }

    public static List<GeneralRounds> singleList(GeneralRounds... rounds) {
        return Arrays.asList(rounds);
    }

    public static Set<Object> emptySet() {
        return new HashSet<>();
    }

}
