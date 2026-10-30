package com.ridebooking.service;

import com.ridebooking.enums.VehicleType;

public class FareService {

    private static final double BASE_FARE = 30;
    private static final double TIME_RATE = 1;
    private static final double MINIMUM_FARE = 50;
    private static final double SURGE_MULTIPLIER = 1.5;

    public double calculateFare(
            double distance,
            int time,
            VehicleType vehicle,
            int pendingRequests,
            int availableDrivers) {

        if (distance < 0 || time < 0) {
            throw new IllegalArgumentException(
                "Distance and time cannot be negative."
            );
        }

        if (pendingRequests < 0 || availableDrivers < 0) {
            throw new IllegalArgumentException(
                "Counts cannot be negative."
            );
        }

        double distanceCharge =
                distance * vehicle.getRatePerKm();

        double timeCharge = time * TIME_RATE;

        double fare = BASE_FARE
                + distanceCharge
                + timeCharge;

        // Minimum fare
        if (fare < MINIMUM_FARE) {
            fare = MINIMUM_FARE;
        }

        // Surge pricing
        if (pendingRequests > 2 * availableDrivers) {
            fare = fare * SURGE_MULTIPLIER;

            System.out.println("Surge pricing applied!");
        }

        return Math.round(fare * 100.0) / 100.0;
    }
}