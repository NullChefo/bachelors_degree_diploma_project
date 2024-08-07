package com.nullchefo.gatewayservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FallbackController {

    private static final Logger log = LoggerFactory.getLogger(FallbackController.class);

    // Send some cashed resources or more useful massage
    @GetMapping("/fallback/user-service")
    Flux<Void> getUsersFallback() {
        log.info("Fallback for user service ");
        return Flux.empty();

    }
}
