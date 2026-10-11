package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.RideStatus;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;

import java.util.*;

public class RideService {

    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy rideMatchingStrategy;
    private final FareStrategy fareStrategy;
    private final Map<Integer, Ride> rides = new HashMap<>();
    private final Map<Integer, FareReceipt> fareReceipts = new HashMap<>();

    private int nextRideId = 1;

    public RideService(
            RiderService riderService,
            DriverService driverService,
            RideMatchingStrategy rideMatchingStrategy,
            FareStrategy fareStrategy) {

        this.riderService = riderService;
        this.driverService = driverService;
        this.rideMatchingStrategy = rideMatchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(int riderId, double distance) {
        if (!Double.isFinite(distance) || distance <= 0) {
            throw new IllegalArgumentException("Distance must be a positive, valid number.");
        }
        Rider rider = riderService.getRiderById(riderId);

        if (!Double.isFinite(distance) || distance <= 0) {
            throw new IllegalArgumentException(
                    "Distance must be a positive, valid number."
            );
        }

        List<Driver> availableDrivers = driverService.getAvailableDrivers();

        Driver driver = rideMatchingStrategy.findDriver(rider, availableDrivers);

        if (driver == null) {
            throw new IllegalStateException(
                    "No available driver found"
            );
        }

        Ride ride = new Ride(nextRideId++, rider, distance);

        ride.assignDriver(driver);

        driver.setAvailable(false);
        driver.incrementActiveRides();

        rides.put(ride.getId(), ride);

        return ride;
    }
    public FareReceipt completeRide(int rideId) {

        Ride ride = rides.get(rideId);

        if (ride == null) {
            throw new IllegalArgumentException("Ride not found with ID: " + rideId);
        }

        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Only assigned rides can be completed");
        }

        double fare = fareStrategy.calculateFare(ride);
        FareReceipt receipt = new FareReceipt(rideId, fare);

        ride.setStatus(RideStatus.COMPLETED);
        Driver driver = ride.getDriver();
        driver.decrementActiveRides();
        driverService.updateAvailability(driver.getId(), true);
        fareReceipts.put(rideId, receipt);
        return receipt;
    }
    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }
}