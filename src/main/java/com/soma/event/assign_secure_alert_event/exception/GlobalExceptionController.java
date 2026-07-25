package com.soma.event.assign_secure_alert_event.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex) {

        Map<String, Object> response = new HashMap<>();

        if (ex.getCause() instanceof InvalidFormatException) {

            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            String fieldName = invalidFormatException.getPath().get(0).getPropertyName();

            if ("timestamp".equals(fieldName)) {
                response.put("error", "Invalid timestamp format. Must be a valid ISO 8601 datetime (e.g., 2024-11-15T03:22:10Z).");
            } else {
                response.put("error", "Invalid value for field: " + fieldName);
            }
        } else {
            response.put("error", "Malformed JSON request body.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> commonExceptions(Exception ex){

        Map<String,String> errroResponse= new HashMap<>();
        errroResponse.put("error", "Something went wrong, please try again later...");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errroResponse);

    }
}