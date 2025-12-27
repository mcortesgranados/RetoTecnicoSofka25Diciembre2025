package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;

import java.util.Optional;

/**
 * Query service that fetches clients by their identifier.
 */
public class GetClientService implements QueryUseCase<GetClientQuery, Optional<Client>> {

    private final ClientReadRepositoryPort readPort;

    public GetClientService(ClientReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    @Override
    public Optional<Client> execute(GetClientQuery query) {
        return readPort.findById(query.getClientId());
    }
}
