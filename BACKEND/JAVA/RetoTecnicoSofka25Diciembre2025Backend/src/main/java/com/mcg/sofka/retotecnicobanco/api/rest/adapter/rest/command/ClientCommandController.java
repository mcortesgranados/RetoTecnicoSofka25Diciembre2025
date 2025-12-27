package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command;

import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.ClientQueryController;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.PersonQueryController;
import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateClientCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Client Command", description = "Operations to register clients")
@RequestMapping("/client/command")
public class ClientCommandController {

    private final CommandUseCase<CreateClientCommand, Client> createUseCase;

    public ClientCommandController(CommandUseCase<CreateClientCommand, Client> createUseCase) {
        this.createUseCase = createUseCase;
    }

    @Operation(summary = "Register a new client",
            description = "Registers credentials and status metadata for an existing Person and returns the created client resource.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Creation validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Client>> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "JSON payload that links a Person with client credentials",
                    content = @Content(
                            schema = @Schema(implementation = CreateClientCommand.class),
                            examples = @ExampleObject(
                                    name = "Register Client",
                                    summary = "Payload with client metadata",
                                    value = "{\n"
                                            + "  \"personId\": 1,\n"
                                            + "  \"clientCode\": \"CL000001\",\n"
                                            + "  \"passwordHash\": \"changeme\",\n"
                                            + "  \"active\": true\n"
                                            + "}"
                            )
                    ))
            CreateClientCommand command) {
        Client saved = createUseCase.execute(command);
        EntityModel<Client> entity = EntityModel.of(saved);
        entity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientQueryController.class)
                .get(saved.getClientId())).withSelfRel());
        entity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonQueryController.class)
                .get(saved.getPerson().getPersonId())).withRel("person"));

        return ResponseEntity.created(
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientQueryController.class)
                                .get(saved.getClientId()))
                                .toUri())
                .body(entity);
    }
}
