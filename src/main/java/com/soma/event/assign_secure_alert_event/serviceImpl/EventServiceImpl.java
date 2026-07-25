package com.soma.event.assign_secure_alert_event.serviceImpl;

import com.soma.event.assign_secure_alert_event.dto.EventRequest;
import com.soma.event.assign_secure_alert_event.entity.Event;
import com.soma.event.assign_secure_alert_event.repository.EventRepository;
import com.soma.event.assign_secure_alert_event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
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

    @Override
    public Map<String,Object> retrivedPageDetails(String deviceId, String sevierty, String eventType,OffsetDateTime from, OffsetDateTime to, int page, int pageSize) {

        Map<String,Object> response=new HashMap<>();
        if(page<1)
            page=1;
        if(pageSize>100 || pageSize<1)
            pageSize=20;

        Pageable pagenation= PageRequest.of(page-1,pageSize, Sort.by("timestamp").descending());
        Page<Event> resultPage =eventRepository.filterEvents(deviceId,sevierty,eventType,from,to,pagenation);
        response.put("Total Events", resultPage.getTotalElements());
        response.put("Page",page);
        response.put("page size",pageSize);
        response.put("events",resultPage.getContent());

        return response;
    }
}
