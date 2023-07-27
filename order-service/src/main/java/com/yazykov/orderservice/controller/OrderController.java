package com.yazykov.orderservice.controller;

import com.yazykov.orderservice.dto.OrderDto;
import com.yazykov.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Flux<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Mono<OrderDto> createNewOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.createNewOrder(orderDto);
    }
}
