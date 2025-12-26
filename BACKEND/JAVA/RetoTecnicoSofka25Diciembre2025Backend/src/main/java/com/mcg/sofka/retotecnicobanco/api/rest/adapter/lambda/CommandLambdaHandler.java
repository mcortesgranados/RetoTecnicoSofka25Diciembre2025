package com.mcg.sofka.retotecnicobanco.api.rest.adapter.lambda;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import java.util.Map;

public class CommandLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, Map<String, Object>> {

    private final CommandUseCase<CreatePersonCommand, Person> createUseCase;

    public CommandLambdaHandler(CommandUseCase<CreatePersonCommand, Person> createUseCase) {
        this.createUseCase = createUseCase;
    }

    @Override
    public Map<String, Object> handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        // Parse body, map to CreatePersonCommand, delegate
        CreatePersonCommand command = new CreatePersonCommand(
                "",
                "",
                "OTHER",
                0,
                "",
                "",
                ""
        );
        Person saved = createUseCase.execute(command);
        return Map.of(
                "status", "accepted",
                "personId", saved.getPersonId()
        );
    }
}
