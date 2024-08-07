package com.nullchefo.authorizationservice;

import com.nullchefo.authorizationservice.utils.PopulateDefaultsUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthorizationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }

    // Populates the clients
    @Bean
    public CommandLineRunner CommandLineRunnerBean(final PopulateDefaultsUtil populateDefaultsUtil) {
        return (args) -> {
            populateDefaultsUtil.PopulateDefaults();
        };
    }

}
