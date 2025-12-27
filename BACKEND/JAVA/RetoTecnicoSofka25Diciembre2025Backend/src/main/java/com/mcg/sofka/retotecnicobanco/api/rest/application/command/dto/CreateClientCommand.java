package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Carries the values needed to register a client that extends an existing person record.
 */
public class CreateClientCommand {

    private final Long personId;
    private final String clientCode;
    private final String passwordHash;
    private final Boolean active;

    @JsonCreator
    public CreateClientCommand(
            @JsonProperty("personId") Long personId,
            @JsonProperty("clientCode") String clientCode,
            @JsonProperty("passwordHash") String passwordHash,
            @JsonProperty("active") Boolean active) {
        this.personId = personId;
        this.clientCode = clientCode;
        this.passwordHash = passwordHash;
        this.active = active;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Boolean getActive() {
        return active;
    }
}
