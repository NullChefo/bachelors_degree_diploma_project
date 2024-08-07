package com.nullchefo.carservcehistorybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CarServiceHistoryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceHistoryBackendApplication.class, args);
	}

}
