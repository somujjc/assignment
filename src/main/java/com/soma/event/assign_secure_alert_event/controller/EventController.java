package com.soma.event.assign_secure_alert_event.controller;

import com.soma.event.assign_secure_alert_event.dto.EventRequest;
import com.soma.event.assign_secure_alert_event.entity.Event;
import com.soma.event.assign_secure_alert_event.serviceImpl.EventServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
