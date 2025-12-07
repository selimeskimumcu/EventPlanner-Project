package com.EventPlanner.event_catalog_service.service;

import com.EventPlanner.event_catalog_service.dto.EventCreateRequest;
import com.EventPlanner.event_catalog_service.dto.EventResponse;
import com.EventPlanner.event_catalog_service.model.Event;
import com.EventPlanner.event_catalog_service.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponse createEvent(EventCreateRequest request) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setDateTime(request.getDateTime());
        event.setLocation(request.getLocation());
        event.setCapacity(request.getCapacity());
        event.setAvailableSeats(request.getCapacity());

        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        return mapToResponse(event);
    }

    @Override
    public EventResponse updateEvent(UUID id, EventCreateRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setDateTime(request.getDateTime());
        event.setLocation(request.getLocation());
        event.setCapacity(request.getCapacity());

        Event saved = eventRepository.save(event);
        return mapToResponse(saved);
    }

    private EventResponse mapToResponse(Event e) {
        return new EventResponse(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getCategory(),
                e.getDateTime(),
                e.getLocation(),
                e.getCapacity(),
                e.getAvailableSeats()
        );
    }
}
