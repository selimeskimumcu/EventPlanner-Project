package com.eventplanner.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//Kullanıcı bilgilerini yönetir
//Şifre gibi hassas veriler içerir
//Spring Security kullanarak Kimlik doğrulama ve Yetkilendirme içerir

@Configuration //konfigürasyon sınıfı
public class SecurityConfig {
	
	//HTTP isteklerini filter chain üzerinden geçirir
	//Hangi endpoint kimlik ister
	//Hangisi serbest
	//CSRF var mı kontrolleri saglanır

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	//Cross-Site Request Forgery
    	//REST tabanlı stateless mimari kullandığımız için CSRF devre dışı bırakıldı
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            		//Herkes erişebilir
                .requestMatchers("/auth/**").permitAll()  
                //Kimlik doğrulama ister
                .anyRequest().authenticated()
            );
        //FilterChain’in build edilmesi

        return http.build();
    }
    
    // Kullanıcı şifreleri
    // Güvenlik kuralı

    @Bean //PasswordEncoder
    public PasswordEncoder passwordEncoder() {
    	
    	//Spring Security’nin önerdiği algoritma
    	
        return new BCryptPasswordEncoder();
    }
}
