package com.soma.event.assign_secure_alert_event.service;

import com.soma.event.assign_secure_alert_event.dto.EventRequest;
import com.soma.event.assign_secure_alert_event.entity.Event;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
public interface EventService {
    public Event createEvent(EventRequest eventRequest);
    Map<String,Object> retrivedPageDetails(String deviceId, String sevierty, String eventType , OffsetDateTime from ,
                                           OffsetDateTime to , int page, int pageSize);

    Map<String, Object> getSummary(OffsetDateTime from, OffsetDateTime to);
}
