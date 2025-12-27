package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query;

import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.dto.AccountStatementResponse;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListAccountStatementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.AccountStatementEntry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account/statement/query")
@Tag(name = "Account Statement Query", description = "Read-only endpoints for account statements")
public class AccountStatementQueryController {

    private final QueryUseCase<ListAccountStatementQuery, List<AccountStatementEntry>> listUseCase;

    public AccountStatementQueryController(
            QueryUseCase<ListAccountStatementQuery, List<AccountStatementEntry>> listUseCase) {
        this.listUseCase = listUseCase;
    }

    @Operation(summary = "Read the statement rows for an account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statement returned"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountStatementResponse>> get(@PathVariable("accountId") Long accountId) {
        try {
            List<AccountStatementEntry> entries = listUseCase.execute(new ListAccountStatementQuery(accountId));
            List<AccountStatementResponse> responses = entries.stream()
                    .map(AccountStatementResponse::from)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
