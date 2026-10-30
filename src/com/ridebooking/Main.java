package com.ridebooking;

import java.util.Scanner;

import com.ridebooking.enums.VehicleType;
import com.ridebooking.model.Payment;
import com.ridebooking.payment.*;
import com.ridebooking.service.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FareService fareService = new FareService();

        PaymentService paymentService =
                new PaymentService();

        System.out.println("===== RIDE BOOKING SYSTEM =====");

        // Rider details
        System.out.print("Enter Rider ID: ");
        String riderId = sc.next();

        // Check previous failed payment
        if (paymentService.hasFailedPayment(riderId)) {

            System.out.println(
                "You have a failed payment. Clear it before booking a new ride."
            );

            sc.close();
            return;
        }

        // Ride details
        System.out.print("Enter Distance (km): ");
        double distance = sc.nextDouble();

        System.out.print("Enter Travel Time (minutes): ");
        int time = sc.nextInt();

        // Vehicle selection
        System.out.println("\nSelect Vehicle:");

        System.out.println("1. Bike");
        System.out.println("2. Auto");
        System.out.println("3. Mini");
        System.out.println("4. Sedan");
        System.out.println("5. SUV");

        int choice = sc.nextInt();

        VehicleType vehicle;

        switch (choice) {

            case 1:
                vehicle = VehicleType.BIKE;
                break;

            case 2:
                vehicle = VehicleType.AUTO;
                break;

            case 3:
                vehicle = VehicleType.MINI;
                break;

            case 4:
                vehicle = VehicleType.SEDAN;
                break;

            case 5:
                vehicle = VehicleType.SUV;
                break;

            default:
                System.out.println("Invalid vehicle choice.");
                sc.close();
                return;
        }

        // Surge pricing details
        System.out.print("Enter Pending Requests: ");
        int pending = sc.nextInt();

        System.out.print("Enter Available Drivers: ");
        int available = sc.nextInt();

        // Calculate fare
        double fare;

        try {

            fare = fareService.calculateFare(
                    distance,
                    time,
                    vehicle,
                    pending,
                    available
            );

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
            sc.close();
            return;
        }

        System.out.println("\n===== FARE DETAILS =====");

        System.out.println("Vehicle: " + vehicle);
        System.out.println("Final Fare: Rs." + fare);

        // Payment selection
        System.out.println("\nSelect Payment Method:");

        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.println("3. Wallet");

        int paymentChoice = sc.nextInt();

        PaymentMethod method;

        switch (paymentChoice) {

            case 1:
                method = new CashPayment();
                break;

            case 2:
                method = new UPIPayment();
                break;

            case 3:

                System.out.print("Enter Wallet Balance: ");

                double balance = sc.nextDouble();

                method = new WalletPayment(balance);
                break;

            default:
                System.out.println("Invalid payment choice.");
                sc.close();
                return;
        }

        // Process payment
        Payment payment = paymentService.processPayment(
                riderId,
                fare,
                method
        );

        System.out.println("\n===== PAYMENT DETAILS =====");

        payment.displayPayment();

        // Revenue distribution
        if (payment.getStatus().toString().equals("PAID")) {

            paymentService.distributeRevenue(fare);
        }

        // Cash payment completion
        else if (payment.getStatus().toString().equals("PENDING")
                && payment.getMethod().equals("CASH")) {

            System.out.print(
                "\nIs the trip completed and cash collected? (1-Yes / 0-No): "
            );

            int completed = sc.nextInt();

            if (completed == 1) {

                paymentService.completeCashPayment(
                        payment.getPaymentId()
                );

                paymentService.distributeRevenue(fare);
            }
        }

        // Display payment history
        paymentService.displayAllPayments();

        sc.close();
    }
}