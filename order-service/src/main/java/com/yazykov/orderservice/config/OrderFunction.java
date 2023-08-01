package com.yazykov.orderservice.config;

import com.yazykov.orderservice.dto.OrderDispatchedDto;
import com.yazykov.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class OrderFunction {
    private static final Logger logger = LoggerFactory.getLogger(OrderFunction.class);

    @Bean
    public Consumer<Flux<OrderDispatchedDto>> dispatchOrder(OrderService orderService) {
        return flux -> {
            orderService.consumeDispatchedOrder(flux)
                    .doOnNext(order -> logger.info("Order with id {} has been dispatched", order.id()))
                    .subscribe();
        };
    }
}
