package com.yazykov.ticketservice.respository;

import com.yazykov.ticketservice.config.DataConfig;
import com.yazykov.ticketservice.model.*;
import com.yazykov.ticketservice.repository.EventRepository;
import com.yazykov.ticketservice.repository.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findAllByDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 10, 10, 5, 50);
        initEvents();
        List<Event> result = eventRepository.findAllByDateTime(dateTime);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Red History", result.get(0).getName());
    }

    @Test
    void findAll() {
        initEvents();
        List<Event> result = eventRepository.findAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void findById() {
        initEvents();
        Optional<Event> byId = eventRepository.findById(3L);
        Assertions.assertEquals("Football match", byId.get().getName());
    }

    @Test
    void deleteById() {
        initEvents();
        seatRepository.deleteSeatsByEventId(2L);
        eventRepository.deleteById(2L);
        Assertions.assertTrue(eventRepository.findById(2L).isEmpty());
    }

    private void initEvents() {
        Event event1 = new Event(null, "Red History", EventType.CONCERT, LocalDateTime.of(2020, 10, 10, 5, 50), null, 5, Instant.now(), null);
        Set<Seat> seats1 = initSeats();
        seats1.forEach(el -> el.setEvent(event1));
        event1.setSeats(seats1);
        Event event2 = new Event(null, "Roy Jefferson's show", EventType.SHOW, LocalDateTime.of(2020, 10, 11, 5, 50), null, 3, Instant.now(), null);
        Set<Seat> seats2 = initSeats();
        seats2.forEach(el -> el.setEvent(event2));
        event2.setSeats(seats2);
        Event event3 = new Event(null, "Football match", EventType.SPORT_EVENT, LocalDateTime.of(2020, 10, 12, 5, 50), null, 1, Instant.now(), null);
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
