package com.yazykov.dispatchservice.config;

import com.yazykov.dispatchservice.dto.OrderAcceptedDto;
import com.yazykov.dispatchservice.dto.OrderDispatchedDto;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchFunctions {

    private static final Logger logger = LoggerFactory.getLogger(DispatchFunctions.class);

    @Bean
    public Function<OrderAcceptedDto, OrderAcceptedDto> prepare() {
        return orderAcceptedDto -> {
            logger.info("Order with id {} ready to dispatch", orderAcceptedDto.orderId());
            return orderAcceptedDto;
        };
    }

    @Bean
    public Function<Flux<OrderAcceptedDto>, Flux<OrderDispatchedDto>> dispatch() {
        return orderAcceptedDto -> {
            return orderAcceptedDto.map(order -> {
                logger.info("The order with id {} dispatched", order.orderId());
                return new OrderDispatchedDto(order.orderId(), order.eventId(), order.seatId());
            });
        };
    }
}
