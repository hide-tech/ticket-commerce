package com.yazykov.orderservice.repository;

import com.yazykov.orderservice.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}
