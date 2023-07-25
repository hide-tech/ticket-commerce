package com.yazykov.ticketservice.repository;

import com.yazykov.ticketservice.model.Seat;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SeatRepository extends CrudRepository<Seat, Long> {

    @Query("select from Seat where Event.id = :eventId")
    Set<Seat> findSeatsByEventId(Long eventId);
}
