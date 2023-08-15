package com.yazykov.orderservice.controller;

import com.yazykov.orderservice.dto.OrderDto;
import com.yazykov.orderservice.model.OrderStatus;
import com.yazykov.orderservice.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;

@WebFluxTest(OrderController.class)
class OrderControllerWebFluxTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private OrderService orderService;

    @Test
    void createNewFaultOrder() {
        var orderDto = new OrderDto(null, 14L, 17L, "Concert Nero", "VIP", "2", "5", LocalDateTime.now(), new BigDecimal("15.50"), null, null, null);
        var expectedOrder = OrderService.buildRejectedOrder(orderDto);
        var expOrder = new OrderDto(expectedOrder.id(), expectedOrder.eventId(), expectedOrder.seatId(), expectedOrder.eventName(), expectedOrder.seatSector(), expectedOrder.seatLine(), expectedOrder.seatPlace(), expectedOrder.eventDateTime(), expectedOrder.price(), expectedOrder.orderStatus(), expectedOrder.createdDate(), expectedOrder.lastModifiedDate());
        given(orderService.createNewOrder(orderDto)).willReturn(Mono.just(expOrder));

        webClient.post()
                .uri("/orders")
                .bodyValue(orderDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(OrderDto.class).value(order -> {
                    Assertions.assertNotNull(order);
                    Assertions.assertEquals(OrderStatus.FAILED, order.orderStatus());
                });
    }
}