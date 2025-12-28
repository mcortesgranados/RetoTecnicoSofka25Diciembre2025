package com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Report payload returned to clients that aggregates overall account data for a client.
 */
public record AccountStatementReport(
        Long clientId,
        String clientCode,
        String clientName,
        OffsetDateTime from,
        OffsetDateTime to,
        List<AccountReportDetail> accounts) {
}
