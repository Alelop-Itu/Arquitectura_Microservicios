package com.bank.app.infrastructure.controller.advice;

import com.bank.app.common.exception.AccountNotFoundException;
import com.bank.app.common.exception.CustomerNotFoundException;
import com.bank.app.common.exception.InsufficientBalanceException;
import com.bank.app.common.exception.MovementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleAccountNotFound(AccountNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "CUENTA_NO_ENCONTRADA", ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "CLIENTE_NO_ENCONTRADO", ex.getMessage());
    }

    @ExceptionHandler(MovementNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleMovementNotFound(MovementNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "MOVIMIENTO_NO_ENCONTRADO", ex.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleInsufficientBalance(InsufficientBalanceException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "SALDO_INSUFICIENTE", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleRuntimeException(RuntimeException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INTERNO", ex.getMessage());
    }

    private Mono<ResponseEntity<Map<String, Object>>> buildResponse(HttpStatus status, String codigo, String mensaje) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("codigo", codigo);
        body.put("mensaje", mensaje);
        return Mono.just(ResponseEntity.status(status).body(body));
    }
}