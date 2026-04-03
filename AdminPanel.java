import java.util.Scanner;

public class AdminPanel {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private BikeService bikeService = new BikeService();

    public void userManagementOptions() {
        while (true) {
            System.out.println("Welcome to E-Ryder Admininstrator Panel.");
            System.out.println("What do you want to do?");
            System.out.println("1. Add New Users");
            System.out.println("2. View Registered Users");
            System.out.println("3. Remove Registered Users");
            System.out.println("4. Update Registered Users");
            System.out.println("5. Demo the Bike Rental System");
            System.out.println("6. View System Logs");
            System.out.println("7. Manage Pending Bike Requests");
            System.out.println("8. EXIT");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                userService.addUsers();
            } else if (choice == 2) {
                userService.viewUsers();
            } else if (choice == 3) {
                userService.removeUser();
            } else if (choice == 4) {
                userService.updateUser();
            } else if (choice == 5) {
                BikeRental bikeRental = new BikeRental();
                bikeRental.simulateApplicationInput();
            } else if (choice == 6) {
                BikeRental bikeRental = new BikeRental();
                bikeRental.viewSystemLogs();
            } else if (choice == 7) {
                managePendingBikeRequests();
            } else if (choice == 8) {
                return;
            } else {
                System.out.println("Invalid choice. Please try again");
            }
        }
    }

    private void managePendingBikeRequests() {
        while (true) {
            System.out.println("\nManage Pending Bike Requests:");
            System.out.println("1. View Queue");
            System.out.println("2. Update Queue (Remove first request)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                bikeService.viewPendingRequests();
            } else if (choice == 2) {
                bikeService.processNextRequest();
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Invalid choice. Please try again");
            }
        }
    }
}
