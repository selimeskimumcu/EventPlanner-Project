package com.EventPlanner.user_service.service;

import com.EventPlanner.user_service.dto.AuthResponse;
import com.EventPlanner.user_service.dto.LoginRequest;
import com.EventPlanner.user_service.dto.RegisterRequest;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
