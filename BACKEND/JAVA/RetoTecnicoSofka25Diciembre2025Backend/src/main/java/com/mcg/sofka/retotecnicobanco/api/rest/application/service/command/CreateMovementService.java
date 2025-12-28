package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateMovementCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.event.MovementEventRecorder;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.MovementCreatedEvent;
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
    private final MovementEventRecorder movementEventRecorder;
    private final EventPublisherPort publisherPort;

    public CreateMovementService(AccountReadRepositoryPort accountReadPort,
                                 AccountWriteRepositoryPort accountWritePort,
                                 MovementWriteRepositoryPort movementWritePort,
                                 MovementEventRecorder movementEventRecorder,
                                 EventPublisherPort publisherPort) {
        this.accountReadPort = accountReadPort;
        this.accountWritePort = accountWritePort;
        this.movementWritePort = movementWritePort;
        this.movementEventRecorder = movementEventRecorder;
        this.publisherPort = publisherPort;
    }

    @Override
    public Movement execute(CreateMovementCommand command) {
        BigDecimal amount = command.getAmount();
        if (amount == null) {
            throw new IllegalArgumentException("Movement amount must be provided");
        }
        int amountSign = amount.compareTo(BigDecimal.ZERO);
        if (amountSign == 0) {
            throw new IllegalArgumentException("Movement amount must not be zero");
        }

        Account account = accountReadPort.findById(command.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Movement.MovementType movementType = Movement.MovementType.from(command.getMovementType());
        if (movementType == Movement.MovementType.CREDIT && amountSign < 0) {
            throw new IllegalArgumentException("Credit movements must have a positive amount");
        }
        if (movementType == Movement.MovementType.DEBIT && amountSign > 0) {
            throw new IllegalArgumentException("Debit movements must have a negative amount");
        }

        BigDecimal currentBalance = account.getCurrentBalance() != null ? account.getCurrentBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = currentBalance.add(amount);

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

        Movement saved = movementWritePort.save(movement);
        movementEventRecorder.record(saved);
        publisherPort.publish(new MovementCreatedEvent(saved));
        return saved;
    }
}
