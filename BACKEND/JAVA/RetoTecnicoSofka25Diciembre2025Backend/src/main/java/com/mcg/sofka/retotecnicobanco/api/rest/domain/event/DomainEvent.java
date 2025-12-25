package com.mcg.sofka.retotecnicobanco.api.rest.domain.event;

/**
 * Base contract for domain events emitted by the core.
 *
 * <p>Keeping this interface in the domain layer ensures other layers can react to events
 * without introducing tight coupling. It supports the event-driven architecture by providing
 * a consistent shape for messages published through {@link com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort}.
 */
public interface DomainEvent {

    /**
     * Returns the canonical name of the event, which adapters may use for routing.
     *
     * @return event name
     */
    String eventName();
}
