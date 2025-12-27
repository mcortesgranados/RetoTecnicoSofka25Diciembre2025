package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateMovementCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.MovementQueryController;
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
@RequestMapping("/movement/command")
@Tag(name = "Movement Command", description = "Register account transactions")
public class MovementCommandController {

    private final CommandUseCase<CreateMovementCommand, Movement> createUseCase;

    public MovementCommandController(CommandUseCase<CreateMovementCommand, Movement> createUseCase) {
        this.createUseCase = createUseCase;
    }

    @Operation(summary = "Record a movement", description = "Creates a debit or credit movement and updates the account balance.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movement recorded"),
            @ApiResponse(responseCode = "400", description = "Validation failure", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Movement>> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Movement payload",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateMovementCommand.class),
                            examples = @ExampleObject(
                                    name = "Record Movement",
                                    summary = "Credit deposit",
                                    value = "{\n"
                                            + "  \"accountId\": 1,\n"
                                            + "  \"movementType\": \"CREDIT\",\n"
                                            + "  \"amount\": 500.00,\n"
                                            + "  \"description\": \"Salary deposit\"\n"
                                            + "}"
                            )
                    ))
            CreateMovementCommand command) {

        Movement saved = createUseCase.execute(command);
        EntityModel<Movement> resource = EntityModel.of(saved);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementQueryController.class)
                        .get(saved.getMovementId()))
                .withSelfRel();
        resource.add(selfLink);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementQueryController.class)
                        .listByAccount(saved.getAccount().getAccountId()))
                .withRel("by-account"));

        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }
}
