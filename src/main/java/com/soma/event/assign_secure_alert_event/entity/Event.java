package com.soma.event.assign_secure_alert_event.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    private String id;

    @Column(nullable = false, name = "device_id", length = 64)
    private String deviceId;

    @Column(nullable = false, name = "event_type")
    private String eventType;

    @Column(nullable = false, name = "severity")
    private String severity;

    @Column(nullable = false, name = "time_stamp")
    private OffsetDateTime timeStamp;

    @Column(nullable = true, columnDefinition = "JSON", name = "meta_data")
    private String metadata;

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(OffsetDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}