package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO used to fetch a single account by its identifier.
 */
public class GetAccountQuery {

    private final Long accountId;

    public GetAccountQuery(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
