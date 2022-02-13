package com.github.mapper.graph;

import java.util.*;

public class Round {

    Round prev;

    int lvl;

    Class<?> type;

    Object value;

    Set<Round> rounds = new HashSet<>();

    public Round(int lvl, Class<?> type, Object value) {
        this.lvl = lvl;
        this.type = type;
        this.value = value;
    }

    public void addRound(Round round) {
        round.prev = this;
        this.rounds.add(round);
    }

    public List<Round> findRoundByLvl(int lvl) {
        List<Round> result = new ArrayList<>();
        if (this.lvl < lvl) {
            for (Round round : this.rounds) {
                result.addAll(round.findRoundByLvl(lvl));
            }
        } else {
            result.addAll(this.rounds);
        }
        return result;
    }

    public int levels() {
        int lvl = 0;
        for (Round r : this.rounds) {
            if (r.rounds.size() == 0) {
                if (r.lvl > lvl) {
                    lvl = r.lvl;
                }
            }
        }
        if (lvl == 0) {
            lvl = this.lvl;
        }
        return lvl;
    }

    public void addRounds(Round round) {
        int levels = round.levels();
        for (int i = 1; i < levels; i++) {
            List<Round> currentRounds = findRoundByLvl(i);
            List<Round> newRounds = round.findRoundByLvl(i);
            if (!currentRounds.containsAll(newRounds)) {
                this.rounds.addAll(newRounds);
            }
        }
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
