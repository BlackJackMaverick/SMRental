package sm.rental.model.entities;


import java.util.HashSet;
class Vans {
    public static enum VansStatus {BOARDING_T1, BOARDING_T2, BOARDING_RC, EXITING_DP, EXITING_RC, TRAVELLING_TO_T1_FROM_DP,
        TRAVELLING_TO_T1_FROM_RC, TRAVELLING_TO_T2, TRAVELLING_TO_RC, TRAVELLING_TO_DP, LOADING_T1, LOADING_T2, LOADING_RC,
        UNLOADING_RC, UNLOADING_DP};
    public VansStatus status;
    public HashSet<Customer> group = new HashSet<Customer>(); //List of customers currently in each van
    int capacity;//Number of seats left available.
    double mileage;//Total number of miles driven by the van in the observation interval
    int num;//The number of vans running in the SUI.


    // Required methods to manipulate the group
    public void insertGrp(Customer icgCustomer) {	group.add(icgCustomer); }
    public boolean removeGrp(Customer icgCustomer) { return(group.remove(icgCustomer)); }
    public int getN() { return group.size(); }  // Attribute n

}
