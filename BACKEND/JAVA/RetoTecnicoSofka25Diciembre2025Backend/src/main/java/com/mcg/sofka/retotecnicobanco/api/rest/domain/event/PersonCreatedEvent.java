package com.mcg.sofka.retotecnicobanco.api.rest.domain.event;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

/**
 * Event emitted after a Person aggregate is created.
 *
 * <p>Being immutable, it carries the snapshot to the event-driven adapters while staying in the domain
 * layer, ensuring downstream consumers only depend on the {@link DomainEvent} contract.
 */
public final class PersonCreatedEvent implements DomainEvent {

    private final Person person;

    /**
     * Constructs the event with the newly created person.
     *
     * @param person person aggregate
     */
    public PersonCreatedEvent(Person person) {
        this.person = person;
    }

    /**
     * Returns the person payload that listeners will consume.
     *
     * @return created person
     */
    public Person getPerson() {
        return person;
    }

    @Override
    public String eventName() {
        return "person.created";
    }
}
