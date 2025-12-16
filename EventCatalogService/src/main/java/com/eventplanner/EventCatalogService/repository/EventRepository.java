package com.eventplanner.EventCatalogService.repository;

import com.eventplanner.EventCatalogService.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    // ileride filtreleme için metotlar ekleyebilirsin
}