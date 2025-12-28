package com.mcg.sofka.retotecnicobanco.api.rest.application.service.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.AccountStatementReportQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report.AccountReportDetail;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report.AccountStatementReport;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report.ReportMovementDetail;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Builds account statement reports by aggregating accounts and their movements for the requested client.
 */
@Transactional(readOnly = true)
public class AccountStatementReportService implements QueryUseCase<AccountStatementReportQuery, AccountStatementReport> {

    private final AccountReadRepositoryPort accountReadPort;
    private final MovementReadRepositoryPort movementReadPort;

    public AccountStatementReportService(AccountReadRepositoryPort accountReadPort,
                                         MovementReadRepositoryPort movementReadPort) {
        this.accountReadPort = accountReadPort;
        this.movementReadPort = movementReadPort;
    }

    @Override
    public AccountStatementReport execute(AccountStatementReportQuery query) {
        List<Account> accounts = accountReadPort.findByClientId(query.clientId());
        if (accounts.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado: " + query.clientId());
        }

        Client client = accounts.get(0).getClient();
        Person person = client.getPerson();
        String clientCode = client.getClientCode();
        String clientName = person.getFirstName() + " " + person.getLastName();

        List<AccountReportDetail> accountDetails = accounts.stream()
                .map(account -> buildAccountDetail(account, query))
                .toList();

        return new AccountStatementReport(
                client.getClientId(),
                clientCode,
                clientName,
                query.from(),
                query.to(),
                accountDetails
        );
    }

    private AccountReportDetail buildAccountDetail(Account account, AccountStatementReportQuery query) {
        List<ReportMovementDetail> movements = movementReadPort.findByAccountId(account.getAccountId()).stream()
                .filter(movement -> isWithinRange(movement.getMovementDate(), query.from(), query.to()))
                .map(movement -> new ReportMovementDetail(
                        movement.getMovementDate(),
                        movement.getMovementType(),
                        movement.getAmount(),
                        movement.getBalanceAfter(),
                        movement.getDescription()
                ))
                .toList();

        String accountType = account.getAccountType() != null ? account.getAccountType().name() : null;
        return new AccountReportDetail(
                account.getAccountNumber(),
                accountType,
                account.getCurrentBalance(),
                movements
        );
    }

    private boolean isWithinRange(OffsetDateTime movementDate, OffsetDateTime from, OffsetDateTime to) {
        if (movementDate == null || from == null || to == null) {
            return false;
        }
        return !movementDate.isBefore(from) && !movementDate.isAfter(to);
    }
}
