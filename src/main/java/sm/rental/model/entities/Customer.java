package sm.rental.model.entities;

public class Customer {
    protected double startTime; //The time at which a customer enters the system.
    protected static enum CustomerType { N ,R }; //‘N’ for new customers or ‘R’ for returning customers
    protected CustomerType uType;
    protected int  numPassengers; //The number of passengers accompanying the customer plus the customer themselves.
    protected static enum CustomerStatus { BOARDING, EXITING, WAITTING_PICKUP, WAITTING_SERVING, SERVING};
    protected CustomerStatus status;
}
