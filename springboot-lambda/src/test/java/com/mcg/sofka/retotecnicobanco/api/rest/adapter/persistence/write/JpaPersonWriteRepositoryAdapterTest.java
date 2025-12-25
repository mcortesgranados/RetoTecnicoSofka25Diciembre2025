package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JpaPersonWriteRepositoryAdapterTest {

    @Test
    void saveShouldDelegateToJpaRepository() {
        PersonWriteJpaRepository repository = Mockito.mock(PersonWriteJpaRepository.class);
        JpaPersonWriteRepositoryAdapter adapter = new JpaPersonWriteRepositoryAdapter(repository);
        Person person = new Person();

        adapter.save(person);
        Mockito.verify(repository).save(person);
    }
}
