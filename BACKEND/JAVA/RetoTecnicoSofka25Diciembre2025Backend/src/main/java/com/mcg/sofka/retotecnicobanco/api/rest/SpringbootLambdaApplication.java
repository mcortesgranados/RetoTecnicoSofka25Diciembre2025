package com.mcg.sofka.retotecnicobanco.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Alternative Spring Boot launcher used when the application runs inside AWS Lambda.
 *
 * <p>This adapter lives in the infrastructure layer of the hexagon and exists solely
 * to satisfy Lambda’s expectations. It has a single responsibility of wiring the Spring context
 * for that environment (SRP) and depends on abstractions from {@link com.mcg.sofka.retotecnicobanco.RetoTecnicoSofka25Diciembre2025Application}
 * rather than concrete business classes (DIP). It remains open for extension through boot configuration.
 */
@SpringBootApplication
public class SpringbootLambdaApplication {

    /**
     * Entry point invoked by AWS Lambda when using a Spring boot adapter.
     *
     * <p>The method defers to Spring Boot, keeping this adapter agnostic about downstream event-driven details
     * while sitting in the outer adapter layer.
     *
     * @param args the standard Spring Boot argument array
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootLambdaApplication.class, args);
    }
}
