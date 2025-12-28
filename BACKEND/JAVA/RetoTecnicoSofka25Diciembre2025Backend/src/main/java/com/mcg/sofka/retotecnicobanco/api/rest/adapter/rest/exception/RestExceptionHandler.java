package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.exception;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.exception.MovementInsufficientBalanceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Maps domain exceptions into HTTP responses while keeping controller code focused on happy paths.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MovementInsufficientBalanceException.class)
    public ResponseEntity<ProblemDetail> handleInsufficientBalance(MovementInsufficientBalanceException exception,
                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Saldo no disponible");
        problemDetail.setProperty("path", request.getRequestURI());
        problemDetail.setProperty("errorCode", "movement.insufficient_balance");
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
