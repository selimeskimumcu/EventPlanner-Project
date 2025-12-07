package com.EventPlanner.event_catalog_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventResponse {

    private UUID id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime dateTime;
    private String location;
    private Integer capacity;
    private Integer availableSeats;

    public EventResponse() {}

    public EventResponse(UUID id, String title, String description,
                         String category, LocalDateTime dateTime,
                         String location, Integer capacity, Integer availableSeats) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
}
