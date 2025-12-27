package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.dto;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;

import java.time.OffsetDateTime;

/**
 * Light-weight representation of a stored movement event suitable for REST responses.
 */
public final class MovementEventResponse {

    private final Long eventId;
    private final Long movementId;
    private final Boolean published;
    private final OffsetDateTime routedAt;
    private final String payload;
    private final OffsetDateTime createdAt;

    public MovementEventResponse(Long eventId, Long movementId, Boolean published, OffsetDateTime routedAt,
                                 String payload, OffsetDateTime createdAt) {
        this.eventId = eventId;
        this.movementId = movementId;
        this.published = published;
        this.routedAt = routedAt;
        this.payload = payload;
        this.createdAt = createdAt;
    }

    public static MovementEventResponse from(MovementEvent event) {
        Long movementId = event.getMovement() != null ? event.getMovement().getMovementId() : null;
        return new MovementEventResponse(
                event.getEventId(),
                movementId,
                event.getPublished(),
                event.getRoutedAt(),
                event.getPayload(),
                event.getCreatedAt()
        );
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getMovementId() {
        return movementId;
    }

    public Boolean getPublished() {
        return published;
    }

    public OffsetDateTime getRoutedAt() {
        return routedAt;
    }

    public String getPayload() {
        return payload;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
