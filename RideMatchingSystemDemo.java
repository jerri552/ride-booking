
import java.util.*;

// =====================================
// 1. INHERITANCE: BASE USER CLASS
// =====================================

class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

// =====================================
// 2. LOCATION CLASS
// =====================================

class Location {
    private double x;
    private double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Distance between two locations
    public double distanceTo(Location other) {
        double dx = x - other.getX();
        double dy = y - other.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}

// =====================================
// 3. VEHICLE ABSTRACTION
// =====================================

abstract class Vehicle {
    private String vehicleType;

    public Vehicle(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    // Abstract method
    public abstract double getRatePerKm();
}

// =====================================
// 4. POLYMORPHISM: VEHICLE TYPES
// =====================================

class Bike extends Vehicle {
    public Bike() {
        super("Bike");
    }

    public double getRatePerKm() {
        return 8;
    }
}

class Auto extends Vehicle {
    public Auto() {
        super("Auto");
    }

    public double getRatePerKm() {
        return 12;
    }
}

class Mini extends Vehicle {
    public Mini() {
        super("Mini");
    }

    public double getRatePerKm() {
        return 15;
    }
}

class Sedan extends Vehicle {
    public Sedan() {
        super("Sedan");
    }

    public double getRatePerKm() {
        return 20;
    }
}

class SUV extends Vehicle {
    public SUV() {
        super("SUV");
    }

    public double getRatePerKm() {
        return 25;
    }
}

// =====================================
// 5. FARE CALCULATOR ABSTRACTION
// =====================================

interface FareCalculator {
    double calculateFare(Vehicle vehicle, double distance);
}

class NormalFareCalculator implements FareCalculator {

    public double calculateFare(Vehicle vehicle, double distance) {
        return vehicle.getRatePerKm() * distance;
    }
}

// =====================================
// 6. RIDER CLASS
// =====================================

class Rider extends User {

    private boolean hasActiveRide;

    public Rider(int id, String name) {
        super(id, name);
        hasActiveRide = false;
    }

    public boolean hasActiveRide() {
        return hasActiveRide;
    }

    public void setActiveRide(boolean status) {
        hasActiveRide = status;
    }
}

// =====================================
// 7. DRIVER CLASS
// =====================================

class Driver extends User {

    private Location location;
    private Vehicle vehicle;
    private boolean available;

    // Used to simulate driver response
    private String response;
    private int responseDelay;

    public Driver(int id, String name, Location location,
                  Vehicle vehicle, String response, int responseDelay) {

        super(id, name);
        this.location = location;
        this.vehicle = vehicle;
        this.available = true;
        this.response = response;
        this.responseDelay = responseDelay;
    }

    public Location getLocation() {
        return location;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status) {
        available = status;
    }

    public String getResponse() {
        return response;
    }

    public int getResponseDelay() {
        return responseDelay;
    }
}

// =====================================
// 8. RIDE REQUEST CLASS
// =====================================

class RideRequest {

    private Rider rider;
    private Location pickup;
    private Location drop;
    private Vehicle requestedVehicle;

    private String status;

    public RideRequest(Rider rider, Location pickup,
                       Location drop, Vehicle requestedVehicle) {

        this.rider = rider;
        this.pickup = pickup;
        this.drop = drop;
        this.requestedVehicle = requestedVehicle;
        this.status = "PENDING";
    }

    public Rider getRider() {
        return rider;
    }

    public Location getPickup() {
        return pickup;
    }

    public Location getDrop() {
        return drop;
    }

