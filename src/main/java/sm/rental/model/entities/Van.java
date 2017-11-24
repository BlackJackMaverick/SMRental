package  sm.rental.model.entities;

import java.util.HashSet;

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
    private VansStatus status;
    public HashSet<Customer> group = new HashSet<Customer>(); //List of customers currently in each van
    private int capacity;//Number of seats left available.
    private double mileage;//Total number of miles driven by the van in the observation interval
    private int numVans; //Total number of vans running in the system.

    public int getNumVans(){
        return this.numVans;
    }
    public VansStatus getVansStatus(){
        return this.status;
    }
    public void setVansStatus(VansStatus status){
        this.status=status;
    }
    public Van getVans(){
        return Van.this;
    }
    // Required methods to manipulate the group
    public void insertGrp(Customer icgCustomer) {
        group.add(icgCustomer);
        capacity -= icgCustomer.getPassengers();
    }
    public boolean removeGrp(Customer icgCustomer) {
        capacity += icgCustomer.getPassengers();
        return group.remove(icgCustomer);
    }
    public int getN() {
        return group.size();
    }// Attribute n

    public Van(int capacity){
         this.capacity = capacity;

    }

}
