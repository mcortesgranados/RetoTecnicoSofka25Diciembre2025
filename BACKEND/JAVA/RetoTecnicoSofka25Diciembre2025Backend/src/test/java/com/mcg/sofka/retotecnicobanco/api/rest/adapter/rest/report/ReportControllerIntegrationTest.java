package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.report;

import com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write.AccountWriteJpaRepository;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write.ClientWriteJpaRepository;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write.MovementWriteJpaRepository;
import com.mcg.sofka.retotecnicobanco.api.rest.adapter.persistence.write.PersonWriteJpaRepository;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.sofka-datasource.url=jdbc:h2:mem:reportdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.sofka-datasource.username=sa",
        "spring.sofka-datasource.password=",
        "spring.sofka-datasource.driver-class-name=org.h2.Driver"
})
@Transactional
class ReportControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private PersonWriteJpaRepository personRepo;
    @Autowired
    private ClientWriteJpaRepository clientRepo;
    @Autowired
    private AccountWriteJpaRepository accountRepo;
    @Autowired
    private MovementWriteJpaRepository movementRepo;

    private Client client;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Person person = new Person();
        person.setFirstName("Marco");
        person.setLastName("Ramos");
        person.setGender(Person.Gender.MALE);
        person.setAge(30);
        person.setIdentification("ABC123");
        person.setAddress("123 Main");
        person.setPhone("0999999999");
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        person.setCreatedAt(now);
        person.setUpdatedAt(now);
        person = personRepo.save(person);

        client = new Client();
        client.setPerson(person);
        client.setClientCode("CLI100");
        client.setPasswordHash("secret");
        client.setRegisteredAt(now);
        client = clientRepo.save(client);

        Account account = new Account();
        account.setAccountNumber("ACCT-001");
        account.setAccountType(Account.AccountType.SAVINGS);
        account.setClient(client);
        account.setInitialBalance(BigDecimal.valueOf(500));
        account.setCurrentBalance(BigDecimal.valueOf(800));
        account.setCreatedAt(now);
        account.setUpdatedAt(now);
        accountRepo.save(account);

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setMovementType(Movement.MovementType.CREDIT);
        movement.setAmount(BigDecimal.valueOf(300));
        movement.setBalanceAfter(BigDecimal.valueOf(800));
        movement.setDescription("Deposit");
        movement.setMovementDate(now.minusDays(1));
        movement.setCreatedAt(now.minusDays(1));
        movementRepo.save(movement);
    }

    @Test
    void reportEndpointReturnsJsonAndMovements() throws Exception {
        String fecha = "2025-01-01,2030-01-01";
        mockMvc.perform(get("/reportes")
                        .param("fecha", fecha)
                        .param("clienteId", client.getClientId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(client.getClientId()))
                .andExpect(jsonPath("$.accounts[0].accountNumber").value("ACCT-001"))
                .andExpect(jsonPath("$.accounts[0].movements[0].movementType").value("CREDIT"))
                .andExpect(jsonPath("$.accounts[0].movements[0].amount").value(300));
    }
}
