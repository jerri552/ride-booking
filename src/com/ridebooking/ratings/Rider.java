package com.ridebooking.ratings;

public class Rider {
       // Rider Details
    private final String riderId;
    private final String name;
    private int completedTrips;
    private double averageRating;
    private boolean flaggedForAdminReview;
    // Constructor
    public Rider(String riderId, String name, int completedTrips) {
        this.riderId = riderId;
        this.name = name;
        this.completedTrips = completedTrips;
    }
    // Getter Methods
    public String getRiderId() { return riderId; }
    public String getName() { return name; }
    public int getCompletedTrips() { return completedTrips; }
    public double getAverageRating() { return averageRating; }
    public boolean isFlaggedForAdminReview() { return flaggedForAdminReview; }
   // Update Completed Trips
    public void setCompletedTrips(int completedTrips) {
        this.completedTrips = completedTrips;
    }

    // Update Average Rating and Admin Review Status

    public void updateRatingDetails(double averageRating) {
        this.averageRating = averageRating;
        this.flaggedForAdminReview =
                completedTrips >= 10 && averageRating < 3.0;
    }
}
