package com.rst.Models;

import java.util.ArrayList;
import java.util.List;

import com.rst.Enums.DocumentStatus;

public class Driver {

    private String name;
    private String phoneNumber;
    private String licenceNumber;

    private Vehicle activeVehicle;
    private DriverDocument documents;

    private boolean available;
    private boolean suspended;

    private List<Integer> ratings;

    public Driver(String name, String phoneNumber, String licenceNumber) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        if (licenceNumber == null || licenceNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Licence number is required");
        }

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenceNumber = licenceNumber;

        this.documents = new DriverDocument();
        this.ratings = new ArrayList<Integer>();

        this.available = false;
        this.suspended = false;
    }

    public void setActiveVehicle(Vehicle vehicle) {

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }

        this.activeVehicle = vehicle;
    }

    public void setDocumentStatus(DocumentStatus status) {
        documents.setStatus(status);
    }

    public void goAvailable() {

        if (documents.getStatus() != DocumentStatus.APPROVED) {
            throw new IllegalStateException("Driver documents are not approved");
        }

        if (suspended) {
            throw new IllegalStateException("Driver is suspended");
        }

        if (activeVehicle == null) {
            throw new IllegalStateException("No active vehicle assigned");
        }

        available = true;
    }

    public void goUnavailable() {
        available = false;
    }

    public void addRating(int rating) {

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        ratings.add(rating);

        if (ratings.size() >= 20 && getAverageRating() < 3.0) {
            suspended = true;
            available = false;
        }
    }

    public double getAverageRating() {

        if (ratings.isEmpty()) {
            return 0.0;
        }

        int total = 0;

        for (Integer rating : ratings) {
            total += rating;
        }

        return (double) total / ratings.size();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public Vehicle getActiveVehicle() {
        return activeVehicle;
    }

    public DocumentStatus getDocumentStatus() {
        return documents.getStatus();
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public int getTotalRides() {
        return ratings.size();
    }
}