package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListAccountByClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;

import java.util.List;

/**
 * Query service that lists accounts associated to a client.
 */
public class ListAccountByClientService implements QueryUseCase<ListAccountByClientQuery, List<Account>> {

    private final AccountReadRepositoryPort readPort;

    public ListAccountByClientService(AccountReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public List<Account> execute(ListAccountByClientQuery query) {
        return readPort.findByClientId(query.getClientId());
    }
}
