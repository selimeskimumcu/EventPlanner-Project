package com.eventplanner.BookingService.client;

import com.eventplanner.BookingService.model.Booking; // Use local model or DTO if prefers
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

@FeignClient(name = "PaymentService")
public interface PaymentClient {

    @PostMapping("/payments")
    Object processPayment(@RequestBody Object paymentRequest);
}
