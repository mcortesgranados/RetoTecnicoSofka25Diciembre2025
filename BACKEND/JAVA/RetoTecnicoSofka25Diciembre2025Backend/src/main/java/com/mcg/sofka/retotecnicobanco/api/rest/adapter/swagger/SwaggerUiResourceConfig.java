package com.mcg.sofka.retotecnicobanco.api.rest.adapter.swagger;

import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.webmvc.ui.SwaggerResourceResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerUiResourceConfig implements WebMvcConfigurer {

	private static final String SWAGGER_UI_PATH_PATTERN = "/swagger-ui/**";
	private static final String DEFAULT_SWAGGER_UI_VERSION = "5.13.0";

	private final SwaggerUiConfigProperties swaggerUiConfigProperties;
	private final SwaggerResourceResolver swaggerResourceResolver;

	public SwaggerUiResourceConfig(SwaggerUiConfigProperties swaggerUiConfigProperties) {
		this.swaggerUiConfigProperties = swaggerUiConfigProperties;
		this.swaggerResourceResolver = new SwaggerResourceResolver(swaggerUiConfigProperties);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(SWAGGER_UI_PATH_PATTERN)
				.addResourceLocations(swaggerUiWebjarLocation())
				.resourceChain(false)
				.addResolver(swaggerResourceResolver);
	}

	private String swaggerUiWebjarLocation() {
		String version = swaggerUiConfigProperties.getVersion();
		if (version == null || version.isBlank()) {
			version = DEFAULT_SWAGGER_UI_VERSION;
		}
		return "classpath:/META-INF/resources/webjars/swagger-ui/" + version + "/";
	}
}
