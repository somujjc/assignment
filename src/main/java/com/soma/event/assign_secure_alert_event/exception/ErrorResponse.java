package com.soma.event.assign_secure_alert_event.exception;

import java.util.Map;

public class ErrorResponse {

    public ErrorResponse(Map<String, Object> errors) {

        this.errors = errors;
    }

    private Map<String, Object> errors;

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }
}
