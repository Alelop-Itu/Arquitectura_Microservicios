package com.bank.app.common.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Saldo no disponible");
    }
}
