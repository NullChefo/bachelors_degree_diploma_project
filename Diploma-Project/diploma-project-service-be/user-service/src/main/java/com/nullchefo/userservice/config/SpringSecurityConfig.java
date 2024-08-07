package com.nullchefo.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http	// TODO fix
				.cors().disable()
				.csrf().disable()
				.authorizeHttpRequests(authorize -> authorize
										   .requestMatchers( "/user/v1/**", "/about").permitAll()
										   .requestMatchers("/admin/**").hasRole("ADMIN")
										   .anyRequest().denyAll()
								  );
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		return http.build();
	}



	/*

	// reference implementation from the spring documentation
	// https://github.com/spring-projects/spring-authorization-server/blob/main/samples/messages-resource/src/main/java/sample/config/ResourceServerConfig.java

		@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/messages/**")
				.authorizeHttpRequests()
					.requestMatchers("/messages/**").hasAuthority("SCOPE_message.read")
					.and()
			.oauth2ResourceServer()
				.jwt();
		return http.build();


	 */

}
