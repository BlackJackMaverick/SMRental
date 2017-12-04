package  sm.rental.model.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

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
    @NonNull @Getter @Setter private VanStatus status;
    @NonNull @Getter @Setter private VanLocation location;
    private LinkedList<Customer> group = new LinkedList<>();
    @Getter private int capacity; //Maximum number of seats in the van.
    @Getter private double mileage; //Total number of miles driven by the van in the observation interval
    @NonNull @Getter private Integer seatsAvailable; //Number of available seats in the van

    // Required methods to manipulate the group
    public void addCustomer(Customer customer) {
        group.offerLast(customer);
        seatsAvailable -= customer.getNumPassengers();
    }

    public Optional<Customer> removeNextCustomer() {
        Optional<Customer> possibleCustomer = Optional.ofNullable(group.pop());
        if(possibleCustomer.isPresent())
            seatsAvailable += possibleCustomer.get().getNumPassengers();
        return possibleCustomer;
    }

    public int getN() {
        return group.size();
    }

    public void addMileage(double milesTravelled){
        mileage += milesTravelled;
    }
}
