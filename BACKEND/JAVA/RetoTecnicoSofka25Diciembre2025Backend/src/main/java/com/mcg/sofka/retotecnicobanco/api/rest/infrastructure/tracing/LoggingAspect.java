package com.mcg.sofka.retotecnicobanco.api.rest.infrastructure.tracing;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect-based tracer that records method entry points for REST adapters.
 *
 * <p>Located in the infrastructure layer, it cross-cuts controllers and adapters without modifying them
 * directly, which upholds the Open/Closed Principle. By binding through AOP, tracing concerns remain
 * separate from core business logic (SRP) while still supporting dependency inversion via Spring proxies.
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Executes before any method inside the REST adapter package to log/tracing entry.
     *
     * <p>This placeholder keeps tracing concerns orthogonal to business flows.
     */
    @Before("execution(* com.mcg.sofka.retotecnicobanco.api.rest..*(..))")
    public void logEntry() {
        // tracing logic placeholder
    }
}
