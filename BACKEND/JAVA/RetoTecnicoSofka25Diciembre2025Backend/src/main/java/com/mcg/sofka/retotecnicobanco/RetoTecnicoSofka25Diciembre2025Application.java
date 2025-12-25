package com.mcg.sofka.retotecnicobanco;

import com.mcg.sofka.retotecnicobanco.api.rest.SpringbootLambdaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Primary Spring Boot entry point that bootstraps the hexagonal API microservice.
 *
 * <p>This class lives in the outermost adapter layer of the hexagon, referencing the
 * application core and its ports without leaking business code back into the framework
 * configuration. It maintains a single responsibility: configuring and launching the
 * Spring ApplicationContext (SRP). It remains open for extension through {@code @ComponentScan}
 * adjustments and closed for modification by keeping initialization logic here minimal (OCP),
 * while relying on dependency inversion through Spring’s injection (DIP).
 *
 * <p>Event-driven aspects (wrapping services behind ports/adapters) are wired in subsequent layers;
 * this entry point merely activates them.
 *
 * @author Manuela Cortés Granados
 * @since 25 Diciembre 2025
 */
@SpringBootApplication
@ComponentScan(
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = SpringbootLambdaApplication.class
	)
)
public class RetoTecnicoSofka25Diciembre2025Application {

	/**
	 * Launches the Spring Boot context and keeps this class focused on orchestration.
	 *
	 * <p>This method demonstrates the Dependency Inversion Principle by delegating
	 * application creation to Spring while remaining agnostic about concrete infrastructures.
	 *
	 * @param args standard SpringApplication arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RetoTecnicoSofka25Diciembre2025Application.class, args);
	}

}
