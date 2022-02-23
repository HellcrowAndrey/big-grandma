package com.github.mapper.graph;

import java.util.List;
import java.util.Set;

public interface DefaultRounds {

    RelationType roundType();

    Class<?> type();

    Object value();

    Set<GeneralRounds> rounds();

    void addRound(GeneralRounds round);

    void collectRounds(GeneralRounds round);

    int levels();

    List<GeneralRounds> findRoundByLvl(int lvl);

}
