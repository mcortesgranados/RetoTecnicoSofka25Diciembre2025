package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO for retrieving a movement by identifier.
 */
public class GetMovementQuery {

    private final Long movementId;

    public GetMovementQuery(Long movementId) {
        this.movementId = movementId;
    }

    public Long getMovementId() {
        return movementId;
    }
}
