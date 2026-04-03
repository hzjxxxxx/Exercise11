import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class BikeRental {
    private boolean isRegisteredUser;
    private String emailAddress;
    private String location;
    private String bikeID;
    private boolean locationValid;

    private UserRegistration userRegistration = new UserRegistration();
    private BikeService bikeService = new BikeService();
    private RentalService rentalService = new RentalService();

    private Scanner scanner = new Scanner(System.in);

    private Deque<ERyderLog> systemLogs = new ArrayDeque<>();

    public void simulateApplicationInput() {
        System.out.println("This is the simulation of the e-bike rental process.");

        System.out.print("Are you a registered user? (true/false): ");
        isRegisteredUser = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Enter your email address: ");
        emailAddress = scanner.nextLine();

        System.out.print("Enter your location: ");
        location = scanner.nextLine();

        System.out.println("Simulating the analysis of the rental request.");
        bikeID = analyseRequest(isRegisteredUser, emailAddress, location);

        if (!locationValid) {
            return;
        }

        System.out.println("Simulating e-bike reservation…");
        reserveBike(bikeID);

        System.out.println("Displaying the active rentals…");
        rentalService.displayActiveRentals();

        System.out.println("Simulating the end of the trip…");
        endTrip(bikeID);

        System.out.println("Displaying the active rentals after trip end…");
        rentalService.displayActiveRentals();
    }

    private String analyseRequest(boolean isRegisteredUser, String emailAddress, String location) {
        if (isRegisteredUser) {
            System.out.println("Welcome back, " + emailAddress + "!");
        } else {
            System.out.println("You're not our registered user. Please consider registering.");
            userRegistration.registration();
        }

        return validateLocation(location);
    }

    private String validateLocation(String location) {
        locationValid = bikeService.validateLocation(location);
        
        if (locationValid) {
            System.out.println("A bike is available at the location you requested.");
            return bikeService.findAvailableBike(location);
        } else {
            System.out.println("Sorry, no bikes are available at the location you requested. Please try again later.");
            return null;
        }
    }

    private void reserveBike(String bikeID) {
        if (bikeID != null) {
            bikeService.reserveBike(bikeID, emailAddress, location);
            rentalService.startRental(bikeID, emailAddress);
            System.out.println("Reserving the bike with the " + bikeID + ". Please following the on-screen instructions to locate the bike and start your pleasant journey.");
            
            String logId = "BR" + (int)(Math.random() * 900 + 100);
            String event = "Bike with " + bikeID + " was rented from " + location;
            ERyderLog log = new ERyderLog(logId, event, LocalDateTime.now());
            systemLogs.push(log);
        } else {
            System.out.println("Sorry, we're unable to reserve a bike at this time. Please try again later.");
        }
    }

    private void endTrip(String bikeID) {
        rentalService.endRental(bikeID);
        bikeService.releaseBike(bikeID, location);
        System.out.println("Your trip has ended. Thank you for riding with us.");
        
        String logId = "ET" + (int)(Math.random() * 900 + 100);
        String event = "Trip ended for bike " + bikeID + " at " + location;
        ERyderLog log = new ERyderLog(logId, event, LocalDateTime.now());
        systemLogs.push(log);
    }

    public void viewSystemLogs() {
        if (systemLogs.isEmpty()) {
            System.out.println("No system logs available.");
            return;
        }
        System.out.println("System Logs:");
        for (ERyderLog log : systemLogs) {
            System.out.println(log);
        }
    }
}
