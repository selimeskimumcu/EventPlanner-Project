package com.EventPlanner.event_catalog_service.controller;

import com.EventPlanner.event_catalog_service.dto.EventCreateRequest;
import com.EventPlanner.event_catalog_service.dto.EventResponse;
import com.EventPlanner.event_catalog_service.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventCreateRequest request) {
        EventResponse response = eventService.createEvent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable UUID id,
                                                     @RequestBody EventCreateRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }
}
