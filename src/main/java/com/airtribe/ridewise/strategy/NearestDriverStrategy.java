package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.util.DistanceUtil;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {

        if (rider == null || drivers == null || drivers.isEmpty()) {
            return null;
        }

        Driver nearestDriver = null;
        double minimumDistance = Double.MAX_VALUE;

        for (Driver driver : drivers) {

            if (driver == null || !driver.isAvailable()) {
                continue;
            }

            double distance = DistanceUtil.calculateDistance(
                    rider.getLatitude(), rider.getLongitude(),
                    driver.getLatitude(), driver.getLongitude());

            if (distance < minimumDistance) {
                minimumDistance = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }
}