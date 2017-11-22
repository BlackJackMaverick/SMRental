package  sm.rental.model.entities;

import java.util.HashSet;

public class Van {
    Customer c=new Customer();
    public static enum VansStatus {
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
    public VansStatus status;
    public HashSet<Customer> group = new HashSet<Customer>(); //List of customers currently in each van
    public int capacity;//Number of seats left available.
    public double mileage;//Total number of miles driven by the van in the observation interval



    // Required methods to manipulate the group
    public void insertGrp(Customer icgCustomer) {
        group.add(icgCustomer);
        capacity -= c.numPassengers;
    }
    public boolean removeGrp(Customer icgCustomer) {
        capacity += c.numPassengers;
        return group.remove(icgCustomer);

    }
    public int getN() {
        return group.size();
    }// Attribute n

    public Van(VansStatus sta, int cap, double mil){
         status = sta;
         capacity = cap;
         mileage = mil;

    }
    public Van(){
    }
}
