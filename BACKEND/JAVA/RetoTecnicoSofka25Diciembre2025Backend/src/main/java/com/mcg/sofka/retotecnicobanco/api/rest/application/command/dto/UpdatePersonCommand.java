package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

/**
 * Command DTO carrying the fields required to update a Person aggregate.
 *
 * <p>It enforces immutability and functions as the contract between adapters and the command service,
 * preventing mutations outside the core and ensuring the command processor receives all needed values
 * in a single object (SRP/DIP).
 */
public class UpdatePersonCommand {

    private final Long personId;
    private final String address;
    private final String phone;

    /**
     * Creates the update command.
     *
     * @param personId identifier of the person to update
     * @param address  new address
     * @param phone    new phone number
     */
    public UpdatePersonCommand(Long personId, String address, String phone) {
        this.personId = personId;
        this.address = address;
        this.phone = phone;
    }

    /**
     * @return identifier to locate the person
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * @return new address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return new phone number
     */
    public String getPhone() {
        return phone;
    }
}
