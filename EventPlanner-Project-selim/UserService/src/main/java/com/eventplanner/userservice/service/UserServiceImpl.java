package com.eventplanner.userservice.service;

import com.eventplanner.userservice.dto.AuthResponse;
import com.eventplanner.userservice.dto.LoginRequest;
import com.eventplanner.userservice.dto.RegisterRequest;
import com.eventplanner.userservice.model.User;
import com.eventplanner.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service //bu sınıf servis katmanı bileşenidir
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    //existsByEmail ile aynı email kontrol edilir
    //Duplicate kayıt engellenir
    //unique=true

    @Override //register(RegisterRequest)
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User(); //Kullanıcı oluşturma ve alan atamaları
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        //Register sırasında şifreyi BCrypt ile hashleyip passwordHash alanına kaydediyoruz

        User saved = userRepository.save(user);
        //db'ye kayıt

        //Şu an token kısmı placeholder, entegrasyon aşamasında JWT üretimi eklenecek
        String token = "dummy-token";

        return new AuthResponse(saved.getId(), saved.getEmail(), saved.getRole(), token);
    }
    
    //Email ile user çekmek
    //Yoksa exception fırlat

    @Override //login(LoginRequest)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        
        //Şifre doğrulama

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        //Başarılı login sonrası response
        //Başarılıysa auth response döner.

        String token = "dummy-token";

        return new AuthResponse(user.getId(), user.getEmail(), user.getRole(), token);
    }
}