package com.mcg.sofka.retotecnicobanco.api.rest.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Centralizes exception handling for the REST adapter layer.
 *
 * <p>This component lives in the infrastructure/adapters portion of the hexagon and translates
 * unchecked exceptions into HTTP responses. It ensures controllers and lambda handlers remain focused
 * on request processing (SRP) while this class manages error translation. The implementation provides a
 * single entry point for uncaught exceptions before propagating them to external clients, supporting
 * a consistent event-driven response pattern.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Remaps any unhandled exception to a 500 response.
     *
     * <p>In a mature system, you may add more specific handlers, but this method currently catches all
     * exceptions to prevent propagation to the client. It preserves the Single Responsibility Principle by
     * keeping error translation here while letting services and adapters throw meaningful exceptions upstream.
     *
     * @param ex original exception
     * @return HTTP 500 with the exception message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
