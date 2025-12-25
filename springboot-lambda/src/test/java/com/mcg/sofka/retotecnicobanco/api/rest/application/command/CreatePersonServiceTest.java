package com.mcg.sofka.retotecnicobanco.api.rest.application.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreatePersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;

class CreatePersonServiceTest {

    @Test
    void executeShouldSaveAndPublishEvent() {
        PersonWriteRepositoryPort writePort = Mockito.mock(PersonWriteRepositoryPort.class);
        EventPublisherPort publisherPort = Mockito.mock(EventPublisherPort.class);
        Person savedPerson = new Person();
        savedPerson.setPersonId(1L);
        savedPerson.setCreatedAt(OffsetDateTime.now());
        savedPerson.setUpdatedAt(savedPerson.getCreatedAt());

        Mockito.when(writePort.save(Mockito.any(Person.class))).thenReturn(savedPerson);

        CreatePersonService service = new CreatePersonService(writePort, publisherPort);
        service.execute(new CreatePersonCommand("Jose","Lema","MALE",34,"123","addr","phone"));

        Mockito.verify(writePort).save(Mockito.any(Person.class));
        Mockito.verify(publisherPort).publish(Mockito.any());
    }
}
