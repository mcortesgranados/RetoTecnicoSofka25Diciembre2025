package com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto;

/**
 * Query DTO that communicates filtering criteria for listing persons, typically by identification.
 *
 * <p>This DTO is used inside the application core to keep read-side logic parameterized, promoting
 * reusable query services and adherence to hexagonal architecture principles.
 */
public class ListPersonQuery {

    private final String identification;

    /**
     * Creates the listing query.
     *
     * @param identification identification filter value
     */
    public ListPersonQuery(String identification) {
        this.identification = identification;
    }

    /**
     * @return identification criterion
     */
    public String getIdentification() {
        return identification;
    }
}
