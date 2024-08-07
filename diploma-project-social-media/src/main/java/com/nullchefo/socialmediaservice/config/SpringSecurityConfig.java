package com.nullchefo.socialmediaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SpringSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //	corsCustomizer(http);

        http    // TODO fix this
                .csrf().disable()
                //	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //	.and()
                .authorizeHttpRequests()
                //	.requestMatchers("/user/**").permitAll()
                .requestMatchers(
                        "/actuator/**",

                        "/v2/user/management/*",

                        "/v1/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**"
                        //				,"/v1/media/**"

                ).permitAll()
                .anyRequest().authenticated();
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    public void corsCustomizer(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowCredentials(true);
                cc.setAllowedOrigins(List.of(
                        "http://127.0.0.1:3000",
                        "http://localhost:3000",
                        "http://127.0.0.1:4200",
                        "http://localhost:4200",
                        "https://diploma-project.nullchefo.com",
                        "https://github.com",
                        "https://backend.nullchefo.com",
                        "https://auth.nullchefo.com"
                ));
                cc.setAllowedHeaders(List.of("*"));
                cc.setAllowedMethods(List.of("*"));
                return cc;
            };
            c.configurationSource(source);
        });
    }

    //	@Bean
    //	protected MethodSecurityExpressionHandler createExpressionHandler() {
    //		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    //		expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator(aclRepository));
    //		return expressionHandler;
    //	}
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
