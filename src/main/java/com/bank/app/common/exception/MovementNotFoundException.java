package com.bank.app.common.exception;

public class MovementNotFoundException extends RuntimeException {
    public MovementNotFoundException(Long id) {
        super("Movimiento no encontrado con ID: " + id);
    }
}