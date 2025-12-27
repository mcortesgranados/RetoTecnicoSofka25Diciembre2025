package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO that lists accounts for a specific client.
 */
public class ListAccountByClientQuery {

    private final Long clientId;

    public ListAccountByClientQuery(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }
}
