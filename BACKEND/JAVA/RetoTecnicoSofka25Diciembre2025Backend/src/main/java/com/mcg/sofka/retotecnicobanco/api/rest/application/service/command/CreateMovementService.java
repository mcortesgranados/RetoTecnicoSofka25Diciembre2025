package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateMovementCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Command service that registers account movements and adjusts balances.
 */
public class CreateMovementService implements CommandUseCase<CreateMovementCommand, Movement> {

    private final AccountReadRepositoryPort accountReadPort;
    private final AccountWriteRepositoryPort accountWritePort;
    private final MovementWriteRepositoryPort movementWritePort;

    public CreateMovementService(AccountReadRepositoryPort accountReadPort,
                                 AccountWriteRepositoryPort accountWritePort,
                                 MovementWriteRepositoryPort movementWritePort) {
        this.accountReadPort = accountReadPort;
        this.accountWritePort = accountWritePort;
        this.movementWritePort = movementWritePort;
    }

    @Override
    public Movement execute(CreateMovementCommand command) {
        BigDecimal amount = command.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Movement amount must be a positive value");
        }

        Account account = accountReadPort.findById(command.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Movement.MovementType movementType = Movement.MovementType.from(command.getMovementType());
        BigDecimal currentBalance = account.getCurrentBalance() != null ? account.getCurrentBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = movementType == Movement.MovementType.CREDIT
                ? currentBalance.add(amount)
                : currentBalance.subtract(amount);

        account.setCurrentBalance(newBalance);
        account.setUpdatedAt(OffsetDateTime.now());
        accountWritePort.save(account);

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setMovementType(movementType);
        movement.setAmount(amount);
        movement.setDescription(command.getDescription() == null ? "" : command.getDescription().trim());
        movement.setMovementDate(OffsetDateTime.now());
        movement.setBalanceAfter(newBalance);
        movement.setCreatedAt(OffsetDateTime.now());

        return movementWritePort.save(movement);
    }
}
