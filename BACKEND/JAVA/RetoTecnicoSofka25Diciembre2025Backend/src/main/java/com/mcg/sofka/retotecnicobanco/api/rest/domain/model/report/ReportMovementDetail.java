package com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Represents a single movement entry inside the account statement report.
 */
public record ReportMovementDetail(
        OffsetDateTime movementDate,
        Movement.MovementType movementType,
        BigDecimal amount,
        BigDecimal balanceAfter,
        String description) {
}
