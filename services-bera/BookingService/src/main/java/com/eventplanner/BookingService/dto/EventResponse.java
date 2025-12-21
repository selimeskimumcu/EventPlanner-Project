package com.eventplanner.BookingService.dto;

import java.util.UUID;

public class EventResponse {
    private UUID id;
    private String title;
    private int availableSeats;
    private Integer capacity;
    // Add other fields if needed for validation

    public EventResponse() {
    }

    public EventResponse(UUID id, String title, int availableSeats, Integer capacity) {
        this.id = id;
        this.title = title;
        this.availableSeats = availableSeats;
        this.capacity = capacity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
