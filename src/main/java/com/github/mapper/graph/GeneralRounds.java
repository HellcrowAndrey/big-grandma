package com.github.mapper.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GeneralRounds extends DefaultRounds, ManyToManyRounds {

    @Override
    default Class<?> type() {
        return null;
    }

    @Override
    default Object value() {
        return null;
    }

    @Override
    default Set<GeneralRounds> rounds() {
        return null;
    }

    @Override
    default void addRound(GeneralRounds round) {

    }

    @Override
    default void collectRounds(GeneralRounds rounds) {

    }

    @Override
    default List<GeneralRounds> findRoundByLvl(int lvl) {
        return null;
    }

    @Override
    default int levels() {
        return 0;
    }

    @Override
    default void addRound(GeneralRounds top, GeneralRounds bottom) {

    }

    @Override
    default Map<GeneralRounds, Map<GeneralRounds, Set<GeneralRounds>>> roundsManyToMany() {
        return null;
    }

}
