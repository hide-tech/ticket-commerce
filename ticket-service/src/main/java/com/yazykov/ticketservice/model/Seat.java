package com.yazykov.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("seats")
public class Seat {
    @Id
    @Column("id")
    private Long id;
    @Column("sector")
    private String sector;
    @Column("line")
    private String line;
    @Column("place")
    private String place;
    @Column("type")
    private SeatType type;
    @Column("state")
    private SeatState state;
    @Column("price")
    private BigDecimal price;
    @Transient
    private Event event;
}
