package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaMovementEventReadRepositoryAdapter implements MovementEventReadRepositoryPort {

    private final MovementEventReadJpaRepository repository;

    public JpaMovementEventReadRepositoryAdapter(MovementEventReadJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MovementEvent> findPendingEvents() {
        return repository.findByPublishedFalseOrderByCreatedAtAsc();
    }

    @Override
    public Optional<MovementEvent> findById(Long eventId) {
        return repository.findById(eventId);
    }
}
