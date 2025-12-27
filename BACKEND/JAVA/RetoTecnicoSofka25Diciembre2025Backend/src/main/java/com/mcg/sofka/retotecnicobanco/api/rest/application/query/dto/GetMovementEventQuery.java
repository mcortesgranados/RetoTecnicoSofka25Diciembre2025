package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO for fetching a movement event by its database identifier.
 */
public class GetMovementEventQuery {

    private final Long eventId;

    public GetMovementEventQuery(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
