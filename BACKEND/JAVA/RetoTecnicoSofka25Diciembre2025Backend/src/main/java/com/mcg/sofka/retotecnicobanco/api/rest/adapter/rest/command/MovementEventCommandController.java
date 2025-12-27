package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command;

import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command.dto.MovementEventPublishRequest;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.MovementEventQueryController;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.dto.MovementEventResponse;
import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.PublishMovementEventCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movement/event/command")
@Tag(name = "Movement Event Command", description = "Operations that publish movement events downstream")
public class MovementEventCommandController {

    private final CommandUseCase<PublishMovementEventCommand, MovementEvent> publishUseCase;

    public MovementEventCommandController(CommandUseCase<PublishMovementEventCommand, MovementEvent> publishUseCase) {
        this.publishUseCase = publishUseCase;
    }

    @Operation(summary = "Mark a movement event as published")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event marked as published"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PostMapping("/{eventId}/publish")
    public ResponseEntity<EntityModel<MovementEventResponse>> publish(
            @PathVariable("eventId") Long eventId,
            @RequestBody(required = false) MovementEventPublishRequest request) {
        try {
            PublishMovementEventCommand command = new PublishMovementEventCommand(
                    eventId,
                    request != null ? request.getRoutedAt() : null
            );
            MovementEvent updated = publishUseCase.execute(command);
            MovementEventResponse response = MovementEventResponse.from(updated);
            EntityModel<MovementEventResponse> resource = EntityModel.of(response);
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementEventQueryController.class)
                            .get(response.getEventId()))
                    .withSelfRel();
            resource.add(selfLink);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementEventQueryController.class)
                            .pending())
                    .withRel("pending"));
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
