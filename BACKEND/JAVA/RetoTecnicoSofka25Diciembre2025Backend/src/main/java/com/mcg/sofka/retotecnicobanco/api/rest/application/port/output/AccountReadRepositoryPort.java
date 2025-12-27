package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Output port that exposes read operations for account aggregates.
 */
public interface AccountReadRepositoryPort {

    /**
     * Finds an account by its table-defined identifier.
     *
     * @param accountId identifier
     * @return optional account
     */
    Optional<Account> findById(Long accountId);

    /**
     * Finds an account by its unique number.
     *
     * @param accountNumber unique number
     * @return optional account
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Lists accounts assigned to a specific client.
     *
     * @param clientId client identifier
     * @return accounts for the client
     */
    List<Account> findByClientId(Long clientId);
}
