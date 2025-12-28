package com.mcg.sofka.retotecnicobanco.api.rest.domain.exception;

/**
 * Signal emitted when a debit movement would drive the account into a negative balance.
 */
public final class MovementInsufficientBalanceException extends RuntimeException {

    private static final String MESSAGE = "Saldo no disponible";

    public MovementInsufficientBalanceException() {
        super(MESSAGE);
    }

    public MovementInsufficientBalanceException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
