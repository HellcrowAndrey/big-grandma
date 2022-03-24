package com.github.mapper.graph;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RootRoundCollector implements Collector<DependenciesGraph.RootRound, List<DependenciesGraph.RootRound>, List<DependenciesGraph.RootRound>> {

    private final Map<Object, Object> rightValues  = new HashMap<>();

    private RootRoundCollector() {
    }

    public static RootRoundCollector toListOfRootRounds() {
        return new RootRoundCollector();
    }

    @Override
    public Supplier<List<DependenciesGraph.RootRound>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<DependenciesGraph.RootRound>, DependenciesGraph.RootRound> accumulator() {
        return (list, round) -> {
            if (list.isEmpty()) {
                if (round.hashManyToMany()) {
                    this.rightValues.put(round.value, round.value);
                }
                list.add(round);
            } else {
                if (!round.hashManyToMany()) {
                    if (!list.contains(round)) {
                        list.add(round);
                    } else {
                        DependenciesGraph.RootRound containsRound = list.get(list.indexOf(round));
                        containsRound.addAll(round.nonMappedValues);
                    }
                } else {
                    var val = round.value;
                    if (Objects.nonNull(val)) {
                        if (!this.rightValues.containsKey(val)) {
                            this.rightValues.put(val, val);
                        }
                        list.forEach(roundMTM -> {
                            roundMTM.collectRoundLeft(this.rightValues, round.value, round.lefts);
                            round.collectRoundLeft(this.rightValues, roundMTM.value, roundMTM.lefts);
                        });
                        if (!list.contains(round)) {
                            list.add(round);
                        } else {
                            DependenciesGraph.RootRound containsRound = list.get(list.indexOf(round));
                            containsRound.addAll(round.nonMappedValues);
                        }
                    }
                }
            }
        };
    }

    @Override
    public BinaryOperator<List<DependenciesGraph.RootRound>> combiner() {
        return (first, second) -> {
            first.addAll(second);
            return first;
        };
    }

    @Override
    public Function<List<DependenciesGraph.RootRound>, List<DependenciesGraph.RootRound>> finisher() {
        return ArrayList::new;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
