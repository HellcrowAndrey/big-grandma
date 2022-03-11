package com.github.mapper.graph;

import com.github.mapper.utils.CollectionsUtils;

import java.util.*;

public class RoundManyToMany implements GeneralRounds {

    GeneralRounds right;

    Map<GeneralRounds, Set<Object>> lefts = new HashMap<>();

    public RoundManyToMany(GeneralRounds right) {
        this.right = right;
    }

    public static GeneralRounds create(GeneralRounds right) {
        return new RoundManyToMany(right);
    }

    @Override
    public RelationType roundType() {
        return RelationType.manyToMany;
    }

    @Override
    public void putLeft(GeneralRounds left, Object value) {
        this.lefts.put(left, CollectionsUtils.singleSet(value));
    }

    @Override
    public void collectRounds(GeneralRounds rounds) {
        this.right.collectRounds(rounds.right());
        Map<GeneralRounds, Set<Object>> newLefts = rounds.lefts();
        for (GeneralRounds left : newLefts.keySet()) {
            if (this.lefts.containsKey(left)) {
                this.lefts.get(left).addAll(newLefts.get(left));
                findRound(this.lefts.keySet(), left)
                        .ifPresent(left::collectRounds);
            } else {
                this.lefts.put(left, newLefts.get(left));
            }
        }
    }

    @Override
    public void collectRoundsLeft(Map<GeneralRounds, Set<Object>> newLefts) {
        for (GeneralRounds left : newLefts.keySet()) {
            if (this.lefts.containsKey(left)) {
                this.lefts.get(left).addAll(newLefts.get(left));
                findRound(this.lefts.keySet(), left)
                        .ifPresent(left::collectRounds);
            }
        }
    }

    private Optional<GeneralRounds> findRound(Set<GeneralRounds> keys, GeneralRounds newRound) {
        return keys.stream().filter(key -> key.equals(newRound)).findFirst();
    }

    @Override
    public GeneralRounds right() {
        return this.right;
    }

    @Override
    public Map<GeneralRounds, Set<Object>> lefts() {
        return this.lefts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundManyToMany)) return false;
        RoundManyToMany that = (RoundManyToMany) o;
        return Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(right);
    }

}
