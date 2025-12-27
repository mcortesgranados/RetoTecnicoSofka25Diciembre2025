package com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.read;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Ensures the database view used for account statements exists before any queries run.
 */
@Component
public class AccountStatementViewInitializer {

    private static final String DROP_VIEW_SQL = "DROP VIEW IF EXISTS account_statement";

    private static final String CREATE_VIEW_SQL = """
            CREATE VIEW account_statement AS
            SELECT
                c.client_code,
                p.first_name,
                p.last_name,
                a.account_number,
                a.account_type,
                a.current_balance,
                m.movement_date,
                m.movement_type,
                m.amount AS movement_amount,
                m.balance_after,
                m.description
            FROM client c
            JOIN person p ON c.person_id = p.person_id
            JOIN account a ON a.client_id = c.client_id
            LEFT JOIN movement m ON m.account_id = a.account_id
            """;

    private final JdbcTemplate jdbcTemplate;

    public AccountStatementViewInitializer(@Qualifier("sofkaJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ensureViewIsCreated() {
        jdbcTemplate.execute(DROP_VIEW_SQL);
        jdbcTemplate.execute(CREATE_VIEW_SQL);
    }
}
