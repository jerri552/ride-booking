package com.ridebooking.ratings;

import java.time.LocalDateTime;

public class Rating {
    // Rating Details
    private final String tripId;
    private final String raterId;
    private final String rateeId;
    private final String raterType;
    private final int score;
    private final LocalDateTime submittedAt;
// Constructor
    public Rating(String tripId, String raterId, String rateeId,
                  String raterType, int score, LocalDateTime submittedAt) {
        this.tripId = tripId;
        this.raterId = raterId;
        this.rateeId = rateeId;
        this.raterType = raterType;
        this.score = score;
        this.submittedAt = submittedAt;
    }
// Getter Methods
    public String getTripId() { return tripId; }
    public String getRaterId() { return raterId; }
    public String getRateeId() { return rateeId; }
    public String getRaterType() { return raterType; }
    public int getScore() { return score; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}
