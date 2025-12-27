package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaClientReadRepositoryAdapter implements ClientReadRepositoryPort {

    private final ClientReadJpaRepository repository;

    public JpaClientReadRepositoryAdapter(ClientReadJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Client> findById(Long clientId) {
        return repository.findById(clientId);
    }

    @Override
    public Optional<Client> findByClientCode(String clientCode) {
        return repository.findByClientCode(clientCode);
    }
}
