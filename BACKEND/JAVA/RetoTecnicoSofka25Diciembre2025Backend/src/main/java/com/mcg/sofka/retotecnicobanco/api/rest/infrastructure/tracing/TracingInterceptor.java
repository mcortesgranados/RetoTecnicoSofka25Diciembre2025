package com.mcg.sofka.retotecnicobanco.api.rest.infrastructure.tracing;

/**
 * Adapter helper responsible for propagating distributed tracing context in the infrastructure layer.
 *
 * <p>By keeping tracing support here, we avoid leaking observability concerns into the application core,
 * aligning with SRP and maintaining a clean hexagonal boundary. Additional methods can be added without
 * changing business services (OCP) while controllers or interceptors call this helper as needed.
 */
public class TracingInterceptor {

    /**
     * Placeholder marker for distributed-tracing integration.
     *
     * <p>In a realistic implementation, this class would provide APIs to continue trace/span context across
     * async boundaries before delegating to monitoring systems, satisfying DIP by letting tracing mechanisms
     * depend on this helper rather than concrete infrastructure clients.
     */
    // Provide distributed tracing context propagation helpers
}
