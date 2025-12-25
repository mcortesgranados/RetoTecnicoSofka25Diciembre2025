package com.mcg.sofka.retotecnicobanco.api.rest.domain.command.handlers;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.EventPublisher;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.PersonCreatedEvent;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

/**
 * Domain command handler that translates person mutations into domain events.
 *
 * <p>This handler sits alongside domain logic and maintains a single responsibility: publishing
 * {@link PersonCreatedEvent} instances when invoked. It depends only on {@link EventPublisher}
 * abstractions, keeping in line with hexagonal architecture principles.
 */
public class PersonCommandHandler {

    private final EventPublisher publisher;

    /**
     * Constructor injecting the domain-level publisher.
     *
     * @param publisher domain event publisher
     */
    public PersonCommandHandler(EventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Publishes a {@link PersonCreatedEvent} for the provided person aggregate.
     *
     * @param person created person
     */
    public void handle(Person person) {
        publisher.publish(new PersonCreatedEvent(person));
    }
}
