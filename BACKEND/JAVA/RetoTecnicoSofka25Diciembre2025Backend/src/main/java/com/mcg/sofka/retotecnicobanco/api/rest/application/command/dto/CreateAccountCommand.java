package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Command DTO that captures the required data for opening a new account.
 */
public class CreateAccountCommand {

    private final String accountNumber;
    private final Long clientId;
    private final String accountType;
    private final BigDecimal initialBalance;
    private final Boolean active;

    @JsonCreator
    public CreateAccountCommand(
            @JsonProperty("accountNumber") String accountNumber,
            @JsonProperty("clientId") Long clientId,
            @JsonProperty("accountType") String accountType,
            @JsonProperty("initialBalance") BigDecimal initialBalance,
            @JsonProperty("active") Boolean active) {
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.active = active;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public Boolean getActive() {
        return active;
    }
}
