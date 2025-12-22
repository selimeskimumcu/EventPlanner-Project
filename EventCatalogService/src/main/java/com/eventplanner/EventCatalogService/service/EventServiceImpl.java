package com.eventplanner.EventCatalogService.service;

import com.eventplanner.EventCatalogService.dto.EventCreateRequest;
import com.eventplanner.EventCatalogService.dto.EventResponse;
import com.eventplanner.EventCatalogService.model.Event;
import com.eventplanner.EventCatalogService.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service //servis bileşeni olduğunu tanımlama
public class EventServiceImpl implements EventService {

	
	//Servis katmanı DB’ye direkt SQL ile değil, Repository üzerinden gider
	//Repository injection (Veritabanı erişimi)
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // İstemciden gelen DTO: EventCreateRequest
    //Yeni bir Event entity oluşturuluyor
    //DTO’daki alanlar entity’ye kopyalanıyor
    //availableSeats ilk başta capacity kadar ayarlanıyor
    //eventRepository.save(event) ile DB’ye kaydediliyor.
    //DB’den dönen entity (id dahil) EventResponse DTO’suna çevrilip dışarı döndürülüyor.
    
    @Override //createEvent — Event oluşturma
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

    // findAll() DB’den tüm Event kayıtlarını getirir (List<Event>)
    // stream() ile tek tek dolaşıp mapToResponse ile DTO’ya çeviriyoruz.
    // En sonda List<EventResponse>
    
    @Override //getAllEvents — Tüm event’leri listeleme
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // findById(id) = Optional<Event> döner
    // Yoksa NoSuchElementException fırlatıyoruz
    
    @Override //getEventById — Tek event getirme
    public EventResponse getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        return mapToResponse(event);
    }

    // DB’den event bulunur yoksa exception vercek.
    // Gelen DTO’daki alanlarla entity güncellenir.
    // Tekrar save() edilir
    // Response DTO döner.
    
    @Override //updateEvent — Event güncelleme
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

    // mapToResponse — Entity → DTO dönüşümü
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
