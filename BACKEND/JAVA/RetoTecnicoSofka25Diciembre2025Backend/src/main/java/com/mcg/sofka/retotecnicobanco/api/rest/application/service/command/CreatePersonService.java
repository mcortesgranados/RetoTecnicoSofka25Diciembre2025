package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.PersonCreatedEvent;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

import java.time.OffsetDateTime;

/**
 * Application command service that handles the creation of Person aggregates.
 *
 * <p>Its single responsibility (SRP) is to mediate between the {@link CreatePersonCommand} command
 * and the persistence and event publishing ports. It adheres to the Dependency Inversion Principle
 * by depending on the {@link PersonWriteRepositoryPort} and {@link EventPublisherPort} abstractions,
 * letting the adapter layer provide the concrete implementations. This service is part of the application
 * core inside the hexagonal architecture and triggers the event-driven flow after persistence operations.
 */
public class CreatePersonService implements CommandUseCase<CreatePersonCommand, Person> {

    private final PersonWriteRepositoryPort writePort;
    private final EventPublisherPort publisherPort;

    /**
     * Constructs the command service with required ports.
     *
     * @param writePort     abstraction for writing Person data
     * @param publisherPort abstraction for publishing domain events
     */
    public CreatePersonService(PersonWriteRepositoryPort writePort, EventPublisherPort publisherPort) {
        this.writePort = writePort;
        this.publisherPort = publisherPort;
    }

    /**
     * Executes the create command by mapping the DTO into the domain, persisting it,
     * and emitting a {@link PersonCreatedEvent}.
     *
     * <p>This method keeps its logic focused (SRP) on orchestrating repository and event interactions so that
     * new event-driven behaviors can be composed without modifying the service (OCP).
     *
     * @param command command DTO containing creation data
     */
    @Override
    public Person execute(CreatePersonCommand command) {
        Person person = new Person();
        person.setFirstName(command.getFirstName());
        person.setLastName(command.getLastName());
        person.setGender(Person.Gender.from(command.getGender()));
        person.setAge(command.getAge());
        person.setIdentification(command.getIdentification());
        person.setAddress(command.getAddress());
        person.setPhone(command.getPhone());
        person.setCreatedAt(OffsetDateTime.now());
        person.setUpdatedAt(person.getCreatedAt());

        Person saved = writePort.save(person);
        publisherPort.publish(new PersonCreatedEvent(saved));
        return saved;
    }
}
