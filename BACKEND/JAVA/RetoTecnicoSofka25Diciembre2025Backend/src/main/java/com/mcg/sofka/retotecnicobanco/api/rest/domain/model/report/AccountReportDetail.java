package com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report;

import java.math.BigDecimal;
import java.util.List;

/**
 * Captures a single account with its balance and filtered movements.
 */
public record AccountReportDetail(
        String accountNumber,
        String accountType,
        BigDecimal currentBalance,
        List<ReportMovementDetail> movements) {
}
