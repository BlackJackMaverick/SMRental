package sm.rental.model.entities;

public class Customer {
    private double startTime; //The time at which a customer enters the system.
    private static enum CustomerType {
        NEW ,
        RETURNING
    };                           //‘N’ for new customers or ‘R’ for returning customers
    private CustomerType uType;
    private int  numPassengers; //The number of passengers accompanying the customer plus the customer themselves.
    public Customer(double startTime, CustomerType uType, int numPassengers){
       this.startTime = startTime;
       this.uType = uType;
       this.numPassengers = numPassengers;
    }
    public int getPassengers(){
        return numPassengers;
    }

}
