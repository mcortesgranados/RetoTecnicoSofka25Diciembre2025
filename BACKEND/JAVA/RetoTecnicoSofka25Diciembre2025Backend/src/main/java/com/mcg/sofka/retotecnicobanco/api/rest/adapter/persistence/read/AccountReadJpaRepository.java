package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountReadJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByClient_ClientId(Long clientId);
}
