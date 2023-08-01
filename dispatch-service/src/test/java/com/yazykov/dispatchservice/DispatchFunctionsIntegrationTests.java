package com.yazykov.dispatchservice;

import com.yazykov.dispatchservice.dto.OrderAcceptedDto;
import com.yazykov.dispatchservice.dto.OrderDispatchedDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.function.Function;

@FunctionalSpringBootTest
public class DispatchFunctionsIntegrationTests {

    @Autowired
    private FunctionCatalog functionCatalog;

    @Test
    void prepareAndDispatchOrder() {
        Function<OrderAcceptedDto, Flux<OrderDispatchedDto>> prepareAndDispatch = functionCatalog
                .lookup(Function.class, "prepare|dispatch");
        OrderAcceptedDto dto = new OrderAcceptedDto(17L, 4L, 24L);

        StepVerifier.create(prepareAndDispatch.apply(dto))
                .expectNextMatches(dispatchedOrder ->
                        Objects.equals(dispatchedOrder, new OrderDispatchedDto(17L, 4L, 24L)))
                .verifyComplete();
    }
}
