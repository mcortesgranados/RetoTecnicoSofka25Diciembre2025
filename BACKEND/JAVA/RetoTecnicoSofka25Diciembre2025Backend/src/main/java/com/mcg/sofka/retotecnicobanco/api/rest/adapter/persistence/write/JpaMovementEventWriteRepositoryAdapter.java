package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import org.springframework.stereotype.Component;

@Component
public class JpaMovementEventWriteRepositoryAdapter implements MovementEventWriteRepositoryPort {

    private final MovementEventWriteJpaRepository repository;

    public JpaMovementEventWriteRepositoryAdapter(MovementEventWriteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovementEvent save(MovementEvent movementEvent) {
        return repository.save(movementEvent);
    }
}
