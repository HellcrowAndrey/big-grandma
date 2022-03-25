package com.github.mapper.graph;

import com.github.mapper.utils.CollectionsUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Round02 {

    int lvl;

    Class<?> type;

    Object value;

    Set<Round02> roundsOneToEtc = new HashSet<>();

    Map<Round02, Set<Round02>> lefts = new HashMap<>();

    public Round02(int lvl, Class<?> type, Object value) {
        this.lvl = lvl;
        this.type = type;
        this.value = value;
    }

    abstract void collectRounds(Round02 round);

    abstract void collectRoundsLeft(Map<Round02, Set<Round02>> newLefts);

    abstract void putLeft(Round02 left, Round02 value);

    abstract boolean hashManyToMany();

    void collectRoundOneToEtc(Round02 round) {
        int levels = round.levels();
        for (int i = 1; i < levels; i++) {
            List<Round02> currentRounds = findRoundByLvl(i);
            List<Round02> newRounds = round.findRoundByLvl(i);
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
        for (Round02 r : this.roundsOneToEtc) {
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

    List<Round02> findRoundByLvl(int lvl) {
        List<Round02> result = new ArrayList<>();
        if (this.lvl < lvl) {
            for (Round02 round : this.roundsOneToEtc) {
                result.addAll(round.findRoundByLvl(lvl));
            }
        } else {
            result.addAll(this.roundsOneToEtc);
        }
        return result;
    }

    void addRound(Round02 round) {
        this.roundsOneToEtc.add(round);
    }

    public static Round02 oneToEtc(int lvl, Class<?> type, Object value) {
        return new Round02(lvl, type, value) {
            @Override
            void collectRounds(Round02 round) {
                collectRoundOneToEtc(round);
            }

            @Override
            void collectRoundsLeft(Map<Round02, Set<Round02>> newLefts) {
                throw new UnsupportedOperationException("Not support in one to etc round");
            }

            @Override
            void putLeft(Round02 left, Round02 value) {
                throw new UnsupportedOperationException("Not support in one to etc round");
            }

            @Override
            boolean hashManyToMany() {
                return Boolean.FALSE;
            }
        };
    }

    public static Round02 manyToMany(int lvl, Class<?> type, Object value) {
        return new Round02(lvl, type, value) {
            @Override
            void collectRounds(Round02 round) {
                collectRoundOneToEtc(round);
                Map<Round02, Set<Round02>> newLefts = round.lefts;
                for (Round02 left : newLefts.keySet()) {
                    if (this.lefts.containsKey(left)) {
                        this.lefts.get(left).addAll(newLefts.get(left));
                        findRound(this.lefts.keySet(), left)
                                .ifPresent(left::collectRounds);
                    } else {
                        this.lefts.put(left, newLefts.get(left));
                    }
                }
            }

            private Optional<Round02> findRound(Set<Round02> keys, Round02 newRound) {
                return keys.stream().filter(key -> key.equals(newRound)).findFirst();
            }

            @Override
            void collectRoundsLeft(Map<Round02, Set<Round02>> newLefts) {
                for (Round02 left : newLefts.keySet()) {
                    if (this.lefts.containsKey(left)) {
                        this.lefts.get(left).addAll(newLefts.get(left));
                        findRound(this.lefts.keySet(), left)
                                .ifPresent(left::collectRounds);
                    }
                }
            }

            @Override
            void putLeft(Round02 left, Round02 value) {
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
        if (!(o instanceof Round02)) return false;
        Round02 round02 = (Round02) o;
        return lvl == round02.lvl &&
                Objects.equals(type, round02.type) &&
                Objects.equals(value, round02.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lvl, type, value);
    }
}
