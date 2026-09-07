package com.bank.app.common.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String number) {
        super("Cuenta no encontrada con número: " + number);
    }
}
