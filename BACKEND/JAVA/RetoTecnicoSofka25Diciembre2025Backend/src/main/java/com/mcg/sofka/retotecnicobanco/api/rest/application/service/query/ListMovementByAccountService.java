package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListMovementByAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

import java.util.List;

/**
 * Query service that lists movements for an account.
 */
public class ListMovementByAccountService implements QueryUseCase<ListMovementByAccountQuery, List<Movement>> {

    private final MovementReadRepositoryPort readPort;

    public ListMovementByAccountService(MovementReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public List<Movement> execute(ListMovementByAccountQuery query) {
        return readPort.findByAccountId(query.getAccountId());
    }
}
