package com.mcg.sofka.retotecnicobanco.api.rest;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Alternative Spring Boot launcher used when the application runs inside AWS Lambda.
 *ddd
 * <p>This adapter lives in the infrastructure layer of the hexagon and exists solely
 * to satisfy Lambdaƒ?Ts expectations. It has a single responsibility of wiring the Spring context
 * for that environment (SRP) and depends on abstractions from {@link com.mcg.sofka.retotecnicobanco.RetoTecnicoSofka25Diciembre2025Application}
 * rather than concrete business classes (DIP). It remains open for extension through boot configuration.
 */
@SpringBootApplication
public class SpringbootLambdaApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringbootLambdaApplication.class);

    /**
     * Entry point invoked by AWS Lambda when using a Spring boot adapter.
     *
     * <p>The method defers to Spring Boot, keeping this adapter agnostic about downstream event-driven details
     * while sitting in the outer adapter layer.
     *
     * @param args the standard Spring Boot argument array
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootLambdaApplication.class, args);
        logDatasourceEnvironment(context.getEnvironment());
        validateDatasourceConnection(context);
    }

    private static void logDatasourceEnvironment(Environment env) {
        log.info("🔎 spring.sofka-datasource.url={}", env.getProperty("spring.sofka-datasource.url"));
        log.info("🔎 spring.sofka-datasource.username={}", env.getProperty("spring.sofka-datasource.username"));
        log.info("🔎 spring.sofka-datasource.password={}", env.getProperty("spring.sofka-datasource.password"));
        log.info("🔎 spring.sofka-datasource.driver-class-name={}", env.getProperty("spring.sofka-datasource.driver-class-name"));
        log.info("🔎 server.port={}", env.getProperty("server.port"));
    }

    private static void validateDatasourceConnection(ConfigurableApplicationContext context) {
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            try (Connection connection = dataSource.getConnection()) {
                log.info("🟢 Database connection validated successfully");
            }
        } catch (SQLException ex) {
            log.error("🔴 Database connection failed: {}", ex.getMessage(), ex);
        } catch (RuntimeException ex) {
            log.error("🔴 Failed to validate datasource: {}", ex.getMessage(), ex);
        }
    }
}
