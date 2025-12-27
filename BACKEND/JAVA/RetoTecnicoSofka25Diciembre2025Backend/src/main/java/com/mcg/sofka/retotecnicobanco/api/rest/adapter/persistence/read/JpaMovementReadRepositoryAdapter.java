package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaMovementReadRepositoryAdapter implements MovementReadRepositoryPort {

    private final MovementReadJpaRepository repository;

    public JpaMovementReadRepositoryAdapter(MovementReadJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Movement> findById(Long movementId) {
        return repository.findById(movementId);
    }

    @Override
    public List<Movement> findByAccountId(Long accountId) {
        return repository.findByAccount_AccountIdOrderByMovementDateDesc(accountId);
    }
}
