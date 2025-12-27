package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountWriteJpaRepository extends JpaRepository<Account, Long> {
}
