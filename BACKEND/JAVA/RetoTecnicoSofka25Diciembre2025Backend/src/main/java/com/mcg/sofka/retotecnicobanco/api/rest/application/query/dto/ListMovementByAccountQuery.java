package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO for listing movements related to an account.
 */
public class ListMovementByAccountQuery {

    private final Long accountId;

    public ListMovementByAccountQuery(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
