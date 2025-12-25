package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaPersonReadRepositoryAdapter implements PersonReadRepositoryPort {

    private final PersonReadJpaRepository repository;

    public JpaPersonReadRepositoryAdapter(PersonReadJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Person> findById(Long personId) {
        return repository.findById(personId);
    }

    @Override
    public List<Person> findByIdentification(String identification) {
        return repository.findByIdentification(identification);
    }
}
