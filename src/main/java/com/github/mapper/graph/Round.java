package com.github.mapper.graph;

import com.github.mapper.utils.CollectionsUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Round {

    int lvl;

    Class<?> type;

    Object value;

    Set<Round> roundsOneToEtc = new HashSet<>();

    Map<Round, Set<Round>> lefts = new HashMap<>();

    public Round(int lvl, Class<?> type, Object value) {
        this.lvl = lvl;
        this.type = type;
        this.value = value;
    }

    abstract void collectRounds(Round round);

    abstract void collectRoundsLeft(Map<Round, Set<Round>> newLefts);

    abstract void putLeft(Round left, Round value);

    abstract boolean hashManyToMany();

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

            @Override
            void collectRoundsLeft(Map<Round, Set<Round>> newLefts) {
                throw new UnsupportedOperationException("Not support in one to etc round");
            }

            @Override
            void putLeft(Round left, Round value) {
                throw new UnsupportedOperationException("Not support in one to etc round");
            }

            @Override
            boolean hashManyToMany() {
                return Boolean.FALSE;
            }
        };
    }

    public static Round manyToMany(int lvl, Class<?> type, Object value) {
        return new Round(lvl, type, value) {
            @Override
            void collectRounds(Round round) {
                collectRoundOneToEtc(round);
                Map<Round, Set<Round>> newLefts = round.lefts;
                for (Round left : newLefts.keySet()) {
                    if (this.lefts.containsKey(left)) {
                        this.lefts.get(left).addAll(newLefts.get(left));
                        findRound(this.lefts.keySet(), left)
                                .ifPresent(left::collectRounds);
                    } else {
                        this.lefts.put(left, newLefts.get(left));
                    }
                }
            }

            private Optional<Round> findRound(Set<Round> keys, Round newRound) {
                return keys.stream().filter(key -> key.equals(newRound)).findFirst();
            }

            @Override
            void collectRoundsLeft(Map<Round, Set<Round>> newLefts) {
                for (Round left : newLefts.keySet()) {
                    if (this.lefts.containsKey(left)) {
                        this.lefts.get(left).addAll(newLefts.get(left));
                        findRound(this.lefts.keySet(), left)
                                .ifPresent(left::collectRounds);
                    }
                }
            }

            @Override
            void putLeft(Round left, Round value) {
                this.lefts.put(left, CollectionsUtils.setOfRounds(value));
            }

            @Override
            boolean hashManyToMany() {
                return Boolean.TRUE;
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
