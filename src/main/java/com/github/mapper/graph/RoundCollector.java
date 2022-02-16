package com.github.mapper.graph;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RoundCollector implements Collector<Round, List<Round>, List<Round>> {

    private RoundCollector() {
    }

    public static RoundCollector toListOfRounds() {
        return new RoundCollector();
    }

    @Override
    public Supplier<List<Round>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Round>, Round> accumulator() {
        return (list, round) -> {
            if (list.isEmpty()) {
                if (Objects.nonNull(round.value)) {
                    list.add(round);
                }
            } else {
                if (list.contains(round)) {
                    if (Objects.nonNull(round.value)) {
                        Round containsRound = list.get(list.indexOf(round));
                        containsRound.addRounds(round);
                    }
                } else {
                    if (Objects.nonNull(round.value)) {
                        list.add(round);
                    }
                }
            }
        };
    }

    @Override
    public BinaryOperator<List<Round>> combiner() {
        return (first, second) -> {
            first.addAll(second);
            return first;
        };
    }

    @Override
    public Function<List<Round>, List<Round>> finisher() {
        return ArrayList::new;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
