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

    public Round(int lvl, Class<?> type, Object value, Set<Round> roundsOneToEtc) {
        this.lvl = lvl;
        this.type = type;
        this.value = value;
        this.roundsOneToEtc = roundsOneToEtc;
    }

    abstract void mergeRounds(Round round);

    void updateLvl() {
        if (!this.roundsOneToEtc.isEmpty()) {
            for (Round round : this.roundsOneToEtc) {
                round.setLvl(this.lvl + 1);
            }
        }
    }

    void setLvl(int lvl) {
        this.lvl = lvl;
        if (!this.roundsOneToEtc.isEmpty()) {
            for (Round round : this.roundsOneToEtc) {
                round.setLvl(this.lvl + 1);
            }
        }
    }

    void merge(Round round) {
        if (this.type.equals(round.type)) {
            var lvl = Math.max(levels(), round.levels());
            List<Round> current = findRoundByLvl(lvl);
            List<Round> next = round.findRoundByLvl(lvl);
            List<Round> needToMerge = current.stream().flatMap(currentRound -> next.stream()
                            .filter(currentRound::equals))
                    .collect(Collectors.toList());
            needToMerge.forEach(res -> {
                var currIndex = current.indexOf(res);
                Round currRound = current.get(currIndex);
                currRound.addRounds(res.roundsOneToEtc);
                var nextIndex = next.indexOf(res);
                Round nextRound = next.get(nextIndex);
                nextRound.addRounds(currRound.roundsOneToEtc);
            });
        }
    }

    void mergeDefRounds(Round round) {
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
            if (r.roundsOneToEtc.size() != 0) {
                var tmp = r.levels();
                if (tmp > lvl) {
                    lvl = tmp;
                }
            } else {
                lvl = r.lvl;
            }
        }
        if (lvl == 0) {
            lvl = this.lvl;
        }
        return lvl;
    }

    List<Round> findRoundByLvl(int lvl) {
        List<Round> result = new ArrayList<>();
        if (this.lvl <= lvl) {
            for (Round round : this.roundsOneToEtc) {
                result.addAll(round.findRoundByLvl(lvl));
            }
        }
        result.addAll(this.roundsOneToEtc);
        return result;
    }

    void addRound(Round round) {
        this.roundsOneToEtc.add(round);
    }

    void addRounds(Set<Round> rounds) {
        this.roundsOneToEtc.addAll(rounds);
    }

    public static Round ofRound(int lvl, Class<?> type, Object value) {
        return new Round(lvl, type, value) {
            @Override
            void mergeRounds(Round round) {
                mergeDefRounds(round);
            }
        };
    }

    public static Round ofRound(int lvl, Class<?> type, Object value, Set<Round> roundsOneToEtc) {
        return new Round(lvl, type, value, roundsOneToEtc) {
            @Override
            void mergeRounds(Round round) {
                mergeDefRounds(round);
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
