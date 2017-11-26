package sm.rental.model.entities;

import lombok.Getter;

public class Customer {
    public enum CustomerType {
        NEW,
        RETURNING
    }

    @Getter private double startTime; //The time at which a customer enters the system.
    @Getter private CustomerType uType;
    @Getter private int numPassengers; //The number of passengers accompanying the customer plus the customer themselves.
    public Customer(double startTime, CustomerType uType, int numPassengers){
       this.startTime = startTime;
       this.uType = uType;
       this.numPassengers = numPassengers;
    }
}
