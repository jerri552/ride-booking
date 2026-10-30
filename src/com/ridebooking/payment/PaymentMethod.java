package com.ridebooking.payment;

import com.ridebooking.enums.PaymentStatus;

public interface PaymentMethod {

    PaymentStatus pay(double amount);

    String getMethodName();
}