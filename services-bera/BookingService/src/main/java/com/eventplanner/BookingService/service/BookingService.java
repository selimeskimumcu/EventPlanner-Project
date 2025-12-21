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

    public Booking createBooking(Booking booking) {
        // 1. Check Event Availability
        // Using Object for now, in real scenario map to EventResponse DTO
        // EventResponse event = eventCatalogClient.getEventById(booking.getEventId());
        // if (event.getAvailableSeats() <= 0) { throw new RuntimeException("No seats
        // available"); }

        // For now, we assume availability is fine if call succeeds
        try {
            eventCatalogClient.getEventById(booking.getEventId());
        } catch (Exception e) {
            throw new RuntimeException("Event not found or service unavailable: " + e.getMessage());
        }

        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");
        booking.setPaymentStatus(false);
        Booking savedBooking = bookingRepository.save(booking);

        // 2. Process Payment
        com.eventplanner.BookingService.dto.PaymentRequest paymentRequest = new com.eventplanner.BookingService.dto.PaymentRequest();
        paymentRequest.setBookingId(savedBooking.getId());
        paymentRequest.setAmount(100.0); // logic to determine price
        paymentRequest.setStatus("PENDING");

        try {
            paymentClient.processPayment(paymentRequest);
            savedBooking.setPaymentStatus(true);
            savedBooking.setStatus("CONFIRMED");
        } catch (Exception e) {
            savedBooking.setStatus("FAILED_PAYMENT");
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
