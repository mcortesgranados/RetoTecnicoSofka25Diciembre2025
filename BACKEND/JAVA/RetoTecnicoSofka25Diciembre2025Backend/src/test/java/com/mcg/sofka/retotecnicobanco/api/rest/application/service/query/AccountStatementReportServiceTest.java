package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.AccountStatementReportQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report.AccountStatementReport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountStatementReportServiceTest {

    @Test
    void includesOnlyMovementsWithinTheRequestedRange() {
        Account account = createAccount();
        Movement inside = createMovement(1L, "2025-01-10T10:00:00Z", Movement.MovementType.CREDIT, BigDecimal.valueOf(300));
        Movement outside = createMovement(2L, "2025-02-01T10:00:00Z", Movement.MovementType.DEBIT, BigDecimal.valueOf(-50));

        MovementReadRepositoryPort movementReadPort = new StubMovementReadRepositoryPort(Map.of(
                account.getAccountId(), List.of(inside, outside)
        ));

        AccountStatementReportService service = new AccountStatementReportService(
                new StubAccountReadRepositoryPort(List.of(account)),
                movementReadPort
        );

        OffsetDateTime from = OffsetDateTime.parse("2025-01-01T00:00:00Z");
        OffsetDateTime to = OffsetDateTime.parse("2025-01-31T23:59:59Z");
        AccountStatementReport report = service.execute(new AccountStatementReportQuery(1L, from, to));

        assertEquals(1, report.accounts().size());
        assertEquals(1, report.accounts().get(0).movements().size());
        assertEquals(inside.getAmount(), report.accounts().get(0).movements().get(0).amount());
    }

    @Test
    void throwsWhenClientHasNoAccounts() {
        AccountStatementReportService service = new AccountStatementReportService(
                new StubAccountReadRepositoryPort(List.of()),
                new StubMovementReadRepositoryPort(Map.of())
        );

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        assertThrows(IllegalArgumentException.class, () ->
                service.execute(new AccountStatementReportQuery(99L, now.minusDays(1), now)));
    }

    private static Account createAccount() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("ACC-1");
        account.setCurrentBalance(BigDecimal.valueOf(500));
        account.setAccountType(Account.AccountType.SAVINGS);
        Client client = new Client();
        client.setClientId(1L);
        client.setClientCode("CLI001");
        Person person = new Person();
        person.setFirstName("Ana");
        person.setLastName("Torres");
        client.setPerson(person);
        account.setClient(client);
        return account;
    }

    private static Movement createMovement(Long id, String isoDate, Movement.MovementType type, BigDecimal amount) {
        Movement movement = new Movement();
        movement.setMovementId(id);
        movement.setMovementDate(OffsetDateTime.parse(isoDate));
        movement.setMovementType(type);
        movement.setAmount(amount);
        movement.setBalanceAfter(BigDecimal.ZERO);
        movement.setDescription("Desc");
        movement.setCreatedAt(OffsetDateTime.parse(isoDate));
        return movement;
    }

    private static final class StubAccountReadRepositoryPort implements AccountReadRepositoryPort {
        private final List<Account> accounts;

        StubAccountReadRepositoryPort(List<Account> accounts) {
            this.accounts = accounts;
        }

        @Override
        public Optional<Account> findById(Long accountId) {
            return accounts.stream().filter(account -> account.getAccountId().equals(accountId)).findFirst();
        }

        @Override
        public Optional<Account> findByAccountNumber(String accountNumber) {
            return accounts.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst();
        }

        @Override
        public List<Account> findByClientId(Long clientId) {
            return accounts.stream()
                    .filter(account -> account.getClient().getClientId().equals(clientId))
                    .toList();
        }
    }

    private static final class StubMovementReadRepositoryPort implements MovementReadRepositoryPort {
        private final Map<Long, List<Movement>> movementsByAccount;

        StubMovementReadRepositoryPort(Map<Long, List<Movement>> movementsByAccount) {
            this.movementsByAccount = new HashMap<>(movementsByAccount);
        }

        @Override
        public Optional<Movement> findById(Long movementId) {
            return movementsByAccount.values().stream()
                    .flatMap(List::stream)
                    .filter(movement -> movement.getMovementId().equals(movementId))
                    .findFirst();
        }

        @Override
        public List<Movement> findByAccountId(Long accountId) {
            return movementsByAccount.getOrDefault(accountId, List.of());
        }
    }
}
