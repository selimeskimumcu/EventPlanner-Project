package com.EventPlanner.event_catalog_service.service;

import com.EventPlanner.event_catalog_service.dto.EventCreateRequest;
import com.EventPlanner.event_catalog_service.dto.EventResponse;

import java.util.List;
import java.util.UUID;

public interface EventService {

    EventResponse createEvent(EventCreateRequest request);

    List<EventResponse> getAllEvents();

    EventResponse getEventById(UUID id);

    EventResponse updateEvent(UUID id, EventCreateRequest request);
}
