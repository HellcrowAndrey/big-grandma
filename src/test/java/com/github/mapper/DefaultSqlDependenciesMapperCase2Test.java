package com.github.mapper;

import com.github.mapper.cases.DependenciesGraphCase2;
import com.github.mapper.models.RootLvl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static com.github.mapper.cases.DependenciesGraphCase2.expect;

public class DefaultSqlDependenciesMapperCase2Test {

    @Test
    void givenListMap_whenSingle_thenReturnFluxOnlyRoot() {
        RootLvl exp = expect();
        List<Map<String, Object>> tuples = DependenciesGraphCase2.tuple();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase2.graph());
        Mono<RootLvl> publisher = sqlDependenciesMapper.single(tuples);
        StepVerifier.create(publisher)
                .expectNext(exp)
                .verifyComplete();
    }

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot() {
        List<RootLvl> expected = DependenciesGraphCase2.expect_many();
        List<Map<String, Object>> tuples = DependenciesGraphCase2.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(DependenciesGraphCase2.graph());
        Flux<Object> publisher = sqlDependenciesMapper.many(tuples);
        StepVerifier.create(publisher)
                .expectNextSequence(expected)
                .verifyComplete();
    }

}
