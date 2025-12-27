package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovementReadJpaRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccount_AccountIdOrderByMovementDateDesc(Long accountId);
}
