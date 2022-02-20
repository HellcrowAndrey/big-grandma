package com.github.mapper.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Round implements GeneralRounds {

    int lvl;

    Class<?> type;

    Object value;

    Set<GeneralRounds> rounds = new HashSet<>();

    private Round(int lvl, Class<?> type, Object value) {
        this.lvl = lvl;
        this.type = type;
        this.value = value;
    }

    public static GeneralRounds create(int lvl, Class<?> type, Object value) {
        return new Round(lvl, type, value);
    }

    @Override
    public void addRound(GeneralRounds round) {
        this.rounds.add(round);
    }

    public List<GeneralRounds> findRoundByLvl(int lvl) {
        List<GeneralRounds> result = new ArrayList<>();
        if (this.lvl < lvl) {
            for (GeneralRounds round : this.rounds) {
                result.addAll(round.findRoundByLvl(lvl));
            }
        } else {
            result.addAll(this.rounds);
        }
        return result;
    }

    @Override
    public int levels() {
        int lvl = 0;
        for (GeneralRounds r : this.rounds) {
            if (r.rounds().size() == 0) {
                if (r.levels() > lvl) {
                    lvl = r.levels();
                }
            }
        }
        if (lvl == 0) {
            lvl = this.lvl;
        }
        return lvl;
    }

    @Override
    public void collectRounds(GeneralRounds round) {
        int levels = round.levels();
        for (int i = 1; i < levels; i++) {
            List<GeneralRounds> currentRounds = findRoundByLvl(i);
            List<GeneralRounds> newRounds = round.findRoundByLvl(i);
            if (!currentRounds.containsAll(newRounds)) {
                this.rounds.addAll(newRounds.stream()
                        .filter(r -> Objects.nonNull(r.value()))
                        .collect(Collectors.toList())
                );
            }
        }
    }

    @Override
    public RoundType roundType() {
        return RoundType.def;
    }

    @Override
    public Class<?> type() {
        return this.type;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public Set<GeneralRounds> rounds() {
        return this.rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round)) return false;
        Round round = (Round) o;
        return Objects.equals(type, round.type) &&
                Objects.equals(value, round.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
