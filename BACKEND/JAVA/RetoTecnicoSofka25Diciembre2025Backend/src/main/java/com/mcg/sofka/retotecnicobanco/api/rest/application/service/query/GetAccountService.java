package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;

import java.util.Optional;

/**
 * Query service that retrieves an account by its identifier.
 */
public class GetAccountService implements QueryUseCase<GetAccountQuery, Optional<Account>> {

    private final AccountReadRepositoryPort readPort;

    public GetAccountService(AccountReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public Optional<Account> execute(GetAccountQuery query) {
        return readPort.findById(query.getAccountId());
    }
}
