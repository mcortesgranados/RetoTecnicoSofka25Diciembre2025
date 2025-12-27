package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/client/query")
@Tag(name = "Client Query", description = "Query operations for client records")
public class ClientQueryController {

    private final QueryUseCase<GetClientQuery, Optional<Client>> queryUseCase;

    public ClientQueryController(QueryUseCase<GetClientQuery, Optional<Client>> queryUseCase) {
        this.queryUseCase = queryUseCase;
    }

    @Operation(summary = "Retrieve a client by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{clientId}")
    public ResponseEntity<EntityModel<Client>> get(@PathVariable("clientId") Long clientId) {
        Optional<Client> client = queryUseCase.execute(new GetClientQuery(clientId));
        return client.map(value -> {
                    EntityModel<Client> resource = EntityModel.of(value);
                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientQueryController.class)
                            .get(value.getClientId())).withRel(IanaLinkRelations.SELF));
                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonQueryController.class)
                            .get(value.getPerson().getPersonId())).withRel("person"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
