package com.ridebooking.enums;

public enum VehicleType {

    BIKE(6),
    AUTO(10),
    MINI(12),
    SEDAN(15),
    SUV(20);

    private final double ratePerKm;

    VehicleType(double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }
}