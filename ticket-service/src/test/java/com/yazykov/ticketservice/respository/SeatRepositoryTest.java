package com.yazykov.ticketservice.respository;

import com.yazykov.ticketservice.model.*;
import com.yazykov.ticketservice.repository.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findSeatByEventId() {
        initEvents();
        Set<Seat> result = seatRepository.findSeatsByEventId(2L);
        Assertions.assertEquals(12, result.size());
    }

    @Test
    void deleteSeatsByEventId() {
        initEvents();
        seatRepository.deleteSeatsByEventId(2L);
        Assertions.assertEquals(0, seatRepository.findSeatsByEventId(2L).size());
    }

    private void initEvents() {
        Event event1 = new Event(null, "Red History", EventType.CONCERT, LocalDateTime.of(2020, 10, 10, 5, 50), null, 5, Instant.now(), null, null, Instant.now());
        Set<Seat> seats1 = initSeats();
        seats1.forEach(el -> el.setEvent(event1));
        event1.setSeats(seats1);
        Event event2 = new Event(null, "Roy Jefferson's show", EventType.SHOW, LocalDateTime.of(2020, 10, 11, 5, 50), null, 3, Instant.now(), null, null, Instant.now());
        Set<Seat> seats2 = initSeats();
        seats2.forEach(el -> el.setEvent(event2));
        event2.setSeats(seats2);
        Event event3 = new Event(null, "Football match", EventType.SPORT_EVENT, LocalDateTime.of(2020, 10, 12, 5, 50), null, 1, Instant.now(), null, null, Instant.now());
        Set<Seat> seats3 = initSeats();
        seats3.forEach(el -> el.setEvent(event3));
        event3.setSeats(seats3);
        jdbcAggregateTemplate.insert(event1);
        jdbcAggregateTemplate.insert(event2);
        jdbcAggregateTemplate.insert(event3);
    }

    private Set<Seat> initSeats() {
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat(null, "A", "1", "1", SeatType.TYPE_A, SeatState.FREE, new BigDecimal("15"), null));
        seats.add(new Seat(null, "B", "1", "1", SeatType.TYPE_A, SeatState.FREE, new BigDecimal("15"), null));
        seats.add(new Seat(null, "C", "1", "1", SeatType.TYPE_A, SeatState.FREE, new BigDecimal("15"), null));
        seats.add(new Seat(null, "A", "2", "1", SeatType.TYPE_B, SeatState.FREE, new BigDecimal("12"), null));
        seats.add(new Seat(null, "B", "2", "1", SeatType.TYPE_B, SeatState.FREE, new BigDecimal("12"), null));
        seats.add(new Seat(null, "C", "2", "1", SeatType.TYPE_B, SeatState.FREE, new BigDecimal("12"), null));
        seats.add(new Seat(null, "A", "3", "1", SeatType.TYPE_C, SeatState.FREE, new BigDecimal("10"), null));
        seats.add(new Seat(null, "B", "3", "1", SeatType.TYPE_C, SeatState.FREE, new BigDecimal("10"), null));
        seats.add(new Seat(null, "C", "3", "1", SeatType.TYPE_C, SeatState.FREE, new BigDecimal("10"), null));
        seats.add(new Seat(null, "VIP", "1", "1", SeatType.VIP, SeatState.FREE, new BigDecimal("25"), null));
        seats.add(new Seat(null, "VIP", "1", "2", SeatType.VIP, SeatState.FREE, new BigDecimal("25"), null));
        seats.add(new Seat(null, "VIP", "1", "3", SeatType.VIP, SeatState.FREE, new BigDecimal("25"), null));
        return seats;
    }
}
