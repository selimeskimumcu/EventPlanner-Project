package com.eventplanner.EventCatalogService.controller;


//Controller’ın görevi:

//HTTP isteklerini almak

//Gerekli servise yönlendirmek

// HTTP response üretmek 

import com.eventplanner.EventCatalogService.dto.EventCreateRequest;
import com.eventplanner.EventCatalogService.dto.EventResponse;
import com.eventplanner.EventCatalogService.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController       //REST endpoint’leri içerdiğini belirtir
@RequestMapping("/events")      // Bu controller altındaki tüm endpoint’lerin base path’i
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // HTTP Method: POST
    // Endpoint: /events
    //EventCreateRequest DTO’su JSON olarak gelecek [201 CREATEED]
    
    @PostMapping //Event Oluşturma
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventCreateRequest request) {
        EventResponse response = eventService.createEvent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    
    //HTTP Method: GET
    //Endpoint: /events
    //Sistemdeki tüm event’leri listeliyorum

    @GetMapping //Tüm Event’leri Getirme
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
    //URL’den gelen id’yi metoda bağlar
    //URL path değişkeni {id}

    @GetMapping("/{id}") //Tek Event Getirme
    public ResponseEntity<EventResponse> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
    
    //ID = hangi event güncellenecek
    //Request = yeni bilgiler
    //Response = güncellenmiş event
    //HTTP Method:PUT

    @PutMapping("/{id}") //Event Güncelleme
    public ResponseEntity<EventResponse> updateEvent(@PathVariable UUID id,
                                                     @RequestBody EventCreateRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }
}
