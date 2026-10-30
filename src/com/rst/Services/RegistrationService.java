package com.rst.Services;

import java.util.HashMap;
import java.util.Map;

import com.rst.Models.Rider;
import com.rst.Models.Driver;
import com.rst.Models.Vehicle;

public class RegistrationService {

    private Map<String, Rider> riders;
    private Map<String, Driver> drivers;
    private Map<String, Vehicle> vehicles;

    public RegistrationService() {

        riders = new HashMap<String, Rider>();
        drivers = new HashMap<String, Driver>();
        vehicles = new HashMap<String, Vehicle>();
    }

    public void registerRider(Rider rider) {

        if (riders.containsKey(rider.getPhoneNumber())) {
            throw new IllegalArgumentException("Rider phone number already exists");
        }

        for (Rider existing : riders.values()) {
            if (existing.getEmail().equalsIgnoreCase(rider.getEmail())) {
                throw new IllegalArgumentException("Rider email already exists");
            }
        }

        riders.put(rider.getPhoneNumber(), rider);
    }

    public void registerDriver(Driver driver, Vehicle vehicle) {

        if (drivers.containsKey(driver.getPhoneNumber())) {
            throw new IllegalArgumentException("Driver phone number already exists");
        }

        for (Driver existing : drivers.values()) {

            if (existing.getLicenceNumber().equalsIgnoreCase(driver.getLicenceNumber())) {
                throw new IllegalArgumentException("Licence number already exists");
            }
        }

        if (vehicles.containsKey(vehicle.getRegistrationNumber())) {
            throw new IllegalArgumentException("Vehicle registration already exists");
        }

        driver.setActiveVehicle(vehicle);

        drivers.put(driver.getPhoneNumber(), driver);

        vehicles.put(vehicle.getRegistrationNumber(), vehicle);
    }

    public Rider getRider(String phoneNumber) {
        return riders.get(phoneNumber);
    }

    public Driver getDriver(String phoneNumber) {
        return drivers.get(phoneNumber);
    }
}