package com.mcg.sofka.retotecnicobanco.api.rest.adapter.events;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class KafkaEventPublisherAdapter implements EventPublisherPort {

    @Override
    public void publish(DomainEvent event) {
        // publish to Kafka topic
    }
}
