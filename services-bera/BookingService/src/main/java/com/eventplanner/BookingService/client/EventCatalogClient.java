package com.eventplanner.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "EventCatalogService", url = "${event-catalog-service.url:http://localhost:8080}")
public interface EventCatalogClient {

    @GetMapping("/events/{id}")
    Object getEventById(@PathVariable("id") UUID id);
    // Using Object for now to avoid duplicating the full DTO hierarchy,
    // or we can create a simple EventDTO in BookingService
}
