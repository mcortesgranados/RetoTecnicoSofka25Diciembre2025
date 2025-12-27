package com.mcg.sofka.retotecnicobanco.api.rest.application.service.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateAccountCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Command service that handles the creation of bank accounts.
 */
public class CreateAccountService implements CommandUseCase<CreateAccountCommand, Account> {

    private final ClientReadRepositoryPort clientReadPort;
    private final AccountReadRepositoryPort accountReadPort;
    private final AccountWriteRepositoryPort accountWritePort;

    public CreateAccountService(ClientReadRepositoryPort clientReadPort,
                                AccountReadRepositoryPort accountReadPort,
                                AccountWriteRepositoryPort accountWritePort) {
        this.clientReadPort = clientReadPort;
        this.accountReadPort = accountReadPort;
        this.accountWritePort = accountWritePort;
    }

    @Override
    public Account execute(CreateAccountCommand command) {
        Client client = clientReadPort.findById(command.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        accountReadPort.findByAccountNumber(command.getAccountNumber())
                .ifPresent(account -> {
                    throw new IllegalArgumentException("Account number already exists");
                });

        Account account = new Account();
        account.setAccountNumber(command.getAccountNumber());
        account.setClient(client);
        account.setAccountType(command.getAccountType() == null
                ? Account.AccountType.SAVINGS
                : Account.AccountType.from(command.getAccountType()));

        BigDecimal initialBalance = command.getInitialBalance() == null
                ? BigDecimal.ZERO
                : command.getInitialBalance();
        account.setInitialBalance(initialBalance);
        account.setCurrentBalance(initialBalance);
        account.setActive(command.getActive() == null ? Boolean.TRUE : command.getActive());

        OffsetDateTime now = OffsetDateTime.now();
        account.setCreatedAt(now);
        account.setUpdatedAt(now);

        return accountWritePort.save(account);
    }
}
