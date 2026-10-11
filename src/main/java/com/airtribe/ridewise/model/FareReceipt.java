package com.airtribe.ridewise.model;

import java.time.LocalDateTime;

public class FareReceipt {

    private int rideId;
    private double amount;
    private LocalDateTime generatedAt;

    public FareReceipt(int rideId, double amount) {
        this.rideId = rideId;
        this.amount = amount;
        this.generatedAt = LocalDateTime.now();
    }

    public int getRideId() {
        return rideId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    @Override
    public String toString() {
        return "FareReceipt{" +
                "rideId=" + rideId +
                ", amount=" + amount +
                ", generatedAt=" + generatedAt +
                '}';
    }
}