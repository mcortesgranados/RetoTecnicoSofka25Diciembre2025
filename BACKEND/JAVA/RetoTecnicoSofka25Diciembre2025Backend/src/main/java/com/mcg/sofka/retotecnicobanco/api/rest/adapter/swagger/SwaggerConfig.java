package com.mcg.sofka.retotecnicobanco.api.rest.adapter.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI baseOpenApi() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("Sofka Banco API")
						.description("Operaciones disponibles para crear y consultar personas en el reto técnico.")
						.version("v0.0.1")
						.contact(new Contact()
								.name("Equipo Sofka")
								.email("soporte@sofka.com")
								.url("https://sofka.com"))
						.license(new License()
								.name("Apache 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0")));
	}

	@Bean
	public GroupedOpenApi personApi() {
		return GroupedOpenApi.builder()
				.group("person")
				.pathsToMatch("/person/**")
				.build();
	}

	@Bean
	public GroupedOpenApi clientApi() {
		return GroupedOpenApi.builder()
				.group("client")
				.pathsToMatch("/client/**")
				.build();
	}
}
