package com.EventPlanner.user_service.dto;

import java.util.UUID;

public class AuthResponse {

    private UUID userId;
    private String email;
    private String role;
    private String token;

    public AuthResponse() {}

    public AuthResponse(UUID userId, String email, String role, String token) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
