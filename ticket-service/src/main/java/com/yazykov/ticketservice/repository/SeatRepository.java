package com.yazykov.ticketservice.repository;

import com.yazykov.ticketservice.model.Seat;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface SeatRepository extends CrudRepository<Seat, Long> {

    @Query("select s from seats s where s.event_id = :eventId")
    Set<Seat> findSeatsByEventId(Long eventId);

    @Transactional
    @Modifying
    @Query("delete from seats s where s.event_id = :eventId")
    void deleteSeatsByEventId(Long eventId);
}
