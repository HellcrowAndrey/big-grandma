package com.github.mapper;

import com.github.mapper.cases.DependenciesGraphCase7;
import com.github.mapper.models.RootLvl;
import com.github.mapper.models.Round2Lvl1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.mapper.cases.DependenciesGraphCase7.expect;

/**
 * Bidirectional 0 -> 1 lvl one to one.
 * No directional 0 -> 1 lvl one to Many coll type List.
 * All fields
 */
public class DefaultSqlDependenciesMapperCase7Test {

    @Test
    void givenListMap_whenSingle_thenReturnFluxOnlyRoot() {
        RootLvl exp = expect();
        List<Map<String, Object>> tuples = DependenciesGraphCase7.tuple();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase7.graph());
        Mono<RootLvl> publisher = sqlDependenciesMapper.single(tuples);
        StepVerifier.create(publisher)
                .expectNextMatches(act -> exp.equals(act) &&
                        exp.getRound2Lvl1s().containsAll(act.getRound2Lvl1s()) &&
                        act.getRound2Lvl1s().containsAll(exp.getRound2Lvl1s())
                ).verifyComplete();
    }

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot() {
        List<RootLvl> exp = DependenciesGraphCase7.expect_many();
        List<Map<String, Object>> tuples = DependenciesGraphCase7.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase7.graph());
        Flux<RootLvl> publisher = sqlDependenciesMapper.many(tuples);
        List<RootLvl> act = publisher.toStream().collect(Collectors.toList());
        Assertions.assertThat(act)
                .isNotEmpty()
                .containsAll(exp);
        isBidirectionalOneToOne(exp, act);
        collectionEqualsMany(exp, act);
    }

    private void isBidirectionalOneToOne(List<RootLvl> input1, List<RootLvl> input2) {
        List<RootLvl> act1 = input1.stream()
                .map(root -> root.getRound1Lvl1().getRootLvl())
                .collect(Collectors.toList());
        List<RootLvl> act2 = input2.stream()
                .map(root -> root.getRound1Lvl1().getRootLvl())
                .collect(Collectors.toList());
        Assertions.assertThat(act1)
                .isNotEmpty()
                .containsAll(act2);
    }

    private void collectionEqualsMany(List<RootLvl> input1, List<RootLvl> input2) {
        List<RootLvl> exp = input1.stream()
                .flatMap(elem -> elem.getRound2Lvl1s().stream())
                .map(Round2Lvl1::getRootLvl)
                .collect(Collectors.toList());
        List<RootLvl> act = input2.stream()
                .flatMap(elem -> elem.getRound2Lvl1s().stream())
                .map(Round2Lvl1::getRootLvl)
                .collect(Collectors.toList());
        Assertions.assertThat(exp)
                .containsAll(act);
    }

}
