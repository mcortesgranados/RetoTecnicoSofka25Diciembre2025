package com.mcg.sofka.retotecnicobanco.api.rest.adapter.events;

import com.mcg.sofka.retotecnicobanco.api.rest.domain.event.DomainEvent;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Kafka adapter can be invoked through the event-driven port without side effects.
 *
 * <p>The test sits in the adapter layer and documents the expectation that {@link KafkaEventPublisherAdapter}
 * fulfills the {@link com.mcg.sofka.retotecnicobanco.api.rest.application.port.output.EventPublisherPort} contract
 * with minimal context, preserving SOLID (SRP enables the test to only check the adapter, DIP keeps it
 * independent from the rest of the stack) and the hexagonal boundary between tests and infrastructure.
 */
class KafkaEventPublisherAdapterTest {

    /**
     * Confirms that publishing a dummy {@link DomainEvent} does not throw so that controllers can safely
     * rely on the adapter in the event-driven architecture.
     */
    @Test
    void publishShouldNotThrow() {
        KafkaEventPublisherAdapter adapter = new KafkaEventPublisherAdapter();
        adapter.publish(new DomainEvent() {
            @Override
            public String eventName() {
                return "dummy";
            }
        });
    }
}
