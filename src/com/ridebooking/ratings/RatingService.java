package com.ridebooking.ratings;

import java.time.Duration;
import java.time.LocalDateTime;

public class RatingService {

    // Repository connection
    private final RatingRepository repository;

    public RatingService(RatingRepository repository) {
        this.repository = repository;
    }

    // Rider rates Driver
    public void riderRatesDriver(Rider rider, Driver driver, String tripId,
                                 int score, LocalDateTime tripEndedAt) {

        submitRating(rider.getRiderId(), driver.getDriverId(),
                "RIDER", "DRIVER", tripId, score, tripEndedAt);

        double average = repository.calculateAverageFor(driver.getDriverId(), "DRIVER");
        driver.updateRatingDetails(average);
    }

    // Driver rates Rider
    public void driverRatesRider(Driver driver, Rider rider, String tripId,
                                 int score, LocalDateTime tripEndedAt) {

        submitRating(driver.getDriverId(), rider.getRiderId(),
                "DRIVER", "RIDER", tripId, score, tripEndedAt);

        double average = repository.calculateAverageFor(rider.getRiderId(), "RIDER");
        rider.updateRatingDetails(average);
    }

    // Validate and submit rating
    private void submitRating(String raterId, String rateeId,
                              String raterType, String rateeType,
                              String tripId, int score,
                              LocalDateTime tripEndedAt) {

        // Check rating score
        if (score < 1 || score > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5.");

        // Check trip ID
        if (tripId == null || tripId.trim().isEmpty())
            throw new IllegalArgumentException("Trip ID is required.");

        // Check trip end time
        if (tripEndedAt == null)
            throw new IllegalArgumentException("Trip end time is required.");

        LocalDateTime now = LocalDateTime.now();

        // Prevent future trip time
        if (tripEndedAt.isAfter(now))
            throw new IllegalArgumentException("Trip end time cannot be in the future.");

        // Allow rating only within 24 hours
        long minutesAfterTrip = Duration.between(tripEndedAt, now).toMinutes();

        if (minutesAfterTrip > 24 * 60)
            throw new IllegalArgumentException("Rating period has expired.");

        // Prevent duplicate rating
        if (repository.hasAlreadyRated(tripId, raterId, raterType))
            throw new IllegalArgumentException("You have already rated for this trip.");

        // Save rating
        repository.save(new Rating(tripId, raterId, rateeId,
                raterType, score, now));
    }
}