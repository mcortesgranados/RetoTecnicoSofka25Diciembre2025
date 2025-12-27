package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

import java.util.Optional;

/**
 * Query service that fetches a movement by identifier.
 */
public class GetMovementService implements QueryUseCase<GetMovementQuery, Optional<Movement>> {

    private final MovementReadRepositoryPort readPort;

    public GetMovementService(MovementReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public Optional<Movement> execute(GetMovementQuery query) {
        return readPort.findById(query.getMovementId());
    }
}
