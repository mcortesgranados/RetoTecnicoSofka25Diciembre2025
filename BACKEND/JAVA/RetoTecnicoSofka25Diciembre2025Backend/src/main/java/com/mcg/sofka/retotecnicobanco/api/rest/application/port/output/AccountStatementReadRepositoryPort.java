package com.mcg.sofka.retotecnicobanco.api.rest.application.port.output;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.AccountStatementEntry;

import java.util.List;

/**
 * Output port that exposes read access to the {@code account_statement} view.
 */
public interface AccountStatementReadRepositoryPort {

    /**
     * Retrieves all statement rows for the given account number.
     *
     * @param accountNumber unique account number
     * @return ledger entries ordered by movement date
     */
    List<AccountStatementEntry> findByAccountNumber(String accountNumber);
}
