package com.mcg.sofka.retotecnicobanco.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupEnvironmentLogger {

	private static final Logger log = LoggerFactory.getLogger(StartupEnvironmentLogger.class);
	private final Environment environment;

	public StartupEnvironmentLogger(Environment environment) {
		this.environment = environment;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logDatasourceEnvironment() {
		log.info("🔎 spring.sofka-datasource.url={}", environment.getProperty("spring.sofka-datasource.url"));
		log.info("🔎 spring.sofka-datasource.username={}", environment.getProperty("spring.sofka-datasource.username"));
		log.info("🔎 spring.sofka-datasource.password={}", environment.getProperty("spring.sofka-datasource.password"));
		log.info("🔎 spring.sofka-datasource.driver-class-name={}", environment.getProperty("spring.sofka-datasource.driver-class-name"));
		log.info("🔎 server.port={}", environment.getProperty("server.port"));
		log.info("🔗 Swagger UI endpoint={}", buildSwaggerUrl());
	}

	private String buildSwaggerUrl() {
		String port = environment.getProperty("server.port", "8080");
		String contextPath = environment.getProperty("server.servlet.context-path", "");
		if (contextPath == null) {
			contextPath = "";
		}
		contextPath = contextPath.trim();
		if (contextPath.isEmpty() || "/".equals(contextPath)) {
			contextPath = "";
		} else {
			if (!contextPath.startsWith("/")) {
				contextPath = "/" + contextPath;
			}
			while (contextPath.endsWith("/") && contextPath.length() > 1) {
				contextPath = contextPath.substring(0, contextPath.length() - 1);
			}
		}

		return String.format("http://localhost:%s%s/swagger-ui/index.html", port, contextPath);
	}
}
