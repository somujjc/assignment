package com.soma.event.assign_secure_alert_event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.Map;

public class EventRequest {

    @JsonProperty("device_id")
    @NotBlank(message = "Device is required")
    @Size(max = 64 , min = 6 , message = "Device id should be less than 6 and maximum 3")
    private String deviceId;

    @JsonProperty("event_type")
    @NotBlank(message = "Event Type is required")
    @Size(message = "Event Type should be less than 6 and maximum 3")
    private String eventType;

    @JsonProperty("severity")
    @NotBlank(message = "Severity is required")
    @Size(message = "Severity should be less than 6 and maximum 3")
    private String severity;

    @JsonProperty("timestamp")
    @NotNull(message = "timestamp is required")
    private OffsetDateTime reportedTime;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;


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

    public OffsetDateTime getReportedTime() {
        return reportedTime;
    }

    public void setReportedTime(OffsetDateTime reportedTime) {
        this.reportedTime = reportedTime;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}