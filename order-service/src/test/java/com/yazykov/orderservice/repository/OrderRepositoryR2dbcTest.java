package com.yazykov.orderservice.repository;

import com.yazykov.orderservice.config.DataConfig;
import com.yazykov.orderservice.dto.OrderDto;
import com.yazykov.orderservice.dto.SeatDto;
import com.yazykov.orderservice.model.OrderStatus;
import com.yazykov.orderservice.model.SeatState;
import com.yazykov.orderservice.model.SeatType;
import com.yazykov.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataR2dbcTest
@Import(DataConfig.class)
@Testcontainers
class OrderRepositoryR2dbcTest {

    @Container
    static PostgreSQLContainer<?> postgresql =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4"));

    @Autowired
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", OrderRepositoryR2dbcTest::r2dbcUrl);
        registry.add("spring.r2dbc.username", postgresql::getUsername);
        registry.add("spring.r2dbc.password", postgresql::getPassword);
        registry.add("spring.flyway.url", postgresql::getJdbcUrl);
    }

    private static String r2dbcUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                postgresql.getContainerIpAddress(),
                postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgresql.getDatabaseName());
    }

    @Test
    void createFaultOrder() {
        var orderDto = new OrderDto(null, 14L, 17L, "Concert Nero", "VIP", "2", "5", LocalDateTime.now(), new BigDecimal("15.50"), null, null, null);
        var faultOrder = OrderService.buildRejectedOrder(orderDto);
        StepVerifier
                .create(orderRepository.save(faultOrder))
                .expectNextMatches(order -> order.orderStatus().equals(OrderStatus.FAILED))
                .verifyComplete();
    }

    @Test
    void createAcceptedOrder() {
        var orderDto = new OrderDto(null, 14L, 17L, "Concert Nero", "VIP", "2", "5", LocalDateTime.now(), new BigDecimal("15.50"), null, null, null);
        var seatDto = new SeatDto(17L, "VIP", "2", "5", SeatType.VIP, SeatState.FREE, new BigDecimal("15.50"), 14L);
        var faultOrder = OrderService.buildAcceptedOrder(seatDto, orderDto);
        StepVerifier
                .create(orderRepository.save(faultOrder))
                .expectNextMatches(order -> order.orderStatus().equals(OrderStatus.CREATED))
                .verifyComplete();
    }
}