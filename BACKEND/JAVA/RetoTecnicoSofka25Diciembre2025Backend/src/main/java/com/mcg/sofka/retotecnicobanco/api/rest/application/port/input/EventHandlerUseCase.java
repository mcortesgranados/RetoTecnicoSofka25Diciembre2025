package com.mcg.sofka.retotecnicobanco.api.rest.application.port.input;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;

/**
 * Port for handling domain events emitted by the core in the context of an event-driven microservice.
 * <p>It bridges domain events to adapters like Kafka/SNS through the application layer,
 * allowing additional handlers to be attached without altering the event sources (OCP).
 */
public interface EventHandlerUseCase<E extends DomainEvent> {

    /**
     * Handles a domain event within the application core.
     *
     * @param event event emitted by domain entities or services
     */
    void handle(E event);
}
