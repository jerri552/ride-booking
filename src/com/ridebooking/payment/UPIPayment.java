package com.ridebooking.payment;

import com.ridebooking.enums.PaymentStatus;

public class UPIPayment implements PaymentMethod {

    @Override
    public PaymentStatus pay(double amount) {

        System.out.println("Processing UPI payment...");

        // Simulated successful payment
        System.out.println("UPI payment successful.");

        return PaymentStatus.PAID;
    }

    @Override
    public String getMethodName() {
        return "UPI";
    }
}