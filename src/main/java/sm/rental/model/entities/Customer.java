package sm.rental.model.entities;

public class Customer {
    private double startTime; //The time at which a customer enters the system.
    private static enum CustomerType { N ,R }; //‘N’ for new customers or ‘R’ for returning customers
    private CustomerType uType;
    public int  numPassengers; //The number of passengers accompanying the customer plus the customer themselves.


    public Customer(double startT,CustomerType type,int numP ){
       startTime = startT;
       uType = type;
       numPassengers = numP;
    }
    public Customer(){
    };
}
