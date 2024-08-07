package com.nullchefo.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SpringSecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//		corsCustomizer(http);
		http
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeHttpRequests()
				.requestMatchers(
						"/v1/user/**",
						"/user/**",
						"/user/v1/**",
						"/actuator/**",
						"/api-docs/**",
						"/swagger-ui/**",
						"/swagger-ui.html",
						"/v1/api-docs/**",
						"/swagger-ui.html",
						"/user/swagger-ui.html")
				.permitAll()
				.anyRequest().authenticated().and()
				.logout()
				.logoutUrl("/api/v1/profile/logout")
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
				.permitAll();
		//	corsCustomizer(http);
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		return http.build();
	}

	/*
	public void corsCustomizer(HttpSecurity http) throws Exception {
		http.cors(c -> {
			CorsConfigurationSource source = s -> {
				CorsConfiguration cc = new CorsConfiguration();
				cc.setAllowCredentials(true);
				cc.setAllowedOrigins(List.of(
//						"http://127.0.0.1:3000",
//						"http://localhost:3000",
//						"http://127.0.0.1:4200",
   					   "http://localhost:4200"
						,"https://diploma-project.nullchefo.com",
						"https://backend.nullchefo.com",
						"https://auth.nullchefo.com",
						"http://authorization:9000"
											));
				cc.setAllowedHeaders(List.of("*"));
				cc.setAllowedMethods(List.of("*"));
				return cc;
			};
			c.configurationSource(source);
		});
	}



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
