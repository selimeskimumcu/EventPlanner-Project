package com.eventplanner.BookingService.service;

import com.eventplanner.BookingService.model.Booking;
import com.eventplanner.BookingService.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private com.eventplanner.BookingService.client.EventCatalogClient eventCatalogClient;

    @Autowired
    private com.eventplanner.BookingService.client.PaymentClient paymentClient;

    /**
     * Creates a new booking.
     * <p>
     * Flow:
     * 1. Validates the event exists (via Feign Client).
     * 2. Saves booking as PENDING.
     * 3. Initiates payment (via Feign Client).
     * 4. Updates status to CONFIRMED or FAILED_PAYMENT.
     */
    public Booking createBooking(Booking booking) {
        try {
            com.eventplanner.BookingService.dto.EventResponse event = eventCatalogClient
                    .getEventById(booking.getEventId());
            if (event == null) {
                throw new RuntimeException("Event not found");
            }
            // Optional: Check available seats logic here
        } catch (Exception e) {
            throw new RuntimeException("Event validation failed: " + e.getMessage());
        }

        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");
        booking.setPaymentStatus(false);
        Booking savedBooking = bookingRepository.save(booking);

        com.eventplanner.BookingService.dto.PaymentRequest paymentRequest = new com.eventplanner.BookingService.dto.PaymentRequest();
        paymentRequest.setBookingId(savedBooking.getId());
        paymentRequest.setAmount(100.0); // This should ideally come from Event details
        paymentRequest.setStatus("PENDING");

        try {
            paymentClient.processPayment(paymentRequest);
            savedBooking.setPaymentStatus(true);
            savedBooking.setStatus("CONFIRMED");
        } catch (Exception e) {
            savedBooking.setStatus("FAILED_PAYMENT");
            // Log error
        }

        return bookingRepository.save(savedBooking);
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getUserBookings(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(UUID id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus("CANCELLED");
            // Call EventCatalogService to release seats if needed
            bookingRepository.save(booking);
        }
    }

    public Booking updateBookingStatus(UUID id, String status) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }

    public Booking updatePaymentStatus(UUID id, boolean paymentStatus) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setPaymentStatus(paymentStatus);
            return bookingRepository.save(booking);
        }
        return null;
    }
}
