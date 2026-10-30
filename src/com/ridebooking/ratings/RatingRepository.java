package com.ridebooking.ratings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();

    public void save(Rating rating) {
        ratings.add(rating);
    }

    public boolean hasAlreadyRated(String tripId, String raterId, String raterType) {
        for (Rating rating : ratings) {
            if (rating.getTripId().equals(tripId)
                    && rating.getRaterId().equals(raterId)
                    && rating.getRaterType().equalsIgnoreCase(raterType)) {
                return true;
            }
        }
        return false;
    }

    public double calculateAverageFor(String rateeId, String rateeType) {
        int total = 0;
        int count = 0;

        for (Rating rating : ratings) {
            if (rating.getRateeId().equals(rateeId)
                    && rating.getRaterType().equalsIgnoreCase(
                            rateeType.equalsIgnoreCase("DRIVER") ? "RIDER" : "DRIVER")) {
                total += rating.getScore();
                count++;
            }
        }

        return count == 0 ? 0.0 : (double) total / count;
    }

    public List<Rating> findAll() {
        return Collections.unmodifiableList(ratings);
    }
}
