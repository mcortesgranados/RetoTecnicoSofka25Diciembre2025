package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command.dto;

import java.time.OffsetDateTime;

/**
 * Request payload that can optionally send the routed timestamp when publishing an event.
 */
public class MovementEventPublishRequest {

    private OffsetDateTime routedAt;

    public OffsetDateTime getRoutedAt() {
        return routedAt;
    }

    public void setRoutedAt(OffsetDateTime routedAt) {
        this.routedAt = routedAt;
    }
}
