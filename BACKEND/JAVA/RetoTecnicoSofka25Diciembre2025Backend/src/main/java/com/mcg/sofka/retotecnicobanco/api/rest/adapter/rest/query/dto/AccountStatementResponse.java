package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.dto;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.AccountStatementEntry;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Response payload that mirrors the data produced by the {@code account_statement} view.
 */
public final class AccountStatementResponse {

    private final String clientCode;
    private final String firstName;
    private final String lastName;
    private final String accountNumber;
    private final String accountType;
    private final BigDecimal currentBalance;
    private final OffsetDateTime movementDate;
    private final String movementType;
    private final BigDecimal movementAmount;
    private final BigDecimal balanceAfter;
    private final String description;

    public AccountStatementResponse(String clientCode,
                                    String firstName,
                                    String lastName,
                                    String accountNumber,
                                    String accountType,
                                    BigDecimal currentBalance,
                                    OffsetDateTime movementDate,
                                    String movementType,
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

    public static AccountStatementResponse from(AccountStatementEntry entry) {
        return new AccountStatementResponse(
                entry.getClientCode(),
                entry.getFirstName(),
                entry.getLastName(),
                entry.getAccountNumber(),
                entry.getAccountType(),
                entry.getCurrentBalance(),
                entry.getMovementDate(),
                entry.getMovementType() != null ? entry.getMovementType().name() : null,
                entry.getMovementAmount(),
                entry.getBalanceAfter(),
                entry.getDescription()
        );
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

    public String getMovementType() {
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
