package com.mcg.sofka.retotecnicobanco.api.rest.domain.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest {

    @Test
    void settersPopulateFieldsAndActiveDefaultsTrue() {
        Client client = new Client();
        client.setClientCode("CLI0001");
        client.setPasswordHash("hashed");
        client.setRegisteredAt(OffsetDateTime.now(ZoneOffset.UTC));

        Person person = new Person();
        person.setFirstName("Lina");
        person.setLastName("Lopez");
        client.setPerson(person);

        assertTrue(client.getActive());
        assertEquals("CLI0001", client.getClientCode());
        assertEquals("hashed", client.getPasswordHash());
        assertEquals(Boolean.TRUE, client.getActive());

        client.setActive(Boolean.FALSE);
        assertEquals(Boolean.FALSE, client.getActive());
        assertEquals(person, client.getPerson());
    }
}
