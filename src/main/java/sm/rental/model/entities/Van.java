package  sm.rental.model.entities;

import lombok.*;

import java.util.LinkedList;
import java.util.Optional;

@ToString(exclude="group")
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

    @Getter private int vanId;
    @Getter @Setter private VanStatus status;
    @Getter @Setter private VanLocation location;
    private LinkedList<Customer> group;
    @Getter private int capacity; //Maximum number of seats in the van.
    @Getter private double mileage; //Total number of miles driven by the van in the observation interval
    @Getter private int seatsAvailable; //Number of available seats in the van

    public Van(int capacity, int id){
        vanId=id;
        status = VanStatus.LOADING;
        location = VanLocation.TERMINAL1;
        this.capacity = this.seatsAvailable = capacity;
        group = new LinkedList<>();
    }

    // Required methods to manipulate the group
    public void addCustomer(Customer customer) {
        group.offerLast(customer);
        seatsAvailable -= customer.getNumPassengers();
    }

    public Optional<Customer> removeNextCustomer() {
        Optional<Customer> possibleCustomer = Optional.ofNullable(group.pop());
        possibleCustomer.ifPresent(c->seatsAvailable += c.getNumPassengers());
        return possibleCustomer;
    }

    public int getN() {
        return group.size();
    }

    public void addMileage(double milesTravelled){
        mileage += milesTravelled;
    }
}
