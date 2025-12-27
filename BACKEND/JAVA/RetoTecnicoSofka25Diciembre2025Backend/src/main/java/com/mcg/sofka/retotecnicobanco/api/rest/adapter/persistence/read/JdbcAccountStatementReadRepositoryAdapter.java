package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountStatementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.AccountStatementEntry;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;

/**
 * JDBC adapter that reads ledger rows from the {@code account_statement} view.
 */
@Component
public class JdbcAccountStatementReadRepositoryAdapter implements AccountStatementReadRepositoryPort {

    private static final String STATEMENT_QUERY = """
            SELECT
                client_code,
                first_name,
                last_name,
                account_number,
                account_type,
                current_balance,
                movement_date,
                movement_type,
                movement_amount,
                balance_after,
                description
            FROM account_statement
            WHERE account_number = ?
            ORDER BY movement_date DESC
            """;

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountStatementReadRepositoryAdapter(@Qualifier("sofkaJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AccountStatementEntry> findByAccountNumber(String accountNumber) {
        return jdbcTemplate.query(STATEMENT_QUERY, this::mapEntry, accountNumber);
    }

    private AccountStatementEntry mapEntry(ResultSet rs, int rowNum) throws SQLException {
        Timestamp movementTs = rs.getTimestamp("movement_date");
        OffsetDateTime movementDate = movementTs != null ? movementTs.toInstant().atOffset(ZoneOffset.UTC) : null;
        String type = rs.getString("movement_type");
        Movement.MovementType movementType = type != null ? Movement.MovementType.valueOf(type.toUpperCase(Locale.ROOT)) : null;
        BigDecimal currentBalance = rs.getBigDecimal("current_balance");
        BigDecimal movementAmount = rs.getBigDecimal("movement_amount");
        BigDecimal balanceAfter = rs.getBigDecimal("balance_after");

        return new AccountStatementEntry(
                rs.getString("client_code"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("account_number"),
                rs.getString("account_type"),
                currentBalance,
                movementDate,
                movementType,
                movementAmount,
                balanceAfter,
                rs.getString("description")
        );
    }
}
