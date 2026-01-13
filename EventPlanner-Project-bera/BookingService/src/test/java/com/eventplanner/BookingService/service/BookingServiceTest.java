package com.eventplanner.BookingService.service;

import com.eventplanner.BookingService.client.EventCatalogClient;
import com.eventplanner.BookingService.client.PaymentClient;
import com.eventplanner.BookingService.dto.EventResponse;
import com.eventplanner.BookingService.dto.PaymentRequest;
import com.eventplanner.BookingService.model.Booking;
import com.eventplanner.BookingService.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventCatalogClient eventCatalogClient;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_Success() {
        UUID eventId = UUID.randomUUID();
        UUID bookingId = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setEventId(eventId);
        booking.setUserId(UUID.randomUUID());

        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(eventId);
        eventResponse.setTitle("Test Event");

        when(eventCatalogClient.getEventById(eventId)).thenReturn(eventResponse);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setId(bookingId);
            return b;
        });
        when(paymentClient.processPayment(any(PaymentRequest.class))).thenReturn(new Object());

        Booking createdBooking = bookingService.createBooking(booking);

        assertNotNull(createdBooking);
        assertEquals("CONFIRMED", createdBooking.getStatus());
        assertTrue(createdBooking.isPaymentStatus());
        verify(paymentClient, times(1)).processPayment(any(PaymentRequest.class));
    }

    @Test
    void createBooking_EventNotFound() {
        UUID eventId = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setEventId(eventId);

        when(eventCatalogClient.getEventById(eventId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(booking));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_PaymentFailed() {
        UUID eventId = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setEventId(eventId);

        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(eventId);

        when(eventCatalogClient.getEventById(eventId)).thenReturn(eventResponse);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doThrow(new RuntimeException("Payment Failed")).when(paymentClient).processPayment(any(PaymentRequest.class));

        Booking createdBooking = bookingService.createBooking(booking);

        assertEquals("FAILED_PAYMENT", createdBooking.getStatus());
        assertFalse(createdBooking.isPaymentStatus());
    }

    @Test
    void getUserBookings() {
        UUID userId = UUID.randomUUID();
        when(bookingRepository.findByUserId(userId)).thenReturn(Arrays.asList(new Booking(), new Booking()));

        List<Booking> bookings = bookingService.getUserBookings(userId);

        assertEquals(2, bookings.size());
    }

    @Test
    void cancelBooking() {
        UUID bookingId = UUID.randomUUID();
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setStatus("CONFIRMED");

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(bookingId);

        verify(bookingRepository, times(1)).save(booking);
        assertEquals("CANCELLED", booking.getStatus());
    }
}
