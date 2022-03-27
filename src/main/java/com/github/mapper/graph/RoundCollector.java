package com.github.mapper.graph;

import com.github.mapper.utils.CollectionsUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RoundCollector implements Collector<Round, List<Round>, List<Round>> {

    private final Map<Class<?>, List<Round>> rightValues = new HashMap<>();

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
                    if (round.hashManyToMany()) {
                        this.rightValues.put(round.type, CollectionsUtils.singleList(round));
                    }
                }
            } else {
                if (Objects.nonNull(round.value)) {
                    if (list.contains(round)) {
                        Round containsRound = list.get(list.indexOf(round));
                        containsRound.collectRounds(round);
                    } else {
                        list.add(round);
                    }
                    updateIfManyToMany(round);
                }
            }
        };
    }

    private void updateIfManyToMany(Round round) {
        if (round.hashManyToMany()) {
            Class<?> type = round.type;
            List<Round> manyToManyList = this.rightValues.getOrDefault(type, new ArrayList<>());
            if (!manyToManyList.isEmpty()) {
                manyToManyList.forEach(r -> {
                            r.collectRoundsLeft(round.lefts);
                            round.collectRoundsLeft(r.lefts);
                        });
                if (!manyToManyList.contains(round)) {
                    manyToManyList.add(round);
                }
            } else {
                manyToManyList.add(round);
                this.rightValues.put(type, manyToManyList);
            }
        }
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
