package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;

/**
 * Output port that exposes write operations for client aggregates.
 */
public interface ClientWriteRepositoryPort {

    /**
     * Persists a client record.
     *
     * @param client entity to persist
     * @return persisted entity with identifiers populated
     */
    Client save(Client client);
}
