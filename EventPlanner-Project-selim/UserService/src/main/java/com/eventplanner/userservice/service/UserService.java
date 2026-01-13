package com.eventplanner.userservice.service;

import com.eventplanner.userservice.dto.AuthResponse;
import com.eventplanner.userservice.dto.LoginRequest;
import com.eventplanner.userservice.dto.RegisterRequest;

public interface UserService {

    AuthResponse register(RegisterRequest request); //register metodu

    AuthResponse login(LoginRequest request);
}


//Yeni kullanıcı kaydı oluşturur
//RegisterRequest = Client’tan gelen kullanıcı bilgileri alır (name, email, password gibi)
//AuthResponse = Kayıt sonucu client’a dönen veri
//LoginRequest = email + password bilgileri
//AuthResponse = token + kullancı bilgileri
//