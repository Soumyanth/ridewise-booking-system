package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

public class LeastActiveDriverStrategy
        implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {

        if (rider == null || drivers == null || drivers.isEmpty()) {
            return null;
        }

        Driver selectedDriver = null;
        int minimumActiveRides = Integer.MAX_VALUE;

        for (Driver driver : drivers) {

            if (driver == null || !driver.isAvailable()) {
                continue;
            }

            if (driver.getActiveRides() < minimumActiveRides) {
                minimumActiveRides = driver.getActiveRides();
                selectedDriver = driver;
            }
        }

        return selectedDriver;
    }
}