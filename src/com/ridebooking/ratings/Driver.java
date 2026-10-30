package com.ridebooking.ratings;

public class Driver {
    private final String driverId;
    private final String name;
    private int completedRides;
    private double averageRating;
    private boolean suspended;

    public Driver(String driverId, String name, int completedRides) {
        this.driverId = driverId;
        this.name = name;
        this.completedRides = completedRides;
    }

    public String getDriverId() { return driverId; }
    public String getName() { return name; }
    public int getCompletedRides() { return completedRides; }
    public double getAverageRating() { return averageRating; }
    public boolean isSuspended() { return suspended; }

    public void setCompletedRides(int completedRides) {
        this.completedRides = completedRides;
    }

    public void updateRatingDetails(double averageRating) {
        this.averageRating = averageRating;
        this.suspended = completedRides >= 20 && averageRating < 3.0;
    }
}
