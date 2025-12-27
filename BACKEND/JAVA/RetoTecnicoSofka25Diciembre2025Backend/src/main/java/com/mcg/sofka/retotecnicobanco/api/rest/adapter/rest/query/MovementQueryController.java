package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListMovementByAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
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
@RequestMapping("/movement/query")
@Tag(name = "Movement Query", description = "Read endpoints for account movements")
public class MovementQueryController {

    private final QueryUseCase<GetMovementQuery, Optional<Movement>> getUseCase;
    private final QueryUseCase<ListMovementByAccountQuery, List<Movement>> listUseCase;

    public MovementQueryController(QueryUseCase<GetMovementQuery, Optional<Movement>> getUseCase,
                                   QueryUseCase<ListMovementByAccountQuery, List<Movement>> listUseCase) {
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
    }

    @Operation(summary = "Get movement by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movement found"),
            @ApiResponse(responseCode = "404", description = "Movement not found")
    })
    @GetMapping("/{movementId}")
    public ResponseEntity<EntityModel<Movement>> get(@PathVariable("movementId") Long movementId) {
        return getUseCase.execute(new GetMovementQuery(movementId))
                .map(movement -> {
                    EntityModel<Movement> resource = EntityModel.of(movement);
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementQueryController.class)
                                    .get(movement.getMovementId()))
                            .withSelfRel();
                    resource.add(selfLink);
                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MovementQueryController.class)
                                    .listByAccount(movement.getAccount().getAccountId()))
                            .withRel("by-account"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "List movements by account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movements returned")
    })
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Movement>> listByAccount(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok(listUseCase.execute(new ListMovementByAccountQuery(accountId)));
    }
}
