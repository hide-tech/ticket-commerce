package com.yazykov.orderservice.service;

import com.yazykov.orderservice.client.SeatClient;
import com.yazykov.orderservice.dto.OrderAcceptedDto;
import com.yazykov.orderservice.dto.OrderDispatchedDto;
import com.yazykov.orderservice.dto.OrderDto;
import com.yazykov.orderservice.dto.SeatDto;
import com.yazykov.orderservice.model.Order;
import com.yazykov.orderservice.model.OrderStatus;
import com.yazykov.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.cloud.stream.function.StreamBridge;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SeatClient seatClient;
    private final StreamBridge streamBridge;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, SeatClient seatClient, StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.seatClient = seatClient;
        this.streamBridge = streamBridge;
    }

    public Flux<OrderDto> getAllOrders(String userId) {
        return orderRepository.findAllByCreatedBy(userId).map(order -> {
            return new OrderDto(order.id(), order.eventId(), order.seatId(), order.eventName(), order.seatSector(),
                    order.seatLine(), order.seatPlace(), order.eventDateTime(), order.price(), order.orderStatus(),
                    order.createdDate(), order.lastModifiedDate());
        });
    }

    @Transactional
    public Mono<OrderDto> createNewOrder(OrderDto orderDto) {
        return seatClient.getSeatByIdAndEventId(orderDto.seatId(), orderDto.eventId())
                .map(seat -> buildAcceptedOrder(seat, orderDto))
                .defaultIfEmpty(buildRejectedOrder(orderDto))
                .flatMap(orderRepository::save)
                .doOnNext(this::publishOrderAccepted)
                .doOnNext(this::publishOrderReserved)
                .map(order -> {
                    return new OrderDto(order.id(), order.eventId(), order.seatId(), order.eventName(), order.seatSector(),
                            order.seatLine(), order.seatPlace(), order.eventDateTime(), order.price(), order.orderStatus(),
                            order.createdDate(), order.lastModifiedDate());
                });
    }

    private void publishOrderReserved(Order order) {
        if (!order.orderStatus().equals(OrderStatus.CREATED)) {
            return;
        }
        var orderAccepted = new OrderAcceptedDto(order.id(), order.eventId(), order.seatId());
        logger.info("Sending seat reserved with id {}", order.seatId());
        var result = streamBridge.send("reserveOrder-out-0", orderAccepted);
        logger.info("Result of sending reserved order {}:{}", order.id(), result);
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

    public Flux<Order> consumeDispatchedOrder(Flux<OrderDispatchedDto> flux) {
        return flux.flatMap(el -> orderRepository.findById(el.orderId()))
                .map(this::buildDispatchedOrder)
                .flatMap(orderRepository::save);
    }

    private Order buildDispatchedOrder(Order existOrder) {
        return new Order(
                existOrder.id(), existOrder.eventId(), existOrder.seatId(), existOrder.eventName(),
                existOrder.seatSector(), existOrder.seatLine(), existOrder.seatPlace(), existOrder.eventDateTime(),
                existOrder.price(), OrderStatus.DELIVERED, existOrder.createdDate(), existOrder.lastModifiedDate(),
                existOrder.version(), existOrder.createdBy(), null
        );
    }

    private void publishOrderAccepted(Order order) {
        if (!order.orderStatus().equals(OrderStatus.CREATED)) {
            return;
        }
        var orderAccepted = new OrderAcceptedDto(order.id(), order.eventId(), order.seatId());
        logger.info("Sending order accepting with id {}", order.id());
        var result = streamBridge.send("acceptOrder-out-0", orderAccepted);
        logger.info("Result of sending accepted order {}:{}", order.id(), result);
    }
}
