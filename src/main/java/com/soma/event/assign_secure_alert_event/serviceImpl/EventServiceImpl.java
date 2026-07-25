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
import java.util.List;
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
        event.setTimeStamp(eventRequest.getReportedTime());
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


    @Override
    public Map<String, Object> getSummary(OffsetDateTime from, OffsetDateTime to) {

        List<Event> events = eventRepository.findEventsBetween(from, to);

        int totalEvents = events.size();


        Map<String, Integer> bySeverity = new HashMap<>();

        Map<String, Integer> byEventType = new HashMap<>();
        
        Map<String, Integer> deviceCount = new HashMap<>();

        for (Event event : events) {

            String severity = event.getSeverity();
            bySeverity.put(severity, bySeverity.getOrDefault(severity, 0) + 1);


            String eventType = event.getEventType();
            byEventType.put(eventType, byEventType.getOrDefault(eventType, 0) + 1);


            String deviceId = event.getDeviceId();
            deviceCount.put(deviceId, deviceCount.getOrDefault(deviceId, 0) + 1);
        }


        String mostActiveDevice = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : deviceCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostActiveDevice = entry.getKey();
            }
        }


        int highCount = bySeverity.getOrDefault("high", 0);
        double highSeverityRate = totalEvents > 0 ? (double) highCount / totalEvents : 0.0;

        Map<String, Object> response = new HashMap<>();
        response.put("total_events", totalEvents);
        response.put("by_severity", bySeverity);
        response.put("by_event_type", byEventType);
        response.put("most_active_device", mostActiveDevice);
        response.put("high_severity_rate", highSeverityRate);

        return response;
    }
}
