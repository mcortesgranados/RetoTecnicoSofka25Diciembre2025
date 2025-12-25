package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO used to request a single Person aggregate by its identifier.
 *
 * <p>Acts as the read-side contract inside the hexagonal architecture, keeping controllers
 * decoupled from repository details and letting query handlers focus on data retrieval (SRP).
 */
public class GetPersonQuery {

    private final Long personId;

    /**
     * Constructs the query with the target identifier.
     *
     * @param personId person identifier
     */
    public GetPersonQuery(Long personId) {
        this.personId = personId;
    }

    /**
     * @return requested identifier
     */
    public Long getPersonId() {
        return personId;
    }
}
