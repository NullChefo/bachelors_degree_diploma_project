package com.nullchefo.authorizationservice.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of("http://127.0.0.1:3000", "http://localhost:3000" , "http://127.0.0.1:4200", "http://localhost:4200"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowedMethods(List.of("*"));
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	public void corsCustomizer(HttpSecurity http) throws Exception {
		http.cors(c -> {
			CorsConfigurationSource source = s -> {
				CorsConfiguration cc = new CorsConfiguration();
				cc.setAllowCredentials(true);
				cc.setAllowedOrigins(List.of("http://127.0.0.1:3000", "http://localhost:3000" ,"http://127.0.0.1:4200", "http://localhost:4200" ));
				cc.setAllowedHeaders(List.of("*"));
				cc.setAllowedMethods(List.of("*"));
				return cc;
			};
			c.configurationSource(source);
		});
	}



}
