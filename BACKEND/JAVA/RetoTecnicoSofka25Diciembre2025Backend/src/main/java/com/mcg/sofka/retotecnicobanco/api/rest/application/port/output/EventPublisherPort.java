package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;

/**
 * Output port for publishing domain events to the infrastructure layer (Kafka, SNS, etc.).
 *
 * <p>Keeping this interface in the core prevents event-driven adapters from leaking into domain logic,
 * satisfying the Dependency Inversion Principle. Implementations can push events asynchronously using
 * messaging middleware while the application layer simply emits {@link DomainEvent} instances.
 */
public interface EventPublisherPort {

    /**
     * Publishes the provided domain event.
     *
     * @param event event to forward
     */
    void publish(DomainEvent event);
}
