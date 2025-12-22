package com.eventplanner.EventCatalogService.repository;

import com.eventplanner.EventCatalogService.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
   
}


// SQL yazmadan, CRUD işlemlerini yapmak ve Servis katmanına temiz bir API sunmak
// EventRepository = Event entity’si için veritabanı işlemlerini yapar