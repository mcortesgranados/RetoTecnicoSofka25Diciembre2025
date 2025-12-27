package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementWriteJpaRepository extends JpaRepository<Movement, Long> {
}
