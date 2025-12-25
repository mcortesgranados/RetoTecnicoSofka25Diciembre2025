package com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto;

/**
 * Command DTO that carries the values needed to create a new Person aggregate.
 *
 * <p>Commands reside in the application layer of the hexagon and prioritize immutability so that
 * controllers or lambda handlers can deliver data into the core without exposing setters (Liskov and
 * Interface Segregation). The constructor enforces the complete state needed for creation, and the
 * getters present only the read access that services such as {@link com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreatePersonService}
 * require.
 */
public class CreatePersonCommand {

    private final String firstName;
    private final String lastName;
    private final String gender;
    private final Integer age;
    private final String identification;
    private final String address;
    private final String phone;

    /**
     * Constructs the command with all mandatory fields required to instantiate a Person.
     *
     * <p>This constructor ensures the command is always valid when handed to the command service,
     * supporting the Single Responsibility Principle by keeping validation logic in dedicated services.
     *
     * @param firstName      person first name
     * @param lastName       person last name
     * @param gender         person gender (enum value name)
     * @param age            person age
     * @param identification unique identification value
     * @param address        postal address
     * @param phone          contact phone
     */
    public CreatePersonCommand(String firstName, String lastName, String gender, Integer age, String identification, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.identification = identification;
        this.address = address;
        this.phone = phone;
    }

    /**
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return gender enum name expected by the domain model
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @return identification that uniquely describes the person
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
}
