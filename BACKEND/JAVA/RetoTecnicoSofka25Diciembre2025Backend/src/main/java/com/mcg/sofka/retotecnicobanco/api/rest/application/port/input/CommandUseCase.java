package com.mcg.sofka.retotecnicobanco.api.rest.application.port.input;

/**
 * Abstraction for command handlers located in the application core.
 *
 * <p>Commands represent intentions to mutate state. By defining this interface, the outer adapters
 * depend on abstractions owned by the application core (DIP). Implementations can be freely switched
 * without changing controllers/lembda handlers.
 *
 * @param <C> command DTO type
 */
public interface CommandUseCase<C, R> {

    /**
     * Executes the command flow (validate, mutate, publish events) inside the application core and
     * returns the result for the adapter.
     *
     * @param command command DTO carrying all necessary data
     * @return command result (e.g., persisted aggregate)
     */
    R execute(C command);
}
