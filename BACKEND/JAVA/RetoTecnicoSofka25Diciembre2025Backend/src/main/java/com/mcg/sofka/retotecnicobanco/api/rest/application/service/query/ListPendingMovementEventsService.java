package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPendingMovementEventsQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;

import java.util.List;

/**
 * Query service that exposes pending movement events awaiting publication.
 */
public class ListPendingMovementEventsService implements QueryUseCase<ListPendingMovementEventsQuery, List<MovementEvent>> {

    private final MovementEventReadRepositoryPort readPort;

    public ListPendingMovementEventsService(MovementEventReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public List<MovementEvent> execute(ListPendingMovementEventsQuery query) {
        return readPort.findPendingEvents();
    }
}
