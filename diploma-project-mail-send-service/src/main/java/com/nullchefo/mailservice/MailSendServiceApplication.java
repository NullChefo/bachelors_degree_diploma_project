package com.nullchefo.mailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MailSendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailSendServiceApplication.class, args);
    }

}
