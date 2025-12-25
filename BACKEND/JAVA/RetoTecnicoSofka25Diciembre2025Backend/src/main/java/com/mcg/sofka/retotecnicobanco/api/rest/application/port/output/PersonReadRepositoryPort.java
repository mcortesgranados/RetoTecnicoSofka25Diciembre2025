package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Output port that exposes read operations for Person entities to the application layer.
 *
 * <p>Read adapters implement this interface to keep the domain model isolated from persistence details,
 * satisfying the Dependency Inversion Principle. The interface also provides a seam for testing and
 * decorating pipeline behaviors without changing the core.
 */
public interface PersonReadRepositoryPort {

    /**
     * Finds a person by primary key.
     *
     * @param personId identifier
     * @return optional person
     */
    Optional<Person> findById(Long personId);

    /**
     * Finds persons matching a given identification value.
     *
     * @param identification search criteria
     * @return list of persons
     */
    List<Person> findByIdentification(String identification);
}
