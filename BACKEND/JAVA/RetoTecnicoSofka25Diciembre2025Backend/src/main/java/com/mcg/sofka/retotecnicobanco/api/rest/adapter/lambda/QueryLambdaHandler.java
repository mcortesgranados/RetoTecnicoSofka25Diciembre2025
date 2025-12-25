package com.mcg.sofka.retotecnicobanco.api.rest.adapter.lambda;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetPersonQuery;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import java.util.Map;
import java.util.Optional;

public class QueryLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, Map<String, Object>> {

    private final QueryUseCase<GetPersonQuery, Optional<?>> getUseCase;

    public QueryLambdaHandler(QueryUseCase<GetPersonQuery, Optional<?>> getUseCase) {
        this.getUseCase = getUseCase;
    }

    @Override
    public Map<String, Object> handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Optional<?> person = getUseCase.execute(new GetPersonQuery(0L));
        return Map.of("result", person.orElse(null));
    }
}
