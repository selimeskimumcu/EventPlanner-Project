package com.eventplanner.PaymentService.repository;

import com.eventplanner.PaymentService.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByBookingId(UUID bookingId);
}
