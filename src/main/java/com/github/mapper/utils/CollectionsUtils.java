package com.github.mapper.utils;

import com.github.mapper.graph.GeneralRounds;

import java.util.*;

public class CollectionsUtils {

    public static Set<GeneralRounds> singleCollSet(GeneralRounds round) {
        Set<GeneralRounds> result = new HashSet<>();
        result.add(round);
        return result;
    }

    public static Set<GeneralRounds> singleCollSet(GeneralRounds... rounds) {
        return new HashSet<>(Arrays.asList(rounds));
    }

    public static List<GeneralRounds> singleCollList(GeneralRounds round) {
        List<GeneralRounds> result = new ArrayList<>();
        result.add(round);
        return result;
    }

    public static List<GeneralRounds> singleCollList(GeneralRounds... rounds) {
        return Arrays.asList(rounds);
    }

}
