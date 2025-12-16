package com.eventplanner.userservice.service;

import com.eventplanner.userservice.dto.AuthResponse;
import com.eventplanner.userservice.dto.LoginRequest;
import com.eventplanner.userservice.dto.RegisterRequest;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
