package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Command DTO containing the data required to register an account movement.
 */
public class CreateMovementCommand {

    private final Long accountId;
    private final String movementType;
    private final BigDecimal amount;
    private final String description;

    @JsonCreator
    public CreateMovementCommand(
            @JsonProperty("accountId") Long accountId,
            @JsonProperty("movementType") String movementType,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("description") String description) {
        this.accountId = accountId;
        this.movementType = movementType;
        this.amount = amount;
        this.description = description;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getMovementType() {
        return movementType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
