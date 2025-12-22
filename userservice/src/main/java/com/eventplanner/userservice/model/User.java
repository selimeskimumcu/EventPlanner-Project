package com.eventplanner.userservice.model;

import jakarta.persistence.*;
import java.util.UUID;

//JPA Entity
//PostgreSQL’de users tablosu ile eşleştirilir
//Sistemdeki kullanıcıları temsil eder

@Entity
@Table(name = "users")
public class User {
	
	//id UUID PRIMARY KEY

    @Id
    @GeneratedValue
    private UUID id;
    
    //Kullanıcının görünen adı
    //zorunlu

    @Column(nullable = false)
    private String name;
    
    //Kullanıcıyı sistemde benzersiz tanımlar
    //Aynı email ile ikinci kayıt engellenir

    @Column(nullable = false, unique = true)
    private String email;
    
    //Şifre saklama mantığı
    //zorunlu

    @Column(nullable = false)
    private String passwordHash;
    
    //Kullanıcının sistemdeki yetkisini belirtir
    //zorunlu

    @Column(nullable = false)
    private String role = "USER";
    
    public User() { //Default constructor
    }
    
  //Getters & Setters
    
    public UUID getId() {
    	return id;
    }
    
    public void setId(UUID id) {
    	this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail( String email) {
    	this.email = email;
    }
    public String getPasswordHash() {
    	return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
    	this.passwordHash = passwordHash;
    }
    
    public String getRole() {
    	return role;
    }
    
    public void setRole(String role) {
    	this.role = role;
    }
}