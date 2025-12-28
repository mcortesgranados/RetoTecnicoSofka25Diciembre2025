package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateMovementCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.event.MovementEventRecorder;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.exception.MovementInsufficientBalanceException;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateMovementServiceTest {

    @Test
    void recordsCreditMovementWhenAmountIsPositive() {
        Fixture fixture = new Fixture();
        fixture.account.setCurrentBalance(BigDecimal.valueOf(1_000));

        Movement movement = fixture.service.execute(
                new CreateMovementCommand(1L, "CREDIT", BigDecimal.valueOf(500), "Salary"));

        assertEquals(BigDecimal.valueOf(1_500), fixture.account.getCurrentBalance());
        assertEquals(BigDecimal.valueOf(500), movement.getAmount());
        assertEquals(Movement.MovementType.CREDIT, movement.getMovementType());
    }

    @Test
    void recordsDebitMovementWhenAmountIsNegative() {
        Fixture fixture = new Fixture();
        fixture.account.setCurrentBalance(BigDecimal.valueOf(900));

        Movement movement = fixture.service.execute(
                new CreateMovementCommand(1L, "DEBIT", BigDecimal.valueOf(-300), "Bill"));

        assertEquals(BigDecimal.valueOf(600), fixture.account.getCurrentBalance());
        assertEquals(BigDecimal.valueOf(-300), movement.getAmount());
        assertEquals(Movement.MovementType.DEBIT, movement.getMovementType());
    }

    @Test
    void rejectsCreditWhenAmountIsNegative() {
        Fixture fixture = new Fixture();

        assertThrows(IllegalArgumentException.class, () ->
                fixture.service.execute(
                        new CreateMovementCommand(1L, "CREDIT", BigDecimal.valueOf(-100), "Refund")));
    }

    @Test
    void rejectsZeroAmount() {
        Fixture fixture = new Fixture();

        assertThrows(IllegalArgumentException.class, () ->
                fixture.service.execute(
                        new CreateMovementCommand(1L, "CREDIT", BigDecimal.ZERO, "No op")));
    }

    @Test
    void rejectsDebitWhenBalanceIsInsufficient() {
        Fixture fixture = new Fixture();
        fixture.account.setCurrentBalance(BigDecimal.valueOf(100));

        MovementInsufficientBalanceException exception = assertThrows(
                MovementInsufficientBalanceException.class,
                () -> fixture.service.execute(
                        new CreateMovementCommand(1L, "DEBIT", BigDecimal.valueOf(-200), "Rent")));

        assertEquals("Saldo no disponible", exception.getMessage());
    }

    private static final class Fixture {
        final Account account = createAccount();
        final StubAccountReadRepositoryPort readPort = new StubAccountReadRepositoryPort(account);
        final RecordingAccountWriteRepositoryPort writePort = new RecordingAccountWriteRepositoryPort();
        final RecordingMovementWriteRepositoryPort movementWritePort = new RecordingMovementWriteRepositoryPort();
        final RecordingMovementEventWriteRepositoryPort eventWritePort = new RecordingMovementEventWriteRepositoryPort();
        final MovementEventRecorder eventRecorder = new MovementEventRecorder(eventWritePort);
        final RecordingEventPublisher publisher = new RecordingEventPublisher();
        final CreateMovementService service = new CreateMovementService(
                readPort, writePort, movementWritePort, eventRecorder, publisher);

        private static Account createAccount() {
            Account account = new Account();
            account.setAccountId(1L);
            account.setCurrentBalance(BigDecimal.ZERO);
            return account;
        }
    }

    private static final class StubAccountReadRepositoryPort implements AccountReadRepositoryPort {
        private final Account account;

        StubAccountReadRepositoryPort(Account account) {
            this.account = account;
        }

        @Override
        public Optional<Account> findById(Long accountId) {
            return Optional.of(account);
        }

        @Override
        public Optional<Account> findByAccountNumber(String accountNumber) {
            return Optional.of(account);
        }

        @Override
        public List<Account> findByClientId(Long clientId) {
            return List.of(account);
        }
    }

    private static final class RecordingAccountWriteRepositoryPort implements AccountWriteRepositoryPort {
        private Account saved;

        @Override
        public Account save(Account account) {
            this.saved = account;
            return account;
        }
    }

    private static final class RecordingMovementWriteRepositoryPort implements MovementWriteRepositoryPort {
        private Movement saved;
        private long nextId = 1;

        @Override
        public Movement save(Movement movement) {
            if (movement.getMovementId() == null) {
                movement.setMovementId(nextId++);
            }
            this.saved = movement;
            return movement;
        }
    }

    private static final class RecordingMovementEventWriteRepositoryPort implements MovementEventWriteRepositoryPort {
        private MovementEvent saved;

        @Override
        public MovementEvent save(MovementEvent movementEvent) {
            this.saved = movementEvent;
            return movementEvent;
        }
    }

    private static final class RecordingEventPublisher implements EventPublisherPort {
        private DomainEvent published;

        @Override
        public void publish(DomainEvent event) {
            this.published = event;
        }
    }
}
