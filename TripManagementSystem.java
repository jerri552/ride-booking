
import java.util.*;

enum TripStatus {
    REQUESTED,
    ACCEPTED,
    DRIVER_ARRIVED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

enum DriverStatus {
    AVAILABLE,
    BUSY,
    BLOCKED
}

class Driver {
    String name;
    DriverStatus status;
    int dailyCancellations;

    Driver(String name) {
        this.name = name;
        this.status = DriverStatus.AVAILABLE;
        this.dailyCancellations = 0;
    }

    void resetDailyCancellations() {
        dailyCancellations = 0;
        if (status == DriverStatus.BLOCKED) {
            status = DriverStatus.AVAILABLE;
        }
    }
}

class Rider {
    String name;
    double balance;

    Rider(String name) {
        this.name = name;
        this.balance = 1000;
    }
}

class Trip {
    int tripId;
    Rider rider;
    Driver driver;
    TripStatus status;
    String otp;
    double cancellationFee;
    long acceptedTime;

    Trip(int tripId, Rider rider) {
        this.tripId = tripId;
        this.rider = rider;
        this.status = TripStatus.REQUESTED;
        this.otp = String.format("%04d",
                new Random().nextInt(10000));
        this.cancellationFee = 0;
    }

    // Driver accepts the ride
    void acceptTrip(Driver driver) {
        if (status != TripStatus.REQUESTED) {
            System.out.println("Invalid trip status.");
            return;
        }

        if (driver.status != DriverStatus.AVAILABLE) {
            System.out.println("Driver is not available.");
            return;
        }

        if (driver.dailyCancellations >= 3) {
            System.out.println("Driver cannot accept more rides today.");
            return;
        }

        this.driver = driver;
        driver.status = DriverStatus.BUSY;
        status = TripStatus.ACCEPTED;
        acceptedTime = System.currentTimeMillis();

        System.out.println("Trip accepted by " + driver.name);
        System.out.println("OTP (for testing): " + otp);
    }

    // Driver reaches pickup point
    void driverArrived() {
        if (status != TripStatus.ACCEPTED) {
            System.out.println("Driver cannot arrive at this stage.");
            return;
        }

        status = TripStatus.DRIVER_ARRIVED;
        System.out.println("Driver arrived at pickup point.");
    }

    // Start trip only after correct OTP
    void startTrip(String enteredOtp) {
        if (status != TripStatus.DRIVER_ARRIVED) {
            System.out.println("Driver must arrive first.");
            return;
        }

        if (!otp.equals(enteredOtp)) {
            System.out.println("Incorrect OTP. Trip cannot start.");
            return;
        }

        status = TripStatus.IN_PROGRESS;
        System.out.println("Trip started successfully.");
    }

    // Complete trip
    void completeTrip() {
        if (status != TripStatus.IN_PROGRESS) {
            System.out.println("Trip is not in progress.");
            return;
        }

        status = TripStatus.COMPLETED;
        driver.status = DriverStatus.AVAILABLE;

        System.out.println("Trip completed successfully.");
        System.out.println("Driver is now available.");
    }

    // Rider cancellation
    void riderCancel() {
        if (status == TripStatus.IN_PROGRESS ||
            status == TripStatus.COMPLETED ||
            status == TripStatus.CANCELLED) {
            System.out.println("Trip cannot be cancelled.");
            return;
        }

        if (status == TripStatus.ACCEPTED ||
            status == TripStatus.DRIVER_ARRIVED) {

            long elapsed = System.currentTimeMillis() - acceptedTime;

            if (elapsed > 2 * 60 * 1000) {
                cancellationFee = 30;
                rider.balance -= cancellationFee;
                System.out.println("Cancellation fee: Rs. 30");
            } else {
                System.out.println("Free cancellation.");
            }
        }

        status = TripStatus.CANCELLED;

        if (driver != null) {
            driver.status = DriverStatus.AVAILABLE;
        }

        System.out.println("Trip cancelled by rider.");
    }

    // Driver cancellation
    void driverCancel() {
        if (status != TripStatus.ACCEPTED) {
            System.out.println(
                "Driver can cancel only before arriving."
            );
            return;
        }

        status = TripStatus.CANCELLED;
        driver.dailyCancellations++;

        if (driver.dailyCancellations >= 3) {
            driver.status = DriverStatus.BLOCKED;
            System.out.println(
                "Driver blocked from accepting rides today."
            );
        } else {
            driver.status = DriverStatus.AVAILABLE;
        }

        System.out.println("Trip cancelled by driver.");
    }

    void displayStatus() {
        System.out.println("Trip ID: " + tripId);
        System.out.println("Status: " + status);
    }
}

public class TripManagementSystem{
    public static void main(String[] args) {

        Rider rider = new Rider("Harish");
        Driver driver = new Driver("Driver1");

        Trip trip = new Trip(101, rider);

        trip.displayStatus();

        // 1. Accept trip
        trip.acceptTrip(driver);

        // 2. Driver arrives
        trip.driverArrived();

        // 3. Enter OTP
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 4-digit OTP: ");
        String enteredOtp = sc.nextLine();

        // 4. Start trip
        trip.startTrip(enteredOtp);

        // 5. Complete trip
        trip.completeTrip();

        // Display final status
        trip.displayStatus();

        System.out.println(
            "Driver Status: " + driver.status
        );

        sc.close();
    }
}
