package com.soma.event.assign_secure_alert_event.repository;

import com.soma.event.assign_secure_alert_event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface EventRepository
        extends JpaRepository<Event, String> {

    @Query("SELECT e FROM Event e WHERE " +
            "(:deviceId IS NULL OR e.deviceId = :deviceId) AND "+
            "(:severity is null OR e.severity = :sevierty) AND "+
            "(:eventType is null OR e.eventType = :eventType) AND "+
             "(:timestamp is null OR e.timestamp between :from AND :to)")
    public Page<Event> filterEvents(@Param("deviceId") String deviceId,
                                    @Param("severity") String severity,
                                    @Param("severity") String eventType,
                                    @Param("timeStamp")OffsetDateTime from,@Param("timeStamp")OffsetDateTime to,  Pageable pageable);

}
