package com.soma.event.assign_secure_alert_event.serviceImpl;

import com.soma.event.assign_secure_alert_event.dto.EventRequest;
import com.soma.event.assign_secure_alert_event.entity.Event;
import com.soma.event.assign_secure_alert_event.repository.EventRepository;
import com.soma.event.assign_secure_alert_event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public Event createEvent(EventRequest eventRequest) {

        Event event = new Event();
        event.setId(UUID.randomUUID().toString());
        event.setDeviceId(eventRequest.getDeviceId());
        event.setEventType(eventRequest.getEventType());
        event.setSeverity(eventRequest.getSeverity());
        event.setTimestamp(eventRequest.getReportedTime());
        try {
            if (eventRequest.getMetadata() != null) {
                String metaData = objectMapper.writeValueAsString(eventRequest.getMetadata());
                event.setMetadata(metaData);
            }
        } catch (JacksonException e) {
            throw new RuntimeException(e.getMessage());
        }

        Event result = eventRepository.save(event);

        return result;
    }
}
