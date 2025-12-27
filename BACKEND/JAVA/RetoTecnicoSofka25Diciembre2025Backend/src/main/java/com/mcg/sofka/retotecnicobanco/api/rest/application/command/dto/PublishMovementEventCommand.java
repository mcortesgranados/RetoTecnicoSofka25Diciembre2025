package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

import java.time.OffsetDateTime;

/**
 * Command used to mark a persisted movement event as routed to downstream systems.
 */
public class PublishMovementEventCommand {

    private final Long eventId;
    private final OffsetDateTime routedAt;

    public PublishMovementEventCommand(Long eventId, OffsetDateTime routedAt) {
        this.eventId = eventId;
        this.routedAt = routedAt;
    }

    public Long getEventId() {
        return eventId;
    }

    public OffsetDateTime getRoutedAt() {
        return routedAt;
    }
}
