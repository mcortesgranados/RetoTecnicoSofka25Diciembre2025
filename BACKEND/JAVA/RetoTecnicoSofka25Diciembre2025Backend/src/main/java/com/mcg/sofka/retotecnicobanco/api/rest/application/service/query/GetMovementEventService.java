package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementEventQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;

import java.util.Optional;

/**
 * Query service that fetches a movement event by identifier.
 */
public class GetMovementEventService implements QueryUseCase<GetMovementEventQuery, Optional<MovementEvent>> {

    private final MovementEventReadRepositoryPort readPort;

    public GetMovementEventService(MovementEventReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public Optional<MovementEvent> execute(GetMovementEventQuery query) {
        return readPort.findById(query.getEventId());
    }
}
