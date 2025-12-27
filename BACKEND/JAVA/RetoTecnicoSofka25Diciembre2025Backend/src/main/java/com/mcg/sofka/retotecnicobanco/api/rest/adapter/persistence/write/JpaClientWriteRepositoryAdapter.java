package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import org.springframework.stereotype.Component;

@Component
public class JpaClientWriteRepositoryAdapter implements ClientWriteRepositoryPort {

    private final ClientWriteJpaRepository repository;

    public JpaClientWriteRepositoryAdapter(ClientWriteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }
}
