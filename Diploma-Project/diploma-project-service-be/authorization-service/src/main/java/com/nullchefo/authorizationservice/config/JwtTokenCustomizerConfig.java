package com.nullchefo.authorizationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.nullchefo.authorizationservice.security.FederatedIdentityIdTokenCustomizer;

@Configuration
public class JwtTokenCustomizerConfig {
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> idTokenCustomizer() {
		return new FederatedIdentityIdTokenCustomizer();
	}

}
