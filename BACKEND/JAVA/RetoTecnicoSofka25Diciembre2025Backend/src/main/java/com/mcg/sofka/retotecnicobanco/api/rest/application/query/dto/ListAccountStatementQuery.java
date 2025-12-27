package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query object used to request an account statement by account identifier.
 */
public final class ListAccountStatementQuery {

    private final Long accountId;

    public ListAccountStatementQuery(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
