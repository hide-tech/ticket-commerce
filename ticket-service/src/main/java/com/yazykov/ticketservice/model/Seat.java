package com.yazykov.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    private Long id;
    private String sector;
    private String line;
    private String place;
    private SeatType type;
    private SeatState state;
    private BigDecimal price;
    private Event event;
}
