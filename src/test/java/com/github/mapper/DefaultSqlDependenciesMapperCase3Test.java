package com.github.mapper;

import com.github.mapper.cases.DependenciesGraphCase3;
import com.github.mapper.models.RootLvl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static com.github.mapper.cases.DependenciesGraphCase3.expect;

public class DefaultSqlDependenciesMapperCase3Test {

    @Test
    void givenListMap_whenSingle_thenReturnFluxOnlyRoot() {
        RootLvl exp = expect();
        List<Map<String, Object>> tuples = DependenciesGraphCase3.tuple();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase3.graph());
        Mono<RootLvl> publisher = sqlDependenciesMapper.single(tuples);
        StepVerifier.create(publisher)
                .expectNext(exp)
                .verifyComplete();
    }

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot() {
        List<RootLvl> expected = DependenciesGraphCase3.expect_many();
        List<Map<String, Object>> tuples = DependenciesGraphCase3.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase3.graph());
        Flux<RootLvl> publisher = sqlDependenciesMapper.many(tuples);
        StepVerifier.create(publisher)
                .expectNextSequence(expected)
                .verifyComplete();
    }

}
