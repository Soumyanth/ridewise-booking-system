package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

public class PeakHourFareStrategy implements FareStrategy {

    private static final double BASE_FARE = 50.0;
    private static final double COST_PER_KM = 12.0;
    private static final double PEAK_MULTIPLIER = 1.5;

    @Override
    public double calculateFare(Ride ride) {

        if (ride == null) {
            throw new IllegalArgumentException("Ride cannot be null");
        }

        if (ride.getDistance() <= 0) {
            throw new IllegalArgumentException(
                    "Ride distance must be greater than zero");
        }

        double normalFare = BASE_FARE
                + (ride.getDistance() * COST_PER_KM);

        return normalFare * PEAK_MULTIPLIER;
    }
}