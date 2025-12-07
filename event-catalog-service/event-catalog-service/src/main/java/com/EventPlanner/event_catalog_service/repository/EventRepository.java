package com.EventPlanner.event_catalog_service.repository;

import com.EventPlanner.event_catalog_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
