package com.github.mapper;

import com.github.mapper.cases.DependenciesGraphCase1;
import com.github.mapper.models.RootLvl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static com.github.mapper.cases.DependenciesGraphCase1.expect;

class DefaultSqlDependenciesMapperCase1Test {

    @Test
    void givenListMap_whenSingle_thenReturnFluxOnlyRoot() {
        RootLvl exp = expect();
        List<Map<String, Object>> tuples = DependenciesGraphCase1.tuple();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase1.graph());
        Mono<RootLvl> publisher = sqlDependenciesMapper.single(tuples);
        StepVerifier.create(publisher)
                .expectNext(exp)
                .verifyComplete();
    }

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot() {
        List<RootLvl> expected = DependenciesGraphCase1.expect_many();
        List<Map<String, Object>> tuples = DependenciesGraphCase1.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase1.graph());
        Flux<Object> publisher = sqlDependenciesMapper.many(tuples);
        StepVerifier.create(publisher)
                .expectNextSequence(expected)
                .verifyComplete();
    }

}
