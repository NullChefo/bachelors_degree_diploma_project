package com.nullchefo.authorizationservice.config;

import com.nullchefo.authorizationservice.security.FederatedIdentityConfigurer;
import com.nullchefo.authorizationservice.service.UserRepositoryOAuth2UserHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    private final UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler;
    private final CorsConfig corsConfig;
    private final String logOutURL;

    public DefaultSecurityConfig(
            final UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler,
            final CorsConfig corsConfig, @Value("${address.frontEnd.successfullyLogOut}") final String logOutURL) {
        this.userRepositoryOAuth2UserHandler = userRepositoryOAuth2UserHandler;
        this.corsConfig = corsConfig;
        this.logOutURL = logOutURL;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http,
            ClientRegistrationRepository clientRegistrationRepository) throws Exception {

        //		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // TODO fix cors and csrf
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                .oauth2UserHandler(userRepositoryOAuth2UserHandler);
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                // TODO!!! fix
                                .requestMatchers(
                                        "/**"
                                        //,"/swagger-ui.html",
                                        //"/v1/api-docs/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .apply(federatedIdentityConfigurer);
        corsConfig.corsCustomizer(http);
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(logOutURL);
        //	http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt).getConfigurer(OAuth2AuthorizationServerConfigurer.class);
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

