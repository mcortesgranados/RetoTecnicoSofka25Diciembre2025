package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import org.springframework.stereotype.Component;

@Component
public class JpaMovementWriteRepositoryAdapter implements MovementWriteRepositoryPort {

    private final MovementWriteJpaRepository repository;

    public JpaMovementWriteRepositoryAdapter(MovementWriteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movement save(Movement movement) {
        return repository.save(movement);
    }
}
