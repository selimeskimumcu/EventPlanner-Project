package com.eventplanner.PaymentService.service;

import com.eventplanner.PaymentService.model.Payment;
import com.eventplanner.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());

        if (payment.getAmount() > 0) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public List<Payment> getPaymentByBookingId(UUID bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }
}
