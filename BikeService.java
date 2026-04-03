import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Queue;

public class BikeService {

    private Queue<BikeRequest> bikeRequestQueue = new ArrayDeque<>();

    public String findAvailableBike(String location) {
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getLocation() != null 
                    && bike.getLocation().equalsIgnoreCase(location) 
                    && bike.isAvailable()) {
                return bike.getBikeID();
            }
        }
        return null;
    }

    public boolean validateLocation(String location) {
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getLocation() != null 
                    && bike.getLocation().equalsIgnoreCase(location) 
                    && bike.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void reserveBike(String bikeID, String emailAddress, String location) {
        if (bikeID == null) {
            BikeRequest request = new BikeRequest(emailAddress, location, LocalDateTime.now());
            bikeRequestQueue.offer(request);
            System.out.println("Bike not available. Request added to pending queue.");
            return;
        }
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID() != null && bike.getBikeID().equalsIgnoreCase(bikeID)) {
                bike.setIsAvailable(false);
                bike.setLastUsedTime(LocalDateTime.now());
                break;
            }
        }
    }

    public void releaseBike(String bikeID, String location) {
        if (bikeID == null) {
            return;
        }
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID() != null && bike.getBikeID().equalsIgnoreCase(bikeID)) {
                bike.setIsAvailable(true);
                bike.setLastUsedTime(LocalDateTime.now());
                System.out.println("Your trip has ended. Thank you for riding with us.");
                break;
            }
        }
        
        if (!bikeRequestQueue.isEmpty()) {
            BikeRequest nextRequest = bikeRequestQueue.poll();
            System.out.println("Assigning bike to next user in queue: " + nextRequest.getUserEmail());
            for (Bike bike : BikeDatabase.bikes) {
                if (bike.getLocation() != null && bike.getLocation().equalsIgnoreCase(nextRequest.getLocation()) && bike.isAvailable()) {
                    bike.setIsAvailable(false);
                    bike.setLastUsedTime(LocalDateTime.now());
                    System.out.println("Bike assigned to " + nextRequest.getUserEmail());
                    break;
                }
            }
        }
    }

    public Queue<BikeRequest> getBikeRequestQueue() {
        return bikeRequestQueue;
    }

    public void viewPendingRequests() {
        if (bikeRequestQueue.isEmpty()) {
            System.out.println("No pending bike requests.");
            return;
        }
        System.out.println("Pending Bike Requests:");
        for (BikeRequest request : bikeRequestQueue) {
            System.out.println(request);
        }
    }

    public void processNextRequest() {
        if (bikeRequestQueue.isEmpty()) {
            System.out.println("No pending requests to process.");
            return;
        }
        BikeRequest request = bikeRequestQueue.poll();
        System.out.println("Processing request for: " + request.getUserEmail());
    }
}
