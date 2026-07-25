package com.soma.event.assign_secure_alert_event.controller;

import com.soma.event.assign_secure_alert_event.dto.EventRequest;
import com.soma.event.assign_secure_alert_event.entity.Event;
import com.soma.event.assign_secure_alert_event.serviceImpl.EventServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    EventServiceImpl eventService;

    @PostMapping("/event")
    public ResponseEntity<?> saveResponse(@Valid @RequestBody EventRequest eventRequest){

        Event event = eventService.createEvent(eventRequest);
        if(event.getId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(event.getId());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Save request failed");
    }

    @GetMapping("/events")
    public ResponseEntity<?> filterEvents(@RequestParam(name="device_id", required = false )String deviceId,
                                         @RequestParam(name="severity" , required = false) String severity,
                                          @RequestParam(name="event_type" , required = false) String eventType,
                                          @RequestParam(required = false) OffsetDateTime from,
                                          @RequestParam(required = false) OffsetDateTime to,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(name = "page_size", defaultValue = "20") int pageSize) {


        Map<String, Object> response = eventService.retrivedPageDetails(deviceId, severity, eventType, from, to, page, pageSize);
        return ResponseEntity.ok(response);

}
}
