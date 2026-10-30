package com.ridebooking.model;

import java.time.LocalDateTime;
import com.ridebooking.enums.PaymentStatus;

public class Payment {

    private int paymentId;
    private String riderId;
    private double amount;
    private String method;
    private PaymentStatus status;
    private LocalDateTime paymentDate;

    public Payment(int paymentId, String riderId,
                   double amount, String method,
                   PaymentStatus status) {

        this.paymentId = paymentId;
        this.riderId = riderId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.paymentDate = LocalDateTime.now();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public String getRiderId() {
        return riderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void displayPayment() {

        System.out.println("Payment ID: " + paymentId);
        System.out.println("Rider ID: " + riderId);
        System.out.println("Amount: Rs." + amount);
        System.out.println("Method: " + method);
        System.out.println("Status: " + status);
        System.out.println("Date: " + paymentDate);
    }
}