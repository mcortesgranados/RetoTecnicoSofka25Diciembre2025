package com.mcg.sofka.retotecnicobanco.api.rest.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Immutable read model that mirrors the {@code account_statement} view used for reporting.
 */
public final class AccountStatementEntry {

    private final String clientCode;
    private final String firstName;
    private final String lastName;
    private final String accountNumber;
    private final String accountType;
    private final BigDecimal currentBalance;
    private final OffsetDateTime movementDate;
    private final Movement.MovementType movementType;
    private final BigDecimal movementAmount;
    private final BigDecimal balanceAfter;
    private final String description;

    public AccountStatementEntry(String clientCode,
                                 String firstName,
                                 String lastName,
                                 String accountNumber,
                                 String accountType,
                                 BigDecimal currentBalance,
                                 OffsetDateTime movementDate,
                                 Movement.MovementType movementType,
                                 BigDecimal movementAmount,
                                 BigDecimal balanceAfter,
                                 String description) {
        this.clientCode = clientCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
        this.movementDate = movementDate;
        this.movementType = movementType;
        this.movementAmount = movementAmount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    public String getClientCode() {
        return clientCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public OffsetDateTime getMovementDate() {
        return movementDate;
    }

    public Movement.MovementType getMovementType() {
        return movementType;
    }

    public BigDecimal getMovementAmount() {
        return movementAmount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }
}
