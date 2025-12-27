package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;

import java.util.Optional;

/**
 * Output port that exposes read operations for clients.
 */
public interface ClientReadRepositoryPort {

    /**
     * Finds a client by its primary identifier.
     *
     * @param clientId identifier
     * @return optional client
     */
    Optional<Client> findById(Long clientId);

    /**
     * Finds a client by its unique code.
     *
     * @param clientCode code
     * @return optional client
     */
    Optional<Client> findByClientCode(String clientCode);
}
