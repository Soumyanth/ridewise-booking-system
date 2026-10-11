package com.airtribe.ridewise.model;

public class Rider {

    private int id;
    private String name;
    private String location;
    private double latitude;
    private double longitude;

    // Existing constructor — maintains backward compatibility
    public Rider(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // New constructor with geographic coordinates
    public Rider(int id, String name, String location,
                 double latitude, double longitude) {

        if (!Double.isFinite(latitude) || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude. Must be between -90 and 90.");
        }

        if (!Double.isFinite(longitude) || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude. Must be between -180 and 180.");
        }

        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Rider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}