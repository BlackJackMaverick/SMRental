package  sm.rental.model.entities;

import lombok.Getter;

import java.util.ArrayList;

public class Van {
    private static enum VansStatus {
                        BOARDING_T1,
                        BOARDING_T2,
                        BOARDING_RC,
                        EXITING_DP,
                        EXITING_RC,
                        TRAVELLING_TO_T1_FROM_DP,
                        TRAVELLING_TO_T1_FROM_RC,
                        TRAVELLING_TO_T2,
                        TRAVELLING_TO_RC,
                        TRAVELLING_TO_DP,
                        LOADING_T1,
                        LOADING_T2,
                        LOADING_RC,
                        UNLOADING_RC,
                        UNLOADING_DP
    };
    @Getter private VansStatus status;
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
         this.status = VansStatus.LOADING_T1;
    }
    public void addMileage(double milesTravelled){
        this.mileage += milesTravelled;
    }
}
