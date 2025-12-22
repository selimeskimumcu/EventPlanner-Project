package com.eventplanner.EventCatalogService.service;

import com.eventplanner.EventCatalogService.dto.EventCreateRequest;
import com.eventplanner.EventCatalogService.dto.EventResponse;

import java.util.List;
import java.util.UUID;

// Yeni bir event oluşturur
// Parametre = EventCreateRequest
// Dönüş tipi = EventResponse
//
public interface EventService {

    EventResponse createEvent(EventCreateRequest request);
    
    //Sistemdeki tüm event’leri listeler
    List<EventResponse> getAllEvents();
    
    //Tek bir event döndürür
    EventResponse getEventById(UUID id);
    //Var olan bir event’i günceller

    EventResponse updateEvent(UUID id, EventCreateRequest request);
}
