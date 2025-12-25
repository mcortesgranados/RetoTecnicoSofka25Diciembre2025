package com.mcg.sofka.retotecnicobanco.api.rest.adapter.events;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;
import org.springframework.stereotype.Component;

@Component
public class SnsEventPublisherAdapter implements EventPublisherPort {

    @Override
    public void publish(DomainEvent event) {
        // publish to SNS topic
    }
}
