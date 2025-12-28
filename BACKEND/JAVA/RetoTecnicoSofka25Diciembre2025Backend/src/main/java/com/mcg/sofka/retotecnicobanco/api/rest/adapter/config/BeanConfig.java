package com.mcg.sofka.retotecnicobanco.api.rest.adapter.config;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateAccountCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateClientCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreateMovementCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.PublishMovementEventCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.EventHandlerUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.ClientWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementEventWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.MovementWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.AccountStatementReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementEventQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.AccountStatementReportQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementEventQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetMovementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListAccountByClientQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListAccountStatementQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListMovementByAccountQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPendingMovementEventsQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.EventDrivenService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreateAccountService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreateClientService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreateMovementService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreatePersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.PublishMovementEventService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.event.MovementEventRecorder;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.AccountStatementReportService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.GetAccountService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.GetClientService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.GetMovementService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.GetMovementEventService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.GetPersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.ListAccountByClientService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.ListAccountStatementService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.ListMovementByAccountService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.ListPendingMovementEventsService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.ListPersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Account;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.AccountStatementEntry;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Client;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Movement;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.MovementEvent;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.report.AccountStatementReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

/**
 * Configures application-scoped beans that expose use cases, queries, and event handlers.
 *
 * <p>This configuration class resides in the adapter layer of the hexagon. It follows the
 * Dependency Inversion Principle by wiring concrete adapters (repositories and publishers)
 * into abstraction-based use case services. It keeps its responsibility limited to bean registration (SRP)
 * and keeps downstream components open for extension (OCP) by being declarative.
 */
@Configuration
public class BeanConfig {

    /**
     * Provides the {@link CommandUseCase} implementation for creating people.
     *
     * <p>It depends on repository and publisher ports rather than their implementations (DIP)
     * and delegates creation logic to {@link CreatePersonService}, which encapsulates the event-driven reaction.
     *
     * @param writePort     port used to persist Person entities
     * @param publisherPort port used to publish domain events after persistence
     * @return initialized command use case
     */
    @Bean
    public CommandUseCase<CreatePersonCommand, Person> createPersonUseCase(PersonWriteRepositoryPort writePort,
                                                                   EventPublisherPort publisherPort) {
        return new CreatePersonService(writePort, publisherPort);
    }

    @Bean
    public CommandUseCase<CreateAccountCommand, Account> createAccountUseCase(ClientReadRepositoryPort clientReadPort,
                                                                             AccountReadRepositoryPort accountReadPort,
                                                                             AccountWriteRepositoryPort accountWritePort) {
        return new CreateAccountService(clientReadPort, accountReadPort, accountWritePort);
    }

    @Bean
    public CommandUseCase<CreateMovementCommand, Movement> createMovementUseCase(AccountReadRepositoryPort accountReadPort,
                                                                                AccountWriteRepositoryPort accountWritePort,
                                                                                MovementWriteRepositoryPort movementWritePort,
                                                                                MovementEventRecorder movementEventRecorder,
                                                                                EventPublisherPort publisherPort) {
        return new CreateMovementService(accountReadPort, accountWritePort, movementWritePort, movementEventRecorder, publisherPort);
    }

    @Bean
    public CommandUseCase<PublishMovementEventCommand, MovementEvent> publishMovementEventUseCase(
            MovementEventReadRepositoryPort readPort,
            MovementEventWriteRepositoryPort writePort) {
        return new PublishMovementEventService(readPort, writePort);
    }

    @Bean
    public QueryUseCase<GetAccountQuery, Optional<Account>> getAccountUseCase(AccountReadRepositoryPort readPort) {
        return new GetAccountService(readPort);
    }

    @Bean
    public QueryUseCase<ListAccountByClientQuery, List<Account>> listAccountByClientUseCase(AccountReadRepositoryPort readPort) {
        return new ListAccountByClientService(readPort);
    }

    @Bean
    public QueryUseCase<GetMovementQuery, Optional<Movement>> getMovementUseCase(MovementReadRepositoryPort readPort) {
        return new GetMovementService(readPort);
    }

    @Bean
    public QueryUseCase<ListMovementByAccountQuery, List<Movement>> listMovementByAccountUseCase(MovementReadRepositoryPort readPort) {
        return new ListMovementByAccountService(readPort);
    }

    @Bean
    public QueryUseCase<ListAccountStatementQuery, List<AccountStatementEntry>> listAccountStatementUseCase(
            AccountReadRepositoryPort accountReadPort,
            AccountStatementReadRepositoryPort statementReadPort) {
        return new ListAccountStatementService(accountReadPort, statementReadPort);
    }

    @Bean
    public QueryUseCase<AccountStatementReportQuery, AccountStatementReport> accountStatementReportUseCase(
            AccountReadRepositoryPort accountReadPort,
            MovementReadRepositoryPort movementReadPort) {
        return new AccountStatementReportService(accountReadPort, movementReadPort);
    }

    @Bean
    public QueryUseCase<GetMovementEventQuery, Optional<MovementEvent>> getMovementEventUseCase(MovementEventReadRepositoryPort readPort) {
        return new GetMovementEventService(readPort);
    }

    @Bean
    public QueryUseCase<ListPendingMovementEventsQuery, List<MovementEvent>> listPendingMovementEventsUseCase(MovementEventReadRepositoryPort readPort) {
        return new ListPendingMovementEventsService(readPort);
    }

    @Bean
    public CommandUseCase<CreateClientCommand, Client> createClientUseCase(PersonReadRepositoryPort personReadPort,
                                                                          ClientReadRepositoryPort clientReadPort,
                                                                          ClientWriteRepositoryPort clientWritePort) {
        return new CreateClientService(personReadPort, clientReadPort, clientWritePort);
    }

    /**
     * Provides a query use case that loads a person by identifier.
     *
     * <p>The use case depends on {@link PersonReadRepositoryPort}, abstracting the persistence
     * implementation away from the controller. This aligns with hexagonal architecture because
     * controllers talk to the core through ports rather than the inner repository.
     *
     * @param readPort port for reading person entities
     * @return person retrieval use case
     */
    @Bean
    public QueryUseCase<GetPersonQuery, Optional<Person>> getPersonUseCase(PersonReadRepositoryPort readPort) {
        return new GetPersonService(readPort);
    }

    @Bean
    public QueryUseCase<GetClientQuery, Optional<Client>> getClientUseCase(ClientReadRepositoryPort readPort) {
        return new GetClientService(readPort);
    }

    /**
     * Provides a query use case that lists all persons.
     *
     * <p>This bean adheres to the Open/Closed Principle by letting {@link ListPersonService} implement
     * listing logic independently of how the controller consumes the result.
     *
     * @param readPort port for reading person entities
     * @return listing query use case
     */
    @Bean
    public QueryUseCase<ListPersonQuery, List<Person>> listPersonUseCase(PersonReadRepositoryPort readPort) {
        return new ListPersonService(readPort);
    }

    /**
     * Exposes the event handler that responds to domain events emitted in the application core.
     *
     * <p>This bean wires {@link EventDrivenService}, which can publish events via {@link EventPublisherPort}
     * implementations, keeping event processing in the application layer while adapters (Kafka/SNS) handle transport.
     *
     * @param publisherPort port used to forward domain events to infrastructure
     * @return configured event handler
     */
    @Bean
    public EventHandlerUseCase<DomainEvent> eventHandler(EventPublisherPort publisherPort) {
        return new EventDrivenService()::handle;
    }
}
