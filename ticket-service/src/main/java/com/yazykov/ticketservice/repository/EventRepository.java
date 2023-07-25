package com.yazykov.ticketservice.repository;

import com.yazykov.ticketservice.model.Event;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findAllByDateTime(LocalDateTime dateTime);

    List<Event> findAll();

    Optional<Event> findById(Long id);

    @Transactional
    @Modifying
    @Query("delete e from Event e where id = :id")
    void deleteById(Long id);
}
