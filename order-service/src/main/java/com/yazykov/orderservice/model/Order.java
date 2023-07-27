package com.yazykov.orderservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Table("orders")
public record Order(
        @Id
        Long id,
        Long eventId,
        Long seatId,
        String eventName,
        String seatSector,
        String seatLine,
        String seatPlace,
        LocalDateTime eventDateTime,
        BigDecimal price,
        OrderStatus orderStatus,
        @CreatedDate
        Instant createdDate,
        @LastModifiedDate
        Instant lastModifiedDate,
        @Version
        int version
) {
    public static Order create(Long eventId, Long seatId, String eventName, String seatSector,
                                    String seatLine, String seatPlace, LocalDateTime eventDateTime,
                                    BigDecimal price, OrderStatus orderStatus) {
        return new Order(null, eventId, seatId, eventName, seatSector, seatLine, seatPlace,
                eventDateTime, price, orderStatus, null, null, 0);
    }
}
