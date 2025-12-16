package com.eventplanner.userservice.service;

import com.eventplanner.userservice.dto.AuthResponse;
import com.eventplanner.userservice.dto.LoginRequest;
import com.eventplanner.userservice.dto.RegisterRequest;
import com.eventplanner.userservice.model.User;
import com.eventplanner.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        User saved = userRepository.save(user);

        // placeholder token (later -> real JWT)
        String token = "dummy-token";

        return new AuthResponse(saved.getId(), saved.getEmail(), saved.getRole(), token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = "dummy-token";

        return new AuthResponse(user.getId(), user.getEmail(), user.getRole(), token);
    }
}