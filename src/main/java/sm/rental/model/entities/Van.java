package  sm.rental.model.entities;

import lombok.Getter;

import java.util.ArrayList;

public class Van {
    private static enum VansStatus {
                        BOARDING,
                        EXITING,
                        TRAVELLING,
                        LOADING,
                        UNLOADING,
    };
    private static enum VansLocation{
                        T1;
                        T2;
                        DROP_OFF;
                        RENTAL_COUNTER;
    };
    @Getter private VansStatus status;
    @Getter private VansLocation location;
    private ArrayList<Customer> group;
    @Getter private int capacity; //Maximum number of seats in the van.
    @Getter private double mileage; //Total number of miles driven by the van in the observation interval
    @Getter private int seatsAvailable; //Number of available seats in the van

    // Required methods to manipulate the group
    public void insertGrp(Customer icgCustomer) {
        group.add(icgCustomer);
        seatsAvailable -= icgCustomer.getNumPassengers();
    }
    public boolean removeGrp(Customer icgCustomer) {
        seatsAvailable += icgCustomer.getNumPassengers();
        return group.remove(icgCustomer);
    }
    public int getN() {
        return group.size();
    }

    public Van(int capacity){
         this.capacity = capacity;
         this.group = new ArrayList<>();
         this.status = VansStatus.BOARDING;
         this.location = T1;
    }
    public void addMileage(double milesTravelled){
        this.mileage += milesTravelled;
    }
}
