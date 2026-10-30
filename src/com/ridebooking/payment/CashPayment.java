package com.ridebooking.payment;

import com.ridebooking.enums.PaymentStatus;

public class CashPayment implements PaymentMethod {

    @Override
    public PaymentStatus pay(double amount) {

        System.out.println("Cash payment selected.");
        System.out.println("Collect cash after trip completion.");

        return PaymentStatus.PENDING;
    }

    @Override
    public String getMethodName() {
        return "CASH";
    }
}