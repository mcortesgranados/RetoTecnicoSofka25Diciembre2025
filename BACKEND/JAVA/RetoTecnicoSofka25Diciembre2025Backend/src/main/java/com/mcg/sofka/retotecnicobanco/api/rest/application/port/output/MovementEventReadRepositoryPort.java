package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;

import java.util.List;
import java.util.Optional;

/**
 * Output port that exposes read access to movement events.
 */
public interface MovementEventReadRepositoryPort {

    /**
     * Lists pending movement events that are not yet published.
     *
     * @return pending movement events ordered by creation time
     */
    List<MovementEvent> findPendingEvents();

    /**
     * Finds a movement event by its identifier.
     *
     * @param eventId identifier of the event to load
     * @return optional event that matches the identifier
     */
    Optional<MovementEvent> findById(Long eventId);
}
