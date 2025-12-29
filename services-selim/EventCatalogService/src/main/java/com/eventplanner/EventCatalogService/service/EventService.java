package com.eventplanner.EventCatalogService.service;

import com.eventplanner.EventCatalogService.dto.EventCreateRequest;
import com.eventplanner.EventCatalogService.dto.EventResponse;

import java.util.List;
import java.util.UUID;

public interface EventService {

    EventResponse createEvent(EventCreateRequest request);

    List<EventResponse> getAllEvents();

    EventResponse getEventById(UUID id);

    EventResponse updateEvent(UUID id, EventCreateRequest request);
}
