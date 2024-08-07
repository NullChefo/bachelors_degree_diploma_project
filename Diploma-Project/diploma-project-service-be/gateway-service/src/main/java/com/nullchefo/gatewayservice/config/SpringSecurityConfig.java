package com.nullchefo.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {


	// TODO fix
	private CsrfTokenRepository csrfTokenRepository()
	{
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setSessionAttributeName("_csrf");
		return repository;
	}
	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

		http

			// TODO fix
			//	.csrf().csrfTokenRepository(csrfTokenRepository()).and()
			.csrf().disable()
			.authorizeExchange()

			// TODO allow /user/registration
			// TODO .pathMatchers("/api/auth/**").permitAll()
			.anyExchange().authenticated()
			.and()
			.oauth2ResourceServer()
			.jwt();
		return http.build();
	}
}
