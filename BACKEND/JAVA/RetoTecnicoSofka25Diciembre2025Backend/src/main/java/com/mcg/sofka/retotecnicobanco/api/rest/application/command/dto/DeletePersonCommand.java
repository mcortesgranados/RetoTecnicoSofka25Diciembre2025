package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

/**
 * Command DTO that encapsulates the identifier of the Person aggregate which should be removed.
 *
 * <p>It lives at the edge of the application core and keeps the adapter-to-core boundary explicit,
 * supporting SOLID principles by forcing concrete delete operations to go through dedicated services
 * that depend on abstractions rather than actual repositories.
 */
public class DeletePersonCommand {

    private final Long personId;

    /**
     * Constructs the command with the required identifier.
     *
     * @param personId identifier of the person to delete
     */
    public DeletePersonCommand(Long personId) {
        this.personId = personId;
    }

    /**
     * @return identifier of the target person
     */
    public Long getPersonId() {
        return personId;
    }
}
