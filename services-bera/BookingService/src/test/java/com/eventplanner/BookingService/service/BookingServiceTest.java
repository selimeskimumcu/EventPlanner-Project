package com.eventplanner.BookingService.service;

import com.eventplanner.BookingService.client.EventCatalogClient;
import com.eventplanner.BookingService.client.PaymentClient;
import com.eventplanner.BookingService.dto.EventResponse;
import com.eventplanner.BookingService.dto.PaymentRequest;
import com.eventplanner.BookingService.model.Booking;
import com.eventplanner.BookingService.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventCatalogClient eventCatalogClient;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private EventResponse eventResponse;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(UUID.randomUUID());
        booking.setUserId(UUID.randomUUID());
        booking.setEventId(UUID.randomUUID());

        eventResponse = new EventResponse();
        eventResponse.setId(booking.getEventId());
        eventResponse.setTitle("Test Event");
        eventResponse.setAvailableSeats(10);
    }

    @Test
    void createBooking_Success() {
        // Arrange
        when(eventCatalogClient.getEventById(booking.getEventId())).thenReturn(eventResponse);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Booking createdBooking = bookingService.createBooking(booking);

        // Assert
        assertNotNull(createdBooking);
        assertEquals("CONFIRMED", createdBooking.getStatus());
        assertTrue(createdBooking.isPaymentStatus());
        verify(paymentClient, times(1)).processPayment(any(PaymentRequest.class));
    }

    @Test
    void createBooking_PaymentFailure() {
        // Arrange
        when(eventCatalogClient.getEventById(booking.getEventId())).thenReturn(eventResponse);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doThrow(new RuntimeException("Payment Failed")).when(paymentClient).processPayment(any(PaymentRequest.class));

        // Act
        Booking createdBooking = bookingService.createBooking(booking);

        // Assert
        assertNotNull(createdBooking);
        assertEquals("FAILED_PAYMENT", createdBooking.getStatus());
        assertFalse(createdBooking.isPaymentStatus());
    }
}
