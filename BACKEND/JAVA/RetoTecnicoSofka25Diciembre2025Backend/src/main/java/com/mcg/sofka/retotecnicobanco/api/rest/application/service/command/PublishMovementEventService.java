package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.PublishMovementEventCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;

import java.time.OffsetDateTime;

/**
 * Command service that marks movement events as published after downstream routing.
 */
public class PublishMovementEventService implements CommandUseCase<PublishMovementEventCommand, MovementEvent> {

    private final MovementEventReadRepositoryPort readPort;
    private final MovementEventWriteRepositoryPort writePort;

    public PublishMovementEventService(MovementEventReadRepositoryPort readPort,
                                       MovementEventWriteRepositoryPort writePort) {
        this.readPort = readPort;
        this.writePort = writePort;
    }

    @Override
    public MovementEvent execute(PublishMovementEventCommand command) {
        MovementEvent event = readPort.findById(command.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Movement event not found: " + command.getEventId()));

        event.setPublished(Boolean.TRUE);
        event.setRoutedAt(command.getRoutedAt() != null ? command.getRoutedAt() : OffsetDateTime.now());
        return writePort.save(event);
    }
}
