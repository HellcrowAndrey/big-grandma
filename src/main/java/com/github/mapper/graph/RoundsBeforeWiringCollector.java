package com.github.mapper.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RoundsBeforeWiringCollector  implements Collector<Round, List<Round>, List<Round>> {

    private RoundsBeforeWiringCollector() {
    }

    public static RoundsBeforeWiringCollector toListOfRounds() {
        return new RoundsBeforeWiringCollector();
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
                if (Objects.nonNull(round.value)) {
                    list.forEach(r -> {
                        r.merge(round);
                        round.merge(r);
                    });
                    list.add(round);
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
