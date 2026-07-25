package com.soma.event.assign_secure_alert_event.repository;

import com.soma.event.assign_secure_alert_event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository
        extends JpaRepository<Event, String> {


}
