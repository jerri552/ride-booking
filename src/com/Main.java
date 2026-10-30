package com.rst;

import java.util.Scanner;

import com.rst.Models.*;
import com.rst.Enums.*;
import com.rst.Services.RegistrationService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RegistrationService service = new RegistrationService();

        System.out.println("====================================");
        System.out.println("     RIDER AND DRIVER REGISTRATION");
        System.out.println("====================================");

        try {

            // RIDER REGISTRATION

            System.out.println("\n--- RIDER REGISTRATION ---");

            System.out.print("Enter Rider Name: ");
            String riderName = sc.nextLine();

            System.out.print("Enter Phone Number: ");
            String riderPhone = sc.nextLine();

            System.out.print("Enter Email: ");
            String riderEmail = sc.nextLine();

            Rider rider = new Rider(
                riderName,
                riderPhone,
                riderEmail
            );

            service.registerRider(rider);

            System.out.println("\nRider Registered Successfully!");


            // DRIVER REGISTRATION

            System.out.println("\n--- DRIVER REGISTRATION ---");

            System.out.print("Enter Driver Name: ");
            String driverName = sc.nextLine();

            System.out.print("Enter Phone Number: ");
            String driverPhone = sc.nextLine();

            System.out.print("Enter Licence Number: ");
            String licenceNumber = sc.nextLine();


            // VEHICLE DETAILS

            System.out.println("\n--- VEHICLE DETAILS ---");

            System.out.print("Enter Vehicle Registration Number: ");
            String registrationNumber = sc.nextLine();

            System.out.println("Vehicle Types: BIKE, AUTO, MINI, SEDAN, SUV");

            System.out.print("Enter Vehicle Type: ");
            VehicleType type = VehicleType.valueOf(
                sc.nextLine().trim().toUpperCase()
            );

            System.out.print("Enter Seating Capacity: ");
            int capacity = Integer.parseInt(sc.nextLine());

            Vehicle vehicle = new Vehicle(
                registrationNumber,
                type,
                capacity
            );


            // CREATE DRIVER

            Driver driver = new Driver(
                driverName,
                driverPhone,
                licenceNumber
            );

            service.registerDriver(driver, vehicle);

            System.out.println("\n====================================");
            System.out.println("     REGISTRATION SUCCESSFUL");
            System.out.println("====================================");

            System.out.println("\n--- RIDER DETAILS ---");
            System.out.println("Name: " + rider.getName());
            System.out.println("Phone: " + rider.getPhoneNumber());
            System.out.println("Email: " + rider.getEmail());

            System.out.println("\n--- DRIVER DETAILS ---");
            System.out.println("Name: " + driver.getName());
            System.out.println("Phone: " + driver.getPhoneNumber());
            System.out.println("Licence: " + driver.getLicenceNumber());

            System.out.println("\n--- VEHICLE DETAILS ---");
            System.out.println("Registration: " +
                vehicle.getRegistrationNumber());

            System.out.println("Type: " + vehicle.getType());

            System.out.println("Seating Capacity: " +
                vehicle.getSeatingCapacity());

            System.out.println("\nDocument Status: " +
                driver.getDocumentStatus());

            System.out.println("Driver Available: " +
                driver.isAvailable());

            System.out.println("\n====================================");

        } catch (Exception e) {

            System.out.println("\nRegistration Failed!");
            System.out.println("Reason: " + e.getMessage());

        }

        sc.close();
    }
}