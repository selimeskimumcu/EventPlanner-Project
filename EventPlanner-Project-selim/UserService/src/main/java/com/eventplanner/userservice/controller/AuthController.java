package com.eventplanner.userservice.controller;

import com.eventplanner.userservice.dto.AuthResponse;
import com.eventplanner.userservice.dto.LoginRequest;
import com.eventplanner.userservice.dto.RegisterRequest;
import com.eventplanner.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//HTTP request almak
//Request DTO’yu servise iletmek
//Uygun HTTP status ile response döndürmek

@RestController //REST endpoint’leri sağlar ve dönüşleri JSON olarak döner
@RequestMapping("/auth") // Bu controller altındaki tüm endpoint’lerin base path’i
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    //Endpoint: POST /auth/register
    //Body: RegisterRequest (kullanıcı bilgileri)
    //Service’e gidecek = userService.register(request)
    //Dönen sonuç: AuthResponse

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    //Endpoint: POST /auth/login
    //Body: LoginRequest (email/username + password)
    //Service’e gider = userService.login(request)

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
