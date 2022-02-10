package com.github.mapper.graph;

import java.util.ArrayList;
import java.util.List;

public class RoundsList {

    List<Round> rounds;

    public void addRound(Round round) {
        this.rounds.add(round);
    }

    public Round findRoundByValue(int lvl, Object value) {
        List<Round> r = new ArrayList<>();
        for (Round round : this.rounds) {
            r.addAll(round.findRoundByLvl(lvl));
        }
        return r.stream().filter(obj -> obj.equals(value))
                .findFirst()
                .orElse(null);
    }


}
