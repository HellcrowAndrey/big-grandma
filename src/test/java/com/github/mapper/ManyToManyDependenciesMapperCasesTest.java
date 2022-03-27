package com.github.mapper;

import com.github.mapper.cases.ManyToManyDependenciesGraphCase1;
import com.github.mapper.cases.ManyToManyDependenciesGraphCase2;
import com.github.mapper.models.many.to.many.case1.RootManyToMany;
import com.github.mapper.models.many.to.many.case2.RootMTMCase2;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public class ManyToManyDependenciesMapperCasesTest {

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot_Case_1() {
        List<RootManyToMany> expected = ManyToManyDependenciesGraphCase1.expect_many();
        List<Map<String, Object>> tuples = ManyToManyDependenciesGraphCase1.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(ManyToManyDependenciesGraphCase1.graph());
        Flux<RootManyToMany> publisher = sqlDependenciesMapper.many(tuples);
        publisher.subscribe(r -> System.out.println(r));

//        StepVerifier.create(publisher)
//                .expectNextSequence(expected)
//                .verifyComplete();
    }

    @Test
    void givenListMap_whenMany_thenReturnFluxRoot_Case_2() {
        List<RootMTMCase2> expected = ManyToManyDependenciesGraphCase2.expect_many();
        List<Map<String, Object>> tuples = ManyToManyDependenciesGraphCase2.tuples();
        SqlDependenciesMapper sqlDependenciesMapper =
                SqlDependenciesMapper.defaultMap(ManyToManyDependenciesGraphCase2.graph());
        Flux<RootMTMCase2> publisher = sqlDependenciesMapper.many(tuples);
        publisher.subscribe(r -> System.out.println(r));

//        StepVerifier.create(publisher)
//                .expectNextSequence(expected)
//                .verifyComplete();
    }

}
