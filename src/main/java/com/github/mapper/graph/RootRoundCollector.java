package com.github.mapper.graph;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RootRoundCollector implements Collector<RootRound, List<RootRound>, List<RootRound>> {

    private final Map<Object, Object> rightValues = new HashMap<>();

    private RootRoundCollector() {
    }

    public static RootRoundCollector toListOfRootRounds() {
        return new RootRoundCollector();
    }

    @Override
    public Supplier<List<RootRound>> supplier() {
        return LinkedList::new;
    }

    @Override
    public BiConsumer<List<RootRound>, RootRound> accumulator() {
        return (list, round) -> {
            if (list.isEmpty()) {
                list.add(round);
            } else {
                var val = round.value;
                if (Objects.nonNull(val)) {
                    if (!this.rightValues.containsKey(val)) {
                        this.rightValues.put(val, val);
                    }
                    if (!list.contains(round)) {
                        list.add(round);
                    } else {
                        RootRound containsRound = list.get(list.indexOf(round));
                        containsRound.addAll(round.nonMappedValues);
                    }
                }
            }
        };
    }

    @Override
    public BinaryOperator<List<RootRound>> combiner() {
        return (first, second) -> {
            first.addAll(second);
            return first;
        };
    }

    @Override
    public Function<List<RootRound>, List<RootRound>> finisher() {
        return ArrayList::new;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
