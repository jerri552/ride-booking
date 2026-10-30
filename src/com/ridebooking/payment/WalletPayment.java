package com.ridebooking.payment;

import com.ridebooking.enums.PaymentStatus;

public class WalletPayment implements PaymentMethod {

    private double walletBalance;

    public WalletPayment(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    @Override
    public PaymentStatus pay(double amount) {

        if (walletBalance >= amount) {

            walletBalance = walletBalance - amount;

            System.out.println("Wallet payment successful.");
            System.out.println(
                "Remaining Balance: Rs." + walletBalance
            );

            return PaymentStatus.PAID;
        }

        System.out.println("Insufficient wallet balance.");

        return PaymentStatus.FAILED;
    }

    @Override
    public String getMethodName() {
        return "WALLET";
    }

    public double getWalletBalance() {
        return walletBalance;
    }
}