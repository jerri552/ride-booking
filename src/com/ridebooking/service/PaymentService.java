package com.ridebooking.service;

import java.util.ArrayList;
import java.util.List;

import com.ridebooking.model.Payment;
import com.ridebooking.payment.PaymentMethod;
import com.ridebooking.enums.PaymentStatus;

public class PaymentService {

    private List<Payment> payments = new ArrayList<>();

    private int paymentCounter = 1;

    // Process payment
    public Payment processPayment(
            String riderId,
            double amount,
            PaymentMethod method) {

        if (amount < 0) {
            throw new IllegalArgumentException(
                "Payment amount cannot be negative."
            );
        }

        PaymentStatus status = method.pay(amount);

        Payment payment = new Payment(
                paymentCounter++,
                riderId,
                amount,
                method.getMethodName(),
                status
        );

        payments.add(payment);

        return payment;
    }

    // Check failed payment for a particular rider
    public boolean hasFailedPayment(String riderId) {

        for (Payment payment : payments) {

            if (payment.getRiderId().equals(riderId)
                    && payment.getStatus() == PaymentStatus.FAILED) {

                return true;
            }
        }

        return false;
    }

    // Complete pending cash payment
    public void completeCashPayment(int paymentId) {

        for (Payment payment : payments) {

            if (payment.getPaymentId() == paymentId
                    && payment.getMethod().equals("CASH")
                    && payment.getStatus() == PaymentStatus.PENDING) {

                payment.setStatus(PaymentStatus.PAID);

                System.out.println("Cash payment completed.");
                return;
            }
        }

        System.out.println("Pending cash payment not found.");
    }

    // Driver receives 80%, platform receives 20%
    public void distributeRevenue(double amount) {

        double driverShare = amount * 0.80;
        double platformShare = amount * 0.20;

        System.out.println("\n===== REVENUE DISTRIBUTION =====");

        System.out.println(
            "Driver Share: Rs." + driverShare
        );

        System.out.println(
            "Platform Share: Rs." + platformShare
        );
    }

    // Cancellation fee goes fully to driver
    public void distributeCancellationFee() {

        double cancellationFee = 30;

        System.out.println("\n===== CANCELLATION FEE =====");

        System.out.println(
            "Cancellation Fee: Rs." + cancellationFee
        );

        System.out.println(
            "Driver Receives: Rs." + cancellationFee
        );
    }

    // Display all payment records
    public void displayAllPayments() {

        System.out.println("\n===== PAYMENT HISTORY =====");

        if (payments.isEmpty()) {
            System.out.println("No payment records found.");
            return;
        }

        for (Payment payment : payments) {

            payment.displayPayment();

            System.out.println("-------------------------");
        }
    }
}