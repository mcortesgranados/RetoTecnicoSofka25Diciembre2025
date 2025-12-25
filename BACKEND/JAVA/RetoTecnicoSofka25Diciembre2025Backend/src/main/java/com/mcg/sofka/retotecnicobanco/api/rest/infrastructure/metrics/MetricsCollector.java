package com.mcg.sofka.retotecnicobanco.api.rest.infrastructure.metrics;

/**
 * Simple infrastructure adapter that exposes a hook where the rest adapters can emit metrics.
 *
 * <p>This class lives in the adapter/infrastructure layer of the hexagon, keeping observability concerns
 * separate from the application core (SRP). Metric events can be published without changing business logic,
 * supporting the Open/Closed Principle by allowing more payloads or destinations via configuration.
 */
public class MetricsCollector {

    /**
     * Records a metric by key.
     *
     * <p>Adapters can invoke this method as part of the event-driven lifecycle to capture timings,
     * counts, or other KPIs. Integrations with systems such as Prometheus or CloudWatch happen
     * here so services stay focused on domain behavior (DIP).
     *
     * @param key   metric key (e.g., latency.bandwidth)
     * @param value value to record
     */
    public void record(String key, double value) {
        // hook into metric system
    }
}
