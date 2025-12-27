package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query;

import com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query.dto.MovementEventResponse;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementEventQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPendingMovementEventsQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movement/event/query")
@Tag(name = "Movement Event Query", description = "Read-only endpoints for movement events")
public class MovementEventQueryController {

    private final QueryUseCase<GetMovementEventQuery, Optional<MovementEvent>> getUseCase;
    private final QueryUseCase<ListPendingMovementEventsQuery, List<MovementEvent>> listUseCase;

    public MovementEventQueryController(QueryUseCase<GetMovementEventQuery, Optional<MovementEvent>> getUseCase,
                                        QueryUseCase<ListPendingMovementEventsQuery, List<MovementEvent>> listUseCase) {
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
    }

    @Operation(summary = "Retrieve a movement event", description = "Loads a stored event by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<EntityModel<MovementEventResponse>> get(@PathVariable("eventId") Long eventId) {
        return getUseCase.execute(new GetMovementEventQuery(eventId))
                .map(MovementEventResponse::from)
                .map(response -> {
                    EntityModel<MovementEventResponse> resource = EntityModel.of(response);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementEventQueryController.class)
                                    .get(response.getEventId()))
                            .withSelfRel();
                    resource.add(selfLink);
                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementEventQueryController.class)
                                    .pending())
                            .withRel("pending"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "List pending events", description = "Returns the events that are still waiting to be published.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pending events returned")
    })
    @GetMapping("/pending")
    public ResponseEntity<List<MovementEventResponse>> pending() {
        List<MovementEvent> events = listUseCase.execute(new ListPendingMovementEventsQuery());
        List<MovementEventResponse> responses = events.stream()
                .map(MovementEventResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
