package sm.rental.model.entities;

import java.util.HashSet;
public class RentalAgents {
    protected HashSet<Customer> group = new HashSet<Customer>(); // maintains set of customer objects, that is RG.RentalAgents.list
    protected double cost; // The total cost for all rental agents in the system during one time of experiment.
    protected int uNumAgent; //The number of rental agents available at rental counter to serve customers.


    // Required methods to manipulate the group
    protected void insertGrp(Customer icgCustomer) {	group.add(icgCustomer); }
    protected boolean removeGrp(Customer icgCustomer) { return(group.remove(icgCustomer)); }
    protected int getN() { return group.size(); }  // Attribute n
}
