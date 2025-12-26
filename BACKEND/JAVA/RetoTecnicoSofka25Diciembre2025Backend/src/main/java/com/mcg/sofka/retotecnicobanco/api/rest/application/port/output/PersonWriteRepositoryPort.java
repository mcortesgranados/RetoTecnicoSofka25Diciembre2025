package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

/**
 * Output port for mutating Person aggregates from the application layer.
 *
 * <p>This port abstracts write operations, keeping the domain/service code independent
 * from storage choices. Implementations may delegate to JPA repositories or other stores in
 * the adapter layer, helping to satisfy both DIP and SRP.
 */
public interface PersonWriteRepositoryPort {

    /**
     * Persists or updates a Person aggregate.
     *
     * @param person entity to persist
     * @return persisted entity with any generated fields populated
     */
    Person save(Person person);

    /**
     * Deletes a Person aggregate by identifier.
     *
     * @param personId identifier to delete
     */
    void deleteById(Long personId);
}
