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
    default void collectRounds(GeneralRounds rounds) {

    }

    @Override
    default void collectRoundsLeft(Map<GeneralRounds, Set<Object>> newLefts) {
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
    default void putLeft(GeneralRounds left, Object value) {
    }

    @Override
    default void addRound(GeneralRounds round) {
    }

    @Override
    default GeneralRounds right(){
        return null;
    }

    @Override
    default Map<GeneralRounds, Set<Object>> lefts() {
        return null;
    }

    @Override
    default Class<?> rightType() {
        return null;
    }

}
