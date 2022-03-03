package com.github.mapper.graph;

import com.github.mapper.utils.CollectionsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoundManyToMany implements GeneralRounds {

    Class<?> topType;

    Map<GeneralRounds, Map<GeneralRounds, Set<Object>>> rounds; // ?

    public RoundManyToMany() {
    }

    private RoundManyToMany(Class<?> topType) {
        this.topType = topType;
    }

    public static GeneralRounds create(Class<?> topType) {
        return new RoundManyToMany(topType);
    }

    public static GeneralRounds create(GeneralRounds top, GeneralRounds bottom) {
        RoundManyToMany roundManyToMany = new RoundManyToMany();
        roundManyToMany.putRounds(top, bottom);
        return roundManyToMany;
    }

    @Override
    public RelationType roundType() {
        return RelationType.manyToMany;
    }

    @Override
    public void putRounds(GeneralRounds top, GeneralRounds bottom) {
        Map<GeneralRounds, Set<Object>> manyToMany =
                this.rounds.getOrDefault(top, new HashMap<>());
        if (manyToMany.isEmpty()) {
            manyToMany.put(bottom, CollectionsUtils.singleSet(top.value()));
        } else {
            if (manyToMany.containsKey(bottom)) {
                manyToMany.get(bottom).add(top);
            } else {
                manyToMany.put(bottom, CollectionsUtils.singleSet(top.value()));
            }
        }
        this.rounds.put(top, manyToMany);
    }

    @Override
    public Map<GeneralRounds, Map<GeneralRounds, Set<Object>>> roundsManyToMany() {
        return this.rounds;
    }

    @Override
    public void addRound(GeneralRounds rounds) {
        Map<GeneralRounds, Map<GeneralRounds, Set<Object>>> roundsManyToMany =
                rounds.roundsManyToMany();
        Set<GeneralRounds> keys = roundsManyToMany.keySet();
        keys.forEach(key -> {
            if (!this.rounds.containsKey(key)) {
                this.rounds.putAll(roundsManyToMany);
            } else {
                Map<GeneralRounds, Set<Object>> valuesExist = this.rounds.get(key);
                Map<GeneralRounds, Set<Object>> valuesNew = roundsManyToMany.get(key);
                Set<GeneralRounds> tmp = valuesNew.keySet();
                tmp.forEach(k -> valuesExist.merge(k, valuesNew.get(k), (oldSet, newSet) -> {
                            oldSet.addAll(newSet);
                            return oldSet;
                        })
                );
            }
        });
    }

    @Override
    public Class<?> topType() {
        return this.topType;
    }

}
