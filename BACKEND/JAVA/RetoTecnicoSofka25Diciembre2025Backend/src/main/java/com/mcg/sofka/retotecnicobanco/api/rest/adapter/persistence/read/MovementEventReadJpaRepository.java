package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementEventReadJpaRepository extends JpaRepository<MovementEvent, Long> {

    List<MovementEvent> findByPublishedFalseOrderByCreatedAtAsc();
}
