package com.stefan.carservcehistorybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@EnableDiscoveryClient
@SpringBootApplication
public class CarServiceHistoryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceHistoryBackendApplication.class, args);
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Auto-service API")
								.description("Simple auto-service application")
								.version("v0.0.1")
								.license(new License()
								.name("Apache 2.0")
								.url("http://springdoc.org")))
								.externalDocs(new ExternalDocumentation()
								.description("Auto-service Wiki Documentation")
								.url("https://kehayov.net/"));
	}

}
