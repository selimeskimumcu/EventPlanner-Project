package com.eventplanner.EventCatalogService.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

// Bu sınıfın Entity olduğunu belirtir
// Bir veritabanı tablosu ile eşler
// Veritabanında oluşacak tablonun adı: events

@Entity
@Table(name = "events")
public class Event {
	
	// @Id = Primary Key
	// @GeneratedValue = ID otomatik üretilir
	// Tip = UUID güvenlik için
	// id UUID PRIMARY KEY
	
    @Id
    @GeneratedValue
    private UUID id;
    
    // Alanlar ve Column annotation’ları
    
    //Event başlığı
    // zorunlu null gelirse başarısız

    @Column(nullable = false)
    private String title;
    
    //Event açıklaması
    //Max 1000 karakter
    //Zorunlu değil

    @Column(length = 1000)
    private String description;
    
    //Event türü (konser, konferans, workshop vb.)
    //Nullable olabilir

    private String category;
    
    //Event tarihi ve saati
    //zorunlu

    @Column(nullable = false)
    private LocalDateTime dateTime;
    
    //Event’in yapılacağı yer
    //zorunlu

    @Column(nullable = false)
    private String location;
    
    //Event’in max katılımcı sayısı
    //zorunlu

    @Column(nullable = false)
    private Integer capacity;
    
    //Kalan boş koltuk sayısı
    // BookingService ile entegre olunca güncellenecek alan olacak

    @Column(nullable = false)
    private Integer availableSeats;

    public Event() {}

    // getters & setters
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
