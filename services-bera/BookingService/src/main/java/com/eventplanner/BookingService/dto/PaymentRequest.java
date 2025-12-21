package com.eventplanner.BookingService.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentRequest {
    private UUID bookingId;
    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
    private String transactionId;

    public PaymentRequest() {
    }

    public PaymentRequest(UUID bookingId, Double amount, String status, LocalDateTime paymentDate,
            String transactionId) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
