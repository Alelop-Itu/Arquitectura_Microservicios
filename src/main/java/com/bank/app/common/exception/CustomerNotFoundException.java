package com.bank.app.common.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Cliente no existe con ID: " + id);
    }
}
