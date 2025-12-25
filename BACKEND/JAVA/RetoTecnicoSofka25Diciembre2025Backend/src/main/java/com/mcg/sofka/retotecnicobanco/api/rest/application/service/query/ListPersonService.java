package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

import java.util.List;

/**
 * Query service that lists people filtered by identification where needed.
 * <p>It acts as a part of the application core inside the hexagonal architecture, using the
 * {@link PersonReadRepositoryPort} to fulfill read operations while hiding persistence details
 * from adapters (Dependency Inversion). The class keeps a single responsibility by focusing on
 * access patterns requested through {@link ListPersonQuery} and can be extended through decorators (OCP).
 */
public class ListPersonService implements QueryUseCase<ListPersonQuery, List<Person>> {

    private final PersonReadRepositoryPort readPort;

    /**
     * Creates the service instance with the required read port.
     *
     * @param readPort persistence abstraction used for listing Person entities
     */
    public ListPersonService(PersonReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    /**
     * Executes the listing query by looking up persons by the identification criterion.
     *
     * @param query input criteria for the listing request
     * @return list of matching persons
     */
    @Override
    public List<Person> execute(ListPersonQuery query) {
        return readPort.findByIdentification(query.getIdentification());
    }
}
