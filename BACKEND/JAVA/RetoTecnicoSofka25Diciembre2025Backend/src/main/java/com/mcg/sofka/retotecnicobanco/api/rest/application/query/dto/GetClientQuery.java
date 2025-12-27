package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO for retrieving a client by its identifier.
 */
public class GetClientQuery {

    private final Long clientId;

    public GetClientQuery(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }
}
