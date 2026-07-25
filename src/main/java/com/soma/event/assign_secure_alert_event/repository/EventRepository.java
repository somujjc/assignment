package com.soma.event.assign_secure_alert_event.repository;

import com.soma.event.assign_secure_alert_event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface EventRepository
        extends JpaRepository<Event, String> {

    @Query("SELECT e FROM Event e WHERE " +
            "(:deviceId IS NULL OR e.deviceId = :deviceId) AND "+
            "(:severity IS NULL OR e.severity = :severity) AND " +
            "(:eventType is null OR e.eventType = :eventType) AND "+
            "(:from IS NULL OR e.timeStamp >= :from) AND " +
            "(:to IS NULL OR e.timeStamp <= :to)")
    public Page<Event> filterEvents(@Param("deviceId") String deviceId,
                                    @Param("severity") String severity,
                                    @Param("eventType") String eventType,
                                    @Param("from")OffsetDateTime from,@Param("to")OffsetDateTime to,  Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.timeStamp BETWEEN :from AND :to")
    List<Event> findEventsBetween(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);

}
