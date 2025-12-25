package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.springframework.stereotype.Component;

@Component
public class JpaPersonWriteRepositoryAdapter implements PersonWriteRepositoryPort {

    private final PersonWriteJpaRepository repository;

    public JpaPersonWriteRepositoryAdapter(PersonWriteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person save(Person person) {
        return repository.save(person);
    }

    @Override
    public void deleteById(Long personId) {
        repository.deleteById(personId);
    }
}
