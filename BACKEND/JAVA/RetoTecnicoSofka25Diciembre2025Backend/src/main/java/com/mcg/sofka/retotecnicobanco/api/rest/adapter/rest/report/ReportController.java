package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.report;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.AccountStatementReportQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report.AccountStatementReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Exposes reporting endpoints such as the account statement view filtered by client and date range.
 */
@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "Genera reportes financieros como el estado de cuenta")
public class ReportController {

    private static final ZoneOffset UTC_OFFSET = ZoneOffset.UTC;
    private final QueryUseCase<AccountStatementReportQuery, AccountStatementReport> reportUseCase;

    public ReportController(QueryUseCase<AccountStatementReportQuery, AccountStatementReport> reportUseCase) {
        this.reportUseCase = reportUseCase;
    }

    @Operation(summary = "Estado de cuenta", description = "Entrega cuentas y movimientos de un cliente dentro de un rango de fechas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping
    public ResponseEntity<AccountStatementReport> report(
            @RequestParam("fecha") String fechaRange,
            @RequestParam("clienteId") Long clientId) {
        OffsetDateTime[] range = parseFechaRange(fechaRange);
        AccountStatementReport report = reportUseCase.execute(new AccountStatementReportQuery(clientId, range[0], range[1]));
        return ResponseEntity.ok(report);
    }

    private OffsetDateTime[] parseFechaRange(String fechaRange) {
        if (fechaRange == null || fechaRange.isBlank()) {
            throw new IllegalArgumentException("El parametro 'fecha' es obligatorio");
        }

        String[] parts = fechaRange.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("El rango debe proveerse como 'fechaInicio,fechaFin'");
        }

        LocalDate inicio = LocalDate.parse(parts[0].trim());
        LocalDate fin = LocalDate.parse(parts[1].trim());
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha final debe ser posterior o igual a la inicial");
        }

        OffsetDateTime from = inicio.atStartOfDay().atOffset(UTC_OFFSET);
        OffsetDateTime to = fin.atTime(LocalTime.MAX).atOffset(UTC_OFFSET);
        return new OffsetDateTime[]{from, to};
    }
}
