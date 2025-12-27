package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaAccountReadRepositoryAdapter implements AccountReadRepositoryPort {

    private final AccountReadJpaRepository repository;

    public JpaAccountReadRepositoryAdapter(AccountReadJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return repository.findById(accountId);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findByClientId(Long clientId) {
        return repository.findByClient_ClientId(clientId);
    }
}
