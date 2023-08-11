package com.yazykov.ticketservice.model;

import com.yazykov.ticketservice.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("events")
public class Event {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("type")
    private EventType type;
    @Column("date_time")
    private LocalDateTime dateTime;
    @MappedCollection(idColumn = "event_id")
    private Set<Seat> seats;
    @Version
    @Column("version")
    private int version;
    @CreatedDate
    @Column("created_date")
    private Instant createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    @Column("last_modified_date")
    private Instant lastModifiedDate;

    public Event(EventDto eventDto) {
        this.name = eventDto.name();
        this.type = eventDto.type();
        this.dateTime = eventDto.dateTime();
        version = 0;
    }
}
