package com.mcg.sofka.retotecnicobanco.api.rest.domain.event;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

/**
 * Event emitted when a movement is recorded for an account.
 */
public final class MovementCreatedEvent implements DomainEvent {

    private final Movement movement;

    public MovementCreatedEvent(Movement movement) {
        this.movement = movement;
    }

    public Movement getMovement() {
        return movement;
    }

    @Override
    public String eventName() {
        return "movement.created";
    }
}
