package com.bank.app.common.constant;

public final class MovementType {

    private MovementType() {
    }

    public static final String DEBITO = "Débito";
    public static final String RETIRO = "Retiro";
    public static final String CREDITO = "Crédito";

    /**
     * Determina si un tipo de movimiento representa un débito (resta el saldo).
     * Cualquier tipo que no sea explícitamente débito/retiro se trata como crédito.
     */
    public static boolean esDebito(String tipo) {
        return DEBITO.equalsIgnoreCase(tipo) || RETIRO.equalsIgnoreCase(tipo);
    }
}