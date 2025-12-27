package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

/**
 * Port that exposes write operations for movement aggregates.
 */
public interface MovementWriteRepositoryPort {

    /**
     * Persists a movement.
     *
     * @param movement movement to persist
     * @return persisted movement
     */
    Movement save(Movement movement);
}
