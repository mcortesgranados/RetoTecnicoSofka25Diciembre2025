package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class JpaAccountWriteRepositoryAdapter implements AccountWriteRepositoryPort {

    private final AccountWriteJpaRepository repository;

    public JpaAccountWriteRepositoryAdapter(AccountWriteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }
}
