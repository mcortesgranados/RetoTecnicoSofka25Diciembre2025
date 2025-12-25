package com.mcg.sofka.retotecnicobanco.api.rest.application.service;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;

/**
 * Application-level event handler that can be wired into adapters to react to domain events.
 *
 * <p>This class sits in the core application layer of the hexagon and encapsulates the reaction
 * logic that should occur whenever the domain emits events. It maintains a single responsibility
 * (SRP) by focusing on event transformation and delegation, and it keeps the core decoupled from
 * the transport mechanisms (OCP/DIP) since adapters supply {@link com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort}.
 */
public class EventDrivenService {

    /**
     * Handles an incoming domain event by delegating to a configurable publisher.
     *
     * <p>The method exists to be bound to {@link com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.EventHandlerUseCase}
     * so adapters can plug the event pipeline into the event-driven architecture without knowing implementation details.
     *
     * @param event domain event emitted by the application layer
     */
    public void handle(DomainEvent event) {
        // Extendable reaction to domain events (e.g., publish to Kafka/SNS)
    }
}
