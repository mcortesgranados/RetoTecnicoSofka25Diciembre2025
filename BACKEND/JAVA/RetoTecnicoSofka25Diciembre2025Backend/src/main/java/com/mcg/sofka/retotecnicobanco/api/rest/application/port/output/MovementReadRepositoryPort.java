package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

import java.util.List;
import java.util.Optional;

/**
 * Port that exposes read operations for movement aggregates.
 */
public interface MovementReadRepositoryPort {

    /**
     * Finds a movement by its identifier.
     *
     * @param movementId identifier
     * @return optional movement
     */
    Optional<Movement> findById(Long movementId);

    /**
     * Lists movements belonging to an account.
     *
     * @param accountId account identifier
     * @return movements ordered by date
     */
    List<Movement> findByAccountId(Long accountId);
}
