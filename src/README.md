# RideWise -- Ride-Sharing System

RideWise is a console-based Java ride-sharing application inspired by
services such as Uber and Ola. It demonstrates object-oriented design,
SOLID principles, and the Strategy design pattern through rider
registration, driver matching, ride management, and fare calculation.

## Features

-   Register riders and drivers.
-   Store rider and driver location details, including latitude and
    longitude.
-   View available drivers.
-   Request a ride and assign a driver using a configurable matching
    strategy.
-   Calculate fares using a configurable fare strategy.
-   Complete rides and make drivers available again.
-   View ride details and their current status.
-   Validate user input and handle invalid operations with meaningful
    errors.

## Technology

-   Java 21 (or a compatible JDK)
-   Standard Java libraries
-   Console-based user interface

No external libraries are required for the current implementation.

## Design

### Matching strategies

-   **Nearest Driver** -- selects the available driver closest to the
    rider using geographic coordinates.
-   **Least Active Driver** -- selects an available driver with the
    lowest active-ride count.

### Fare strategies

-   **Default Fare** -- base fare plus a per-kilometre charge.
-   **Peak-Hour Fare** -- applies a multiplier to the standard fare
    calculation.

The application selects the strategies at startup. The service depends
on strategy interfaces rather than concrete strategy classes, so
strategies can be replaced without changing the ride service.

### Ride lifecycle

A ride is created with `REQUESTED` status, becomes `ASSIGNED` when a
driver is assigned, and becomes `COMPLETED` when the ride is completed.
`CANCELLED` is also defined in the ride-status enum.

## SOLID principles demonstrated

-   **Single Responsibility Principle (SRP):** rider, driver, ride,
    strategy, and service classes have separate responsibilities.
-   **Open/Closed Principle (OCP):** matching and fare behavior can be
    extended by implementing their strategy interfaces.
-   **Liskov Substitution Principle (LSP):** matching and fare strategy
    implementations can be used through their interfaces.
-   **Interface Segregation Principle (ISP):** matching and fare
    calculation use focused interfaces.
-   **Dependency Inversion Principle (DIP):** `RideService` receives
    `RideMatchingStrategy` and `FareStrategy` through constructor
    injection.

The project also uses encapsulation and separates console interaction
from service-layer operations.

## Project structure

``` text
src/main/java/com/airtribe/ridewise/
├── Main.java
├── model/
│   ├── Driver.java
│   ├── FareReceipt.java
│   ├── Ride.java
│   ├── RideStatus.java
│   ├── Rider.java
│   └── VehicleType.java
├── service/
│   ├── DriverService.java
│   ├── RiderService.java
│   └── RideService.java
├── strategy/
│   ├── DefaultFareStrategy.java
│   ├── FareStrategy.java
│   ├── LeastActiveDriverStrategy.java
│   ├── NearestDriverStrategy.java
│   ├── PeakHourFareStrategy.java
│   └── RideMatchingStrategy.java
└── util/
    └── DistanceUtil.java
```

*The tree above describes the expected source layout; adjust it if your
actual project structure differs.*

## How to run

### Option 1: IntelliJ IDEA

1.  Open the project in IntelliJ IDEA.
2.  Configure a compatible JDK (Java 21 recommended).
3.  Open `Main.java`.
4.  Run `Main.main()`.

### Option 2: Compile and run from the terminal

Run these commands from the project root, assuming the source files are
under `src/main/java`:

``` bash
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
java -cp out com.airtribe.ridewise.Main
```

## Console menu

The application provides the following menu:

1.  Add Rider
2.  Add Driver
3.  View Available Drivers
4.  Request Ride
5.  Complete Ride
6.  View Rides
7.  Exit

At startup, select the ride-matching strategy and fare strategy, then
use the menu to exercise the application.

## Example workflow

1.  Register a rider with a unique positive ID and location coordinates.
2.  Register one or more drivers with unique positive IDs and
    coordinates.
3.  View the available drivers.
4.  Request a ride by entering the rider ID and a positive trip distance
    in kilometres.
5.  Review the assigned driver and ride status.
6.  Complete the ride and review the fare receipt.
7.  View rides to verify the completed status.

## Validation and error handling

The services validate IDs, required names and locations, duplicate
registrations, ride distance, ride existence, and ride completion state.
The console layer handles invalid input and displays error messages.

