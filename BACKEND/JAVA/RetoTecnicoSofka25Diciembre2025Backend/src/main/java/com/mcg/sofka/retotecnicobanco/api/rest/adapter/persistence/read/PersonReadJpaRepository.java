package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonReadJpaRepository extends JpaRepository<Person, Long> {
    List<Person> findByIdentification(String identification);
}
