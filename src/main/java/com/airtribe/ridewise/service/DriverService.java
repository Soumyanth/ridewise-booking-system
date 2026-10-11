package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverService {

    private final Map<Integer, Driver> drivers = new HashMap<>();

    public void registerDriver(Driver driver) {

        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (driver.getId() <= 0) {
            throw new IllegalArgumentException("Driver ID must be greater than zero");
        }

        if (driver.getName() == null || driver.getName().isBlank()) {
            throw new IllegalArgumentException("Driver name cannot be empty");
        }

        if (driver.getCurrentLocation() == null || driver.getCurrentLocation().isBlank()) {
            throw new IllegalArgumentException("Driver location cannot be empty");
        }

        if (drivers.containsKey(driver.getId())) {
            throw new IllegalArgumentException("Driver ID already exists: " + driver.getId());
        }

        drivers.put(driver.getId(), driver);
    }

    public Driver getDriverById(int driverId) {

        Driver driver = drivers.get(driverId);

        if (driver == null) {
            throw new IllegalArgumentException(
                    "Driver not found with ID: " + driverId);
        }

        return driver;
    }

    public void updateAvailability(int driverId, boolean available) {
        Driver driver = getDriverById(driverId);

        if (available && driver.getActiveRides() > 0) {
            throw new IllegalStateException("Cannot make a driver available while they have active rides");
        }

        driver.setAvailable(available);
    }

    public List<Driver> getAvailableDrivers() {

        List<Driver> availableDrivers = new ArrayList<>();

        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                availableDrivers.add(driver);
            }
        }

        return availableDrivers;
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }
}