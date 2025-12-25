package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

import java.util.Optional;

/**
 * Query service that retrieves a single person by identifier.
 *
 * <p>It belongs to the application core layer of the hexagon and depends on the
 * {@link PersonReadRepositoryPort} abstraction. This class stays focused on providing
 * a read-use case (SRP) and can be decorated with additional cross-cutting concerns (OCP).
 */
public class GetPersonService implements QueryUseCase<GetPersonQuery, Optional<Person>> {

    private final PersonReadRepositoryPort readPort;

    /**
     * Creates the service with the required read port.
     *
     * @param readPort abstraction for repository reads
     */
    public GetPersonService(PersonReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    /**
     * Executes the query by delegating to the read port.
     *
     * @param query DTO containing the person identifier
     * @return optional person found
     */
    @Override
    public Optional<Person> execute(GetPersonQuery query) {
        return readPort.findById(query.getPersonId());
    }
}
