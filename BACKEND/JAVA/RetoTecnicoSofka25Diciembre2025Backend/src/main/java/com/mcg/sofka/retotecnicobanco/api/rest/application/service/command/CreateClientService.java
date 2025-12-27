package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateClientCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Command service that registers clients tied to an existing Person entity.
 */
public class CreateClientService implements CommandUseCase<CreateClientCommand, Client> {

    private final PersonReadRepositoryPort personReadPort;
    private final ClientReadRepositoryPort clientReadPort;
    private final ClientWriteRepositoryPort clientWritePort;

    public CreateClientService(PersonReadRepositoryPort personReadPort,
                               ClientReadRepositoryPort clientReadPort,
                               ClientWriteRepositoryPort clientWritePort) {
        this.personReadPort = personReadPort;
        this.clientReadPort = clientReadPort;
        this.clientWritePort = clientWritePort;
    }

    @Override
    public Client execute(CreateClientCommand command) {
        Person person = personReadPort.findById(command.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        Optional<Client> existing = clientReadPort.findByClientCode(command.getClientCode());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Client code already exists");
        }

        Client client = new Client();
        client.setPerson(person);
        client.setClientCode(command.getClientCode());
        client.setPasswordHash(command.getPasswordHash());
        client.setActive(command.getActive() == null ? Boolean.TRUE : command.getActive());
        client.setRegisteredAt(OffsetDateTime.now());

        return clientWritePort.save(client);
    }
}
