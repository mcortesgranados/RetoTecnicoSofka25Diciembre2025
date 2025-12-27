package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;

/**
 * Output port that exposes write access to movement events.
 */
public interface MovementEventWriteRepositoryPort {

    /**
     * Persists a movement event record.
     *
     * @param movementEvent movement event
     * @return persisted event
     */
    MovementEvent save(MovementEvent movementEvent);
}
