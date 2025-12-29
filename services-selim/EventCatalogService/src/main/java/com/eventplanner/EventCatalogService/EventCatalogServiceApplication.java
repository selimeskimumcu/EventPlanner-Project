package com.eventplanner.EventCatalogService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EventCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventCatalogServiceApplication.class, args);
	}

}
