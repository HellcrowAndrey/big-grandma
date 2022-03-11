package com.github.mapper.graph;

import java.util.Map;
import java.util.Set;

public interface ManyToManyRounds {

    void putLeft(GeneralRounds left, Object value);

    GeneralRounds right();

    Map<GeneralRounds, Set<Object>> lefts();

    void collectRoundsLeft(Map<GeneralRounds, Set<Object>> newLefts);

    Class<?> rightType();

}
