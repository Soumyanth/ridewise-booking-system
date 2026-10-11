package com.airtribe.ridewise.model;

public class Driver {

    private int id;
    private String name;
    private String currentLocation;
    private boolean available;
    private int activeRides;
    private double latitude;
    private double longitude;

    // Existing constructor — backward compatible
    public Driver(int id, String name, String currentLocation) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.available = true;
        this.activeRides = 0;
    }

    // New constructor with geographic coordinates
    public Driver(int id, String name, String currentLocation,
                  double latitude, double longitude) {

        validateCoordinates(latitude, longitude);

        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.available = true;
        this.activeRides = 0;
    }

    private void validateCoordinates(double latitude, double longitude) {
        if (!Double.isFinite(latitude) || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude");
        }

        if (!Double.isFinite(longitude) || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude");
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getActiveRides() {
        return activeRides;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Setters
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void incrementActiveRides() {
        activeRides++;
    }

    public void decrementActiveRides() {
        if (activeRides > 0) {
            activeRides--;
        }
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", available=" + available +
                ", activeRides=" + activeRides +
                '}';
    }
}