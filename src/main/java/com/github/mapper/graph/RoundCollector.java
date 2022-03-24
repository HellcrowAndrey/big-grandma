package com.github.mapper.graph;

import com.github.mapper.utils.CollectionsUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RoundCollector implements Collector<GeneralRounds, List<GeneralRounds>, List<GeneralRounds>> {

    private final Map<Class<?>, List<GeneralRounds>> manyToMany = new HashMap<>();

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
                    if (round.roundType().isNotDefault()) {
                        this.manyToMany.put(round.type(), CollectionsUtils.singleList(round));
                    }
                }
            } else {
                if (Objects.nonNull(round.value())) {
                    if (list.contains(round)) {
                        GeneralRounds containsRound = list.get(list.indexOf(round));
                        containsRound.collectRounds(round);
                    } else {
                        list.add(round);
                    }
                    updateIfManyToMany(round);
                }
            }
        };
    }

    private void updateIfManyToMany(GeneralRounds round) {
        if (round.roundType().isNotDefault()) {
            Class<?> type = round.type();
            List<GeneralRounds> manyToManyList = this.manyToMany.getOrDefault(type, new ArrayList<>());
            if (!manyToManyList.isEmpty()) {
                manyToManyList.forEach(r -> {
                            r.collectRoundsLeft(round.lefts());
                            round.collectRoundsLeft(r.lefts());
                        });
                if (!manyToManyList.contains(round)) {
                    manyToManyList.add(round);
                }
            } else {
                manyToManyList.add(round);
                this.manyToMany.put(type, manyToManyList);
            }
        }
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
