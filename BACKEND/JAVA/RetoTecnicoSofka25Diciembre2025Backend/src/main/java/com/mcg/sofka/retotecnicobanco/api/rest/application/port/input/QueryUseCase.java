package com.mcg.sofka.retotecnicobanco.api.rest.application.port.input;

/**
 * Abstraction for query-oriented use cases inside the core.
 *
 * <p>Query use cases are read-only and return data without causing side effects. Placing this interface
 * in the application layer keeps the adapter side (controllers, lambdas) free from implementation details.
 *
 * @param <Q> query DTO type
 * @param <R> result type
 */
public interface QueryUseCase<Q, R> {

    /**
     * Executes the query by orchestrating the domain and persistence layers.
     *
     * @param query DTO encapsulating read criteria
     * @return result data to be returned to adapters
     */
    R execute(Q query);
}
