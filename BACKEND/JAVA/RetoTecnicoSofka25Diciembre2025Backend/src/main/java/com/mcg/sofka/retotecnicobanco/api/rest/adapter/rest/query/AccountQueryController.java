package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListAccountByClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account/query")
@Tag(name = "Account Query", description = "Read-only endpoints for accounts")
public class AccountQueryController {

    private final QueryUseCase<GetAccountQuery, Optional<Account>> getUseCase;
    private final QueryUseCase<ListAccountByClientQuery, List<Account>> listUseCase;

    public AccountQueryController(QueryUseCase<GetAccountQuery, Optional<Account>> getUseCase,
                                  QueryUseCase<ListAccountByClientQuery, List<Account>> listUseCase) {
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
    }

    @Operation(summary = "Retrieve account by identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountId}")
    public ResponseEntity<EntityModel<Account>> get(@PathVariable("accountId") Long accountId) {
        return getUseCase.execute(new GetAccountQuery(accountId))
                .map(account -> {
                    EntityModel<Account> resource = EntityModel.of(account);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountQueryController.class)
                                    .get(account.getAccountId()))
                            .withSelfRel();
                    resource.add(selfLink);
                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountQueryController.class)
                                    .listByClient(account.getClient().getClientId()))
                            .withRel("by-client"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "List accounts by client identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accounts returned for client")
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Account>> listByClient(@PathVariable("clientId") Long clientId) {
        List<Account> accounts = listUseCase.execute(new ListAccountByClientQuery(clientId));
        return ResponseEntity.ok(accounts);
    }
}
