package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

import java.time.OffsetDateTime;

/**
 * Query DTO used to request an account statement report for a client inside a date window.
 */
public record AccountStatementReportQuery(Long clientId,
                                         OffsetDateTime from,
                                         OffsetDateTime to) {
}
