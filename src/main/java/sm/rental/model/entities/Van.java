package  sm.rental.model.entities;

import lombok.Getter;

import java.util.ArrayList;

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
    private ArrayList<Customer> group;
    @Getter private int capacity; //Maximum number of seats in the van.
    @Getter private double mileage; //Total number of miles driven by the van in the observation interval
    @Getter private int seatsAvailable; //Number of available seats in the van

    // Required methods to manipulate the group
    public void addCustomer(Customer customer) {
        group.add(customer);
        seatsAvailable -= customer.getNumPassengers();
    }
    public boolean removeCustomer(Customer customer) {
        seatsAvailable += customer.getNumPassengers();
        return group.remove(customer);
    }
    public int getN() {
        return group.size();
    }

    public Van(int capacity){
         this.capacity = capacity;
         this.group = new ArrayList<>();
         this.status = VanStatus.LOADING;
         this.location = VanLocation.TERMINAL1;
    }
    public void addMileage(double milesTravelled){
        this.mileage += milesTravelled;
    }
}
