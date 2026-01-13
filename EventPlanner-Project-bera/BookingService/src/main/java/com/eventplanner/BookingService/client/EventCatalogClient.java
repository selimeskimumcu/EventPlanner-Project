package com.eventplanner.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "event-catalog-service")
public interface EventCatalogClient {

    @GetMapping("/events/{id}")
    com.eventplanner.BookingService.dto.EventResponse getEventById(@PathVariable("id") UUID id);
}
