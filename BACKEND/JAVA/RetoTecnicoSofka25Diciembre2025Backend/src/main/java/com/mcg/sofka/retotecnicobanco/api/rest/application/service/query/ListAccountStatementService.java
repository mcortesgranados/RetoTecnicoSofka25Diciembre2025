package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountStatementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListAccountStatementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.AccountStatementEntry;

import java.util.List;

/**
 * Query service that reads the account statement view for a specific account.
 */
public class ListAccountStatementService implements QueryUseCase<ListAccountStatementQuery, List<AccountStatementEntry>> {

    private final AccountReadRepositoryPort accountReadPort;
    private final AccountStatementReadRepositoryPort statementReadPort;

    public ListAccountStatementService(AccountReadRepositoryPort accountReadPort,
                                       AccountStatementReadRepositoryPort statementReadPort) {
        this.accountReadPort = accountReadPort;
        this.statementReadPort = statementReadPort;
    }

    @Override
    public List<AccountStatementEntry> execute(ListAccountStatementQuery query) {
        String accountNumber = accountReadPort.findById(query.getAccountId())
                .map(account -> account.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + query.getAccountId()));
        return statementReadPort.findByAccountNumber(accountNumber);
    }
}
