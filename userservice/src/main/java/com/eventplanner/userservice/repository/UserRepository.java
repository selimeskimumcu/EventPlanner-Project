package com.eventplanner.userservice.repository;

import com.eventplanner.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	//Email adresine göre kullanıcıyı bulur
	//Login sırasında kullanılır

    Optional<User> findByEmail(String email);
    
    //Bu email ile kullanıcı var mı
    //Register sırasında duplicate kayıt önlemek için

    boolean existsByEmail(String email);
    
}


//UserRepository, User entity’si için veritabanı işlemlerini yönetir
//JpaRepository<User, UUID> sayesinde
//CRUD işlemleri otomatik gelir
//SQL yazmaya gerek kalmaz