package com.github.mapper.graph;

import java.util.Map;
import java.util.Set;

public interface ManyToManyRounds {

    void addRound(GeneralRounds top, GeneralRounds bottom);

    Map<GeneralRounds, Map<GeneralRounds, Set<Object>>> roundsManyToMany();

}
