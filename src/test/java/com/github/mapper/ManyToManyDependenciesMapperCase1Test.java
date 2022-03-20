package com.github.mapper;

import com.github.mapper.cases.ManyToManyDependenciesGraphCase1;
import com.github.mapper.models.many.to.many.case1.RootManyToMany;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class ManyToManyDependenciesMapperCase1Test {

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot() {
        List<RootManyToMany> expected = ManyToManyDependenciesGraphCase1.expect_many();
        List<Map<String, Object>> tuples = ManyToManyDependenciesGraphCase1.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(ManyToManyDependenciesGraphCase1.graph());
        Flux<RootManyToMany> publisher = sqlDependenciesMapper.many(tuples);
        publisher.subscribe(r -> System.out.println(r));

        StepVerifier.create(publisher)
                .expectNextSequence(expected)
                .verifyComplete();
    }

}
