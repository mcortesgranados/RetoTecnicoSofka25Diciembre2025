package com.mcg.sofka.retotecnicobanco.api.rest.domain.event;

/**
 * Domain-level publisher that can be implemented by domain services or adapters to emit events.
 *
 * <p>This interface allows domain logic to trigger events without knowing about outer infrastructure,
 * keeping the core clean and testable.
 */
public interface EventPublisher {

    /**
     * Publishes the provided domain event.
     *
     * @param event event emitted for downstream systems
     */
    void publish(DomainEvent event);
}
