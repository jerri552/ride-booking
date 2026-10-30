package com.ridebooking.ratings;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Rider rider = new Rider("R001", "Arun", 12);
        Driver driver = new Driver("D001", "Kumar", 22);

        RatingRepository repository = new RatingRepository();
        RatingService service = new RatingService(repository);
        LocalDateTime tripEndedAt = LocalDateTime.now();

        System.out.println("===== RIDE BOOKING - RATINGS MODULE =====");
        System.out.println("Trip ID: TRIP1001");
        System.out.println("1. Rider rates Driver");
        System.out.println("2. Driver rates Rider");
        System.out.print("Choose option: ");

        int choice = scanner.nextInt();

        try {
            System.out.print("Enter rating (1 to 5): ");
            int score = scanner.nextInt();

            if (choice == 1) {
                service.riderRatesDriver(rider, driver, "TRIP1001",
                        score, tripEndedAt);
                System.out.println("Driver rating submitted successfully.");
                System.out.printf("Driver average rating: %.2f%n", driver.getAverageRating());
                System.out.println("Driver suspended: " + driver.isSuspended());
            } else if (choice == 2) {
                service.driverRatesRider(driver, rider, "TRIP1001",
                        score, tripEndedAt);
                System.out.println("Rider rating submitted successfully.");
                System.out.printf("Rider average rating: %.2f%n", rider.getAverageRating());
                System.out.println("Rider flagged for admin review: "
                        + rider.isFlaggedForAdminReview());
            } else {
                System.out.println("Invalid option.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Rating failed: " + ex.getMessage());
        }

        System.out.println("\nTotal ratings stored: " + repository.findAll().size());
        scanner.close();
    }
}
