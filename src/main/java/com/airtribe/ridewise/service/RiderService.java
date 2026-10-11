package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Rider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RiderService {

    private final Map<Integer, Rider> riders = new HashMap<>();

    public void registerRider(Rider rider) {

        if (rider == null) {
            throw new IllegalArgumentException("Rider cannot be null");
        }
        if (rider.getId() <= 0) {
            throw new IllegalArgumentException("Rider ID must be greater than zero");
        }

        if (rider.getName() == null || rider.getName().isBlank()) {
            throw new IllegalArgumentException("Rider name cannot be empty");
        }

        if (rider.getLocation() == null || rider.getLocation().isBlank()) {
            throw new IllegalArgumentException("Rider location cannot be empty");
        }

        if (riders.containsKey(rider.getId())) {
            throw new IllegalArgumentException("Rider ID already exists: " + rider.getId());
        }

        riders.put(rider.getId(), rider);
    }

    public Rider getRiderById(int riderId) {
        Rider rider = riders.get(riderId);

        if (rider == null) {
            throw new IllegalArgumentException(
                    "Rider not found with ID: " + riderId);
        }

        return rider;
    }

    public Collection<Rider> getAllRiders() {
        return new ArrayList<>(riders.values());
    }
}