package com.mcg.sofka.retotecnicobanco.api.rest.adapter.config;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.EventHandlerUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonReadRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.PersonWriteRepositoryPort;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.EventDrivenService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.command.CreatePersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.GetPersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.application.service.query.ListPersonService;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class BeanConfig {

    @Bean
    public CommandUseCase<CreatePersonCommand> createPersonUseCase(PersonWriteRepositoryPort writePort,
                                                                   EventPublisherPort publisherPort) {
        return new CreatePersonService(writePort, publisherPort);
    }

    @Bean
    public QueryUseCase<GetPersonQuery, Optional<Person>> getPersonUseCase(PersonReadRepositoryPort readPort) {
        return new GetPersonService(readPort);
    }

    @Bean
    public QueryUseCase<ListPersonQuery, List<Person>> listPersonUseCase(PersonReadRepositoryPort readPort) {
        return new ListPersonService(readPort);
    }

    @Bean
    public EventHandlerUseCase<DomainEvent> eventHandler(EventPublisherPort publisherPort) {
        return new EventDrivenService()::handle;
    }
}
