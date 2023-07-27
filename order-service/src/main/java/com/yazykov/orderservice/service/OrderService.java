package com.yazykov.orderservice.service;

import com.yazykov.orderservice.dto.OrderDto;
import com.yazykov.orderservice.model.Order;
import com.yazykov.orderservice.model.OrderStatus;
import com.yazykov.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<OrderDto> getAllOrders() {
        return orderRepository.findAll().map(order -> {
            return new OrderDto(order.id(), order.eventId(), order.seatId(), order.eventName(), order.seatSector(),
                    order.seatLine(), order.seatPlace(), order.eventDateTime(), order.price(), order.orderStatus(),
                    order.createdDate(), order.lastModifiedDate());
        });
    }

    public Mono<OrderDto> createNewOrder(OrderDto orderDto) {
        return Mono.just(buildNewOrder(orderDto)).flatMap(orderRepository::save)
                .map(order -> {
                    return new OrderDto(order.id(), order.eventId(), order.seatId(), order.eventName(), order.seatSector(),
                            order.seatLine(), order.seatPlace(), order.eventDateTime(), order.price(), order.orderStatus(),
                            order.createdDate(), order.lastModifiedDate());
                });
    }

    private Order buildNewOrder(OrderDto orderDto) {
        return Order.create(orderDto.eventId(), orderDto.seatId(), orderDto.eventName(), orderDto.seatSector(),
                orderDto.seatLine(), orderDto.seatPlace(), orderDto.eventDateTime(), orderDto.price(),
                OrderStatus.CREATED);
    }

}