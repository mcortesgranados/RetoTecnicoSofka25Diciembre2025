package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;

/**
 * Output port that exposes write operations for account aggregates.
 */
public interface AccountWriteRepositoryPort {

    /**
     * Persists an account aggregate.
     *
     * @param account account to persist
     * @return persisted account
     */
    Account save(Account account);
}
