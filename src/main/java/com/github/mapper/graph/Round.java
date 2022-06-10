package com.github.mapper.graph;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Round {

    int lvl;

    Class<?> type;

    Object value;

    Set<Round> roundsOneToEtc = new HashSet<>();

    public Round(int lvl, Class<?> type, Object value) {
        this.lvl = lvl;
        this.type = type;
        this.value = value;
    }

    abstract void collectRounds(Round round);

    void collectRoundOneToEtc(Round round) {
        int levels = round.levels();
        for (int i = 1; i < levels; i++) {
            List<Round> currentRounds = findRoundByLvl(i);
            List<Round> newRounds = round.findRoundByLvl(i);
            if (!currentRounds.containsAll(newRounds)) {
                this.roundsOneToEtc.addAll(newRounds.stream()
                        .filter(r -> Objects.nonNull(r.value))
                        .collect(Collectors.toList())
                );
            }
        }
    }

    int levels() {
        int lvl = 0;
        for (Round r : this.roundsOneToEtc) {
            if (r.roundsOneToEtc.size() == 0) {
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

    List<Round> findRoundByLvl(int lvl) {
        List<Round> result = new ArrayList<>();
        if (this.lvl < lvl) {
            for (Round round : this.roundsOneToEtc) {
                result.addAll(round.findRoundByLvl(lvl));
            }
        } else {
            result.addAll(this.roundsOneToEtc);
        }
        return result;
    }

    void addRound(Round round) {
        this.roundsOneToEtc.add(round);
    }

    public static Round oneToEtc(int lvl, Class<?> type, Object value) {
        return new Round(lvl, type, value) {
            @Override
            void collectRounds(Round round) {
                collectRoundOneToEtc(round);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round)) return false;
        Round round = (Round) o;
        return lvl == round.lvl &&
                Objects.equals(type, round.type) &&
                Objects.equals(value, round.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lvl, type, value);
    }

}
