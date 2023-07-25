package com.yazykov.ticketservice.repository;

import com.yazykov.ticketservice.model.Event;
import com.yazykov.ticketservice.model.EventType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("select from Event where dateTime = :dateTime")
    List<Event> findAllByDateTime(LocalDateTime dateTime);

    @Query("select from Event where type = :type")
    List<Event> findAllByType(EventType type);

    @Query("select from Event where id = :id")
    Optional<Event> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Event where id = :id")
    void deleteById(Long id);
}
