package ro.elicitatie.seap.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, Object>> handleClientError(HttpClientErrorException ex) {
        log.error("Client error from e-licitatie.ro API: {}", ex.getMessage());
        return buildErrorResponse(
                "Client error from e-licitatie.ro API",
                ex.getStatusCode().value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleServerError(HttpServerErrorException ex) {
        log.error("Server error from e-licitatie.ro API: {}", ex.getMessage());
        return buildErrorResponse(
                "Server error from e-licitatie.ro API",
                HttpStatus.BAD_GATEWAY.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAccessError(ResourceAccessException ex) {
        log.error("Cannot access e-licitatie.ro API: {}", ex.getMessage());
        return buildErrorResponse(
                "Cannot connect to e-licitatie.ro API",
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "The external API is not reachable"
        );
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, Object>> handleRestClientException(RestClientException ex) {
        log.error("Error communicating with e-licitatie.ro API: {}", ex.getMessage());
        return buildErrorResponse(
                "Error communicating with e-licitatie.ro API",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        return buildErrorResponse(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
        );
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, int status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", error);
        body.put("status", status);
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
