package com.github.mapper.graph;

import com.github.mapper.SqlDependenciesMapper;
import com.github.mapper.graph.cases.DependenciesGraphParameterized;
import com.github.mapper.graph.cases.DependenciesGraphProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

class DependenciesGraphTest {

    @ParameterizedTest(name = "{index} - {0} - cases")
    @ArgumentsSource(DependenciesGraphProvider.class)
    void givenListMap_whenSingle_thenReturnFluxOnlyRoot(DependenciesGraphParameterized dependencies) {
        Object exp = dependencies.expect_one();
        List<Map<String, Object>> tuples = dependencies.tuple();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(dependencies.graph());
        Mono<Object> publisher = sqlDependenciesMapper.single(tuples);
        StepVerifier.create(publisher)
                .expectNext(exp)
                .verifyComplete();
    }

    @ParameterizedTest(name = "{index} - {0} - cases")
    @ArgumentsSource(DependenciesGraphProvider.class)
    void givenListMap_whenMany_thenReturnFluxRoot(DependenciesGraphParameterized dependencies) {
        List<Object> expected = dependencies.expect_many();
        List<Map<String, Object>> tuples = dependencies.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(dependencies.graph());
        Flux<Object> publisher = sqlDependenciesMapper.many(tuples);
        publisher.collectList().subscribe(s -> System.out.println(s));
        StepVerifier.create(publisher)
                .expectNextSequence(expected)
                .verifyComplete();
    }

}