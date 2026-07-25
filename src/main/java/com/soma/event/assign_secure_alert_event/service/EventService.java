package com.soma.event.assign_secure_alert_event.service;

import com.soma.event.assign_secure_alert_event.dto.EventRequest;
import com.soma.event.assign_secure_alert_event.entity.Event;
import org.springframework.stereotype.Component;

@Component
public interface EventService {
    public Event createEvent(EventRequest eventRequest);
}