package com.airtribe.ridewise;

import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        // 2. Select driver matching strategy
        System.out.println("\n===== Driver Matching Strategy =====");
        System.out.println("1. Nearest Driver");
        System.out.println("2. Least Active Driver");
        System.out.print("Enter your choice: ");

        String strategyChoice = scanner.nextLine();

        RideMatchingStrategy matchingStrategy;

        switch (strategyChoice) {
            case "1":
                matchingStrategy = new NearestDriverStrategy();
                System.out.println("Nearest Driver Strategy selected.");
                break;

            case "2":
                matchingStrategy = new LeastActiveDriverStrategy();
                System.out.println("Least Active Driver Strategy selected.");
                break;

            default:
                System.out.println("Invalid choice. Using Nearest Driver Strategy.");
                matchingStrategy = new NearestDriverStrategy();
        }

        // 3. Select fare calculation strategy
        System.out.println("\n===== Fare Calculation Strategy =====");
        System.out.println("1. Default Fare");
        System.out.println("2. Peak Hour Fare");
        System.out.print("Enter your choice: ");

        String fareChoice = scanner.nextLine();

        FareStrategy fareStrategy;

        switch (fareChoice) {
            case "1":
                fareStrategy = new DefaultFareStrategy();
                System.out.println("Default Fare Strategy selected.");
                break;

            case "2":
                fareStrategy = new PeakHourFareStrategy();
                System.out.println("Peak Hour Fare Strategy selected.");
                break;

            default:
                System.out.println("Invalid choice. Using Default Fare Strategy.");
                fareStrategy = new DefaultFareStrategy();
        }
        // 4. Inject dependencies into RideService
        RideService rideService = new RideService(
                riderService, driverService, matchingStrategy, fareStrategy);

        boolean running = true;

        while (running) {
            System.out.println("\n===== RideWise Menu =====");
            System.out.println("1. Add Rider");
            System.out.println("2. Add Driver");
            System.out.println("3. View Available Drivers");
            System.out.println("4. Request Ride");
            System.out.println("5. Complete Ride");
            System.out.println("6. View Rides");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addRider(scanner, riderService);
                    break;
                case "2":
                    addDriver(scanner, driverService);
                    break;
                case "3":
                    viewAvailableDrivers(driverService);
                    break;
                case "4":
                    requestRide(scanner, riderService, rideService);
                    break;
                case "5":
                    completeRide(scanner, rideService);
                    break;
                case "6":
                    viewRides(rideService);
                    break;
                case "7":
                    running = false;
                    System.out.println("Thank you for using RideWise!");
                    break;
                default:
                    System.out.println(
                            "Invalid choice. Please select 1 to 7.");
            }
        }

        scanner.close();
    }
    private static void addRider(
            Scanner scanner, RiderService riderService) {

        try {
            System.out.print("Enter Rider ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Rider Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Rider Location: ");
            String location = scanner.nextLine().trim();

            System.out.print("Enter Latitude: ");
            double latitude = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Longitude: ");
            double longitude = Double.parseDouble(scanner.nextLine());

            Rider rider = new Rider(id, name, location, latitude, longitude);

            riderService.registerRider(rider);

            System.out.println("Rider registered successfully!");
            System.out.println(rider);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to register rider: " + e.getMessage());
        }
    }

    private static void addDriver(
            Scanner scanner, DriverService driverService) {

        try {
            System.out.print("Enter Driver ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Driver Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Driver Location: ");
            String location = scanner.nextLine().trim();

            System.out.print("Enter Latitude: ");
            double latitude = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Longitude: ");
            double longitude = Double.parseDouble(scanner.nextLine());

            Driver driver = new Driver(id, name, location, latitude, longitude);

            driverService.registerDriver(driver);

            System.out.println("Driver registered successfully!");
            System.out.println(driver);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to register driver: " + e.getMessage());
        }
    }

    private static void viewAvailableDrivers(DriverService driverService) {
        List<Driver> availableDrivers = driverService.getAvailableDrivers();

        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers are currently available.");
            return;
        }

        System.out.println("===== Available Drivers =====");
        for (Driver driver : availableDrivers) {
            System.out.println(driver);
        }
    }

    private static void requestRide(
            Scanner scanner,
            RiderService riderService,
            RideService rideService) {

        try {
            System.out.print("Enter Rider ID: ");
            int riderId = Integer.parseInt(scanner.nextLine());

            // Validate that the rider exists
            riderService.getRiderById(riderId);

            System.out.print("Enter Distance (km): ");
            double distance = Double.parseDouble(scanner.nextLine());

            Ride ride = rideService.requestRide(riderId, distance);

            System.out.println("Ride booked successfully!");
            System.out.println(ride);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Unable to book ride: " + e.getMessage());
        }
    }


    private static void completeRide(
            Scanner scanner,
            RideService rideService) {

        try {
            System.out.print("Enter Ride ID: ");
            int rideId = Integer.parseInt(scanner.nextLine());

            FareReceipt receipt = rideService.completeRide(rideId);

            System.out.println("Ride completed successfully!");
            System.out.println("Fare Receipt:");
            System.out.println(receipt);

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid input. Please enter a numeric ride ID."
            );

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(
                    "Unable to complete ride: " + e.getMessage()
            );
        }
    }

    private static void viewRides(RideService rideService) {

        List<Ride> rides = rideService.getAllRides();

        if (rides.isEmpty()) {
            System.out.println("No rides found.");
            return;
        }

        System.out.println("\n===== All Rides =====");

        for (Ride ride : rides) {
            System.out.println(ride);
        }
    }
}