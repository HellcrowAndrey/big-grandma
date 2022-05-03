package com.github.mapper.sql;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveSelect {

    <T> Mono<T> one();

    <T>Flux<T> any();

}
