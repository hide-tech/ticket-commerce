package com.yazykov.orderservice.service;

import com.yazykov.orderservice.client.SeatClient;
import com.yazykov.orderservice.dto.OrderDto;
import com.yazykov.orderservice.dto.SeatDto;
import com.yazykov.orderservice.model.Order;
import com.yazykov.orderservice.model.OrderStatus;
import com.yazykov.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SeatClient seatClient;

    public OrderService(OrderRepository orderRepository, SeatClient seatClient) {
        this.orderRepository = orderRepository;
        this.seatClient = seatClient;
    }

    public Flux<OrderDto> getAllOrders() {
        return orderRepository.findAll().map(order -> {
            return new OrderDto(order.id(), order.eventId(), order.seatId(), order.eventName(), order.seatSector(),
                    order.seatLine(), order.seatPlace(), order.eventDateTime(), order.price(), order.orderStatus(),
                    order.createdDate(), order.lastModifiedDate());
        });
    }

    public Mono<OrderDto> createNewOrder(OrderDto orderDto) {
        return seatClient.getSeatByIdAndEventId(orderDto.seatId(), orderDto.eventId())
                .map(seat -> buildAcceptedOrder(seat, orderDto))
                .defaultIfEmpty(buildRejectedOrder(orderDto))
                .flatMap(orderRepository::save)
                .map(order -> {
                    return new OrderDto(order.id(), order.eventId(), order.seatId(), order.eventName(), order.seatSector(),
                            order.seatLine(), order.seatPlace(), order.eventDateTime(), order.price(), order.orderStatus(),
                            order.createdDate(), order.lastModifiedDate());
                });
    }

    public static Order buildAcceptedOrder(SeatDto seat, OrderDto orderDto) {
        return Order.create(orderDto.eventId(), seat.id(), orderDto.eventName(), seat.sector(),
                seat.line(), seat.place(), orderDto.eventDateTime(), seat.price(),
                OrderStatus.CREATED);
    }

    public static Order buildRejectedOrder(OrderDto orderDto) {
        return Order.create(orderDto.eventId(), orderDto.seatId(), orderDto.eventName(), orderDto.seatSector(),
                orderDto.seatLine(), orderDto.seatPlace(), orderDto.eventDateTime(), orderDto.price(),
                OrderStatus.FAILED);
    }

}
