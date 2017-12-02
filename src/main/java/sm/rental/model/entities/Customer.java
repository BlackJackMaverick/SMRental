package sm.rental.model.entities;

import lombok.Value;

@Value
public class Customer {
    public enum CustomerType {
        NEW,
        RETURNING
    }

    private double startTime; //The time at which a customer enters the system.
    private CustomerType uType;
    private int numPassengers; //The number of passengers accompanying the customer plus the customer themselves.
}
