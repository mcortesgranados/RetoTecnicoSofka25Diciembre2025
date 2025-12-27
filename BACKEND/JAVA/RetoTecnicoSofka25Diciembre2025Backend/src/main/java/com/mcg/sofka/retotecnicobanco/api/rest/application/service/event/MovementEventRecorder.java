package com.mcg.sofka.retotecnicobanco.api.rest.application.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Records movement events into the persistent store so downstream systems can consume them asynchronously.
 */
@Component
public class MovementEventRecorder {

    private final MovementEventWriteRepositoryPort writePort;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public MovementEventRecorder(MovementEventWriteRepositoryPort writePort) {
        this.writePort = writePort;
    }

    public MovementEvent record(Movement movement) {
        MovementEvent event = new MovementEvent();
        event.setMovement(movement);
        event.setPayload(serializePayload(movement));
        event.setPublished(Boolean.FALSE);
        event.setCreatedAt(OffsetDateTime.now());
        return writePort.save(event);
    }

    private String serializePayload(Movement movement) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("eventName", "movement.created");
        payload.put("movementId", movement.getMovementId());
        payload.put("accountId", movement.getAccount().getAccountId());
        payload.put("movementType", movement.getMovementType().name());
        payload.put("amount", movement.getAmount());
        payload.put("balanceAfter", movement.getBalanceAfter());
        payload.put("movementDate", movement.getMovementDate());
        payload.put("description", movement.getDescription());
        try {
        return OBJECT_MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Unable to serialize movement event payload", ex);
        }
    }
}
