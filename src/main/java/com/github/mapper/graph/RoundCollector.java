package com.github.mapper.graph;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RoundCollector implements Collector<GeneralRounds, List<GeneralRounds>, List<GeneralRounds>> {

    private RoundCollector() {
    }

    public static RoundCollector toListOfRounds() {
        return new RoundCollector();
    }

    @Override
    public Supplier<List<GeneralRounds>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<GeneralRounds>, GeneralRounds> accumulator() {
        return (list, round) -> {
            if (list.isEmpty()) {
                if (Objects.nonNull(round.value())) {
                    list.add(round);
                }
            } else {
                if (list.contains(round)) {
                    if (Objects.nonNull(round.value())) {
                        GeneralRounds containsRound = list.get(list.indexOf(round));
                        containsRound.collectRounds(round);
                    }
                } else {
                    if (Objects.nonNull(round.value())) {
                        list.add(round);
                    }
                }
            }
        };
    }

    @Override
    public BinaryOperator<List<GeneralRounds>> combiner() {
        return (first, second) -> {
            first.addAll(second);
            return first;
        };
    }

    @Override
    public Function<List<GeneralRounds>, List<GeneralRounds>> finisher() {
        return ArrayList::new;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
