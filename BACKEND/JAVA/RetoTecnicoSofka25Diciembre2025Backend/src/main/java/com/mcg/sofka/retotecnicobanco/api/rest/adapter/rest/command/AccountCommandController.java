package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateAccountCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.AccountQueryController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/command")
@Tag(name = "Account Command", description = "Operations to open bank accounts")
public class AccountCommandController {

    private final CommandUseCase<CreateAccountCommand, Account> createUseCase;

    public AccountCommandController(CommandUseCase<CreateAccountCommand, Account> createUseCase) {
        this.createUseCase = createUseCase;
    }

    @Operation(summary = "Open a new account", description = "Allocates a new account for an existing client.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed or client/account number issue", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error while creating account", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Account>> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Payload describing the account to open",
                    content = @Content(
                            schema = @Schema(implementation = CreateAccountCommand.class),
                            examples = @ExampleObject(
                                    name = "Open Account",
                                    summary = "Payload for creating a checking account",
                                    value = "{\n"
                                            + "  \"accountNumber\": \"ACC000123\",\n"
                                            + "  \"clientId\": 1,\n"
                                            + "  \"accountType\": \"CHECKING\",\n"
                                            + "  \"initialBalance\": 1500.00,\n"
                                            + "  \"active\": true\n"
                                            + "}"
                            )
                    ))
            CreateAccountCommand command) {

        Account saved = createUseCase.execute(command);
        EntityModel<Account> resource = EntityModel.of(saved);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountQueryController.class)
                        .get(saved.getAccountId()))
                .withSelfRel();
        resource.add(selfLink);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountQueryController.class)
                        .listByClient(saved.getClient().getClientId()))
                .withRel("by-client"));

        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }
}
