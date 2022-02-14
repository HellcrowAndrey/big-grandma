package com.github.mapper;

import com.github.mapper.cases.DependenciesGraphCase4;
import com.github.mapper.models.RootLvl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.mapper.cases.DependenciesGraphCase4.expect;

/**
 * Bidirectional 0 -> 1 lvl one to one
 */
public class DefaultSqlDependenciesMapperCase4Test {

    @Test
    void givenListMap_whenSingle_thenReturnFluxOnlyRoot() {
        RootLvl exp = expect();
        List<Map<String, Object>> tuples = DependenciesGraphCase4.tuple();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase4.graph());
        Mono<RootLvl> publisher = sqlDependenciesMapper.single(tuples);
        StepVerifier.create(publisher)
                .expectNext(exp)
                .verifyComplete();
    }

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot() {
        List<RootLvl> exp = DependenciesGraphCase4.expect_many();
        List<Map<String, Object>> tuples = DependenciesGraphCase4.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase4.graph());
        Flux<RootLvl> publisher = sqlDependenciesMapper.many(tuples);
        List<RootLvl> act = publisher.toStream().collect(Collectors.toList());
        Assertions.assertThat(act)
                .isNotEmpty()
                .containsAll(exp);
        isBidirectional(exp, act);
    }

    private void isBidirectional(List<RootLvl> input1, List<RootLvl> input2) {
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

}
