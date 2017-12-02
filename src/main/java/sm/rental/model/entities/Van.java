package  sm.rental.model.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;

@Data
@RequiredArgsConstructor
public class Van {
    public enum VanStatus {
        BOARDING,
        EXITING,
        TRAVELLING,
        LOADING,
        UNLOADING,
    }

    public enum VanLocation{
        TERMINAL1,
        TERMINAL2,
        DROP_OFF,
        RENTAL_COUNTER
    }
    @Getter private VanStatus status;
    @Getter private VanLocation location;
    private LinkedList<Customer> group;
    @Getter private int capacity; //Maximum number of seats in the van.
    @Getter private double mileage; //Total number of miles driven by the van in the observation interval
    @NonNull @Getter private final Integer seatsAvailable; //Number of available seats in the van

    // Required methods to manipulate the group
    public void addCustomer(Customer customer) {
        group.offerLast(customer);
    }

    public Customer removeNextCustomer() {
        return group.pop();
    }

    public int getN() {
        return group.size();
    }

    public void addMileage(double milesTravelled){
        mileage += milesTravelled;
    }
}
