package com.github.mapper.graph;

import java.util.Map;
import java.util.Set;

public interface ManyToManyRounds {

    void putRounds(GeneralRounds top, GeneralRounds bottom);

    Map<GeneralRounds, Map<GeneralRounds, Set<Object>>> roundsManyToMany();

    Class<?> topType();

}
