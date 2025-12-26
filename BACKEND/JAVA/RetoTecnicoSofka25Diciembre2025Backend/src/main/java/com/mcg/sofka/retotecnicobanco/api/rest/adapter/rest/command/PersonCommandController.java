package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.PersonQueryController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person/command")
@Tag(name = "Person Command", description = "Command operations for managing Person aggregates")
public class PersonCommandController {

    private final CommandUseCase<CreatePersonCommand, Person> createUseCase;

    public PersonCommandController(CommandUseCase<CreatePersonCommand, Person> createUseCase) {
        this.createUseCase = createUseCase;
    }

    @Operation(
            summary = "Create a person command",
            description = "Accepts a command that captures the minimum information to create a Person aggregate in the core domain.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Person command executed and created successfully"),
            @ApiResponse(responseCode = "400", description = "Payload validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error during person creation", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Person>> create(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "JSON body that describes a new Person",
                    content = @Content(
                            schema = @Schema(implementation = CreatePersonCommand.class),
                            examples = @ExampleObject(
                                    name = "Create Person",
                                    summary = "Realistic example payload",
                                    value = "{\n"
                                            + "  \"firstName\": \"Isabella\",\n"
                                            + "  \"lastName\": \"González\",\n"
                                            + "  \"gender\": \"FEMALE\",\n"
                                            + "  \"age\": 34,\n"
                                            + "  \"identification\": \"0102030405\",\n"
                                            + "  \"address\": \"Av. Amazonas 1234\",\n"
                                            + "  \"phone\": \"0987654321\"\n"
                                            + "}"
                            )
                    ))
            CreatePersonCommand command) {
        Person saved = createUseCase.execute(command);
        EntityModel<Person> resource = EntityModel.of(saved);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonQueryController.class)
                .get(saved.getPersonId()))
                .withSelfRel();
        resource.add(selfLink);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonQueryController.class)
                .list(saved.getIdentification()))
                .withRel("by-identification"));

        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }
}