    public Vehicle getRequestedVehicle() {
        return requestedVehicle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

// =====================================
// 9. RIDE MATCHING SYSTEM
// =====================================

class RideMatchingSystem {

    private ArrayList<Driver> drivers = new ArrayList<>();

    private static final double MAX_DISTANCE = 5.0;

    private static final long DRIVER_TIMEOUT = 30_000;

    private static final long REQUEST_TIMEOUT = 120_000;

    private FareCalculator fareCalculator =
            new NormalFareCalculator();

    // Add driver
    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    // Request a ride
    public void requestRide(RideRequest request) {

        Rider rider = request.getRider();

        // Check active ride
        if (rider.hasActiveRide()) {
            System.out.println("Rider already has an active ride!");
            return;
        }

        long requestStartTime = System.currentTimeMillis();

        rider.setActiveRide(true);

        System.out.println("\nRide request created!");

        // Find matching drivers
        ArrayList<Driver> matchedDrivers = new ArrayList<>();

        for (Driver driver : drivers) {

            double distance =
                    driver.getLocation().distanceTo(request.getPickup());

            if (driver.isAvailable()
                    && driver.getVehicle().getVehicleType()
                    .equalsIgnoreCase(
                            request.getRequestedVehicle().getVehicleType())
                    && distance <= MAX_DISTANCE) {

                matchedDrivers.add(driver);
            }
        }

        // Sort by nearest distance
        matchedDrivers.sort((d1, d2) -> {

            double distance1 =
                    d1.getLocation().distanceTo(request.getPickup());

            double distance2 =
                    d2.getLocation().distanceTo(request.getPickup());

            return Double.compare(distance1, distance2);
        });

        if (matchedDrivers.isEmpty()) {
            request.setStatus("EXPIRED");
            rider.setActiveRide(false);
            System.out.println("No available drivers within 5 km.");
            System.out.println("Request expired.");
            return;
        }

        // Offer to maximum 3 drivers
        int offerCount = Math.min(3, matchedDrivers.size());

        for (int i = 0; i < offerCount; i++) {

            long elapsed =
                    System.currentTimeMillis() - requestStartTime;

            long remainingTime = REQUEST_TIMEOUT - elapsed;

            if (remainingTime <= 0) {
                break;
            }

            Driver driver = matchedDrivers.get(i);

            double distance =
                    driver.getLocation().distanceTo(request.getPickup());

            System.out.println("\nOffering ride to: "
                    + driver.getName());

            System.out.println("Distance: "
                    + String.format("%.2f", distance) + " km");

            System.out.println("Waiting for driver response...");

            long waitTime = Math.min(DRIVER_TIMEOUT, remainingTime);

            int delay = driver.getResponseDelay();

            if (delay * 1000L > waitTime) {

                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                System.out.println("Driver response timeout!");

                continue;
            }

            try {
                Thread.sleep(delay * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            // Check total request timeout
            if (System.currentTimeMillis() - requestStartTime
                    >= REQUEST_TIMEOUT) {
                break;
            }

            // Driver accepts
            if (driver.getResponse().equalsIgnoreCase("ACCEPT")) {

                driver.setAvailable(false);

                request.setStatus("ACCEPTED");

                System.out.println("\nRide accepted!");

                System.out.println("Driver: " + driver.getName());

                System.out.println("Vehicle: "
                        + driver.getVehicle().getVehicleType());

                double tripDistance =
                        request.getPickup().distanceTo(request.getDrop());

                double fare = fareCalculator.calculateFare(
                        driver.getVehicle(), tripDistance);

                System.out.println("Trip Distance: "
                        + String.format("%.2f", tripDistance) + " km");

                System.out.println("Estimated Fare: Rs."
                        + String.format("%.2f", fare));

                System.out.println("Driver Status: BUSY");

                return;
            }

            // Driver rejects
            else {
                System.out.println("Driver rejected the ride.");

                System.out.println("Searching for next driver...");
            }
        }

        // No driver accepted
        request.setStatus("EXPIRED");

        rider.setActiveRide(false);

        System.out.println("\nRide request expired!");

        System.out.println("Rider has been informed.");
    }
}

// =====================================
// 10. MAIN CLASS
// =====================================

public class RideMatchingSystemDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RideMatchingSystem system = new RideMatchingSystem();

        // Create drivers
        system.addDriver(new Driver(
                101, "Arun", new Location(2, 2),
                new Bike(), "REJECT", 5));

        system.addDriver(new Driver(
                102, "Kumar", new Location(1, 1),
                new Bike(), "ACCEPT", 10));

        system.addDriver(new Driver(
                103, "Ravi", new Location(3, 2),
                new Bike(), "ACCEPT", 5));

        system.addDriver(new Driver(
                104, "Vijay", new Location(8, 8),
                new Bike(), "ACCEPT", 5));

        // Create rider
        Rider rider = new Rider(1, "Jerrin");

        System.out.println("===== RIDE BOOKING SYSTEM =====");

        System.out.print("Enter pickup X: ");
        double px = sc.nextDouble();

        System.out.print("Enter pickup Y: ");
        double py = sc.nextDouble();

        System.out.print("Enter drop X: ");
        double dx = sc.nextDouble();

        System.out.print("Enter drop Y: ");
        double dy = sc.nextDouble();

        System.out.println("\nChoose Vehicle:");
        System.out.println("1. Bike");
        System.out.println("2. Auto");
        System.out.println("3. Mini");
        System.out.println("4. Sedan");
        System.out.println("5. SUV");

        int choice = sc.nextInt();

        Vehicle vehicle;

        switch (choice) {

            case 1:
                vehicle = new Bike();
                break;

            case 2:
                vehicle = new Auto();
                break;

            case 3:
                vehicle = new Mini();
                break;

            case 4:
                vehicle = new Sedan();
                break;

            case 5:
                vehicle = new SUV();
                break;

            default:
                System.out.println("Invalid vehicle!");
                sc.close();
                return;
        }

        RideRequest request = new RideRequest(
                rider,
                new Location(px, py),
                new Location(dx, dy),
                vehicle
        );

        system.requestRide(request);

        sc.close();
    }
}
