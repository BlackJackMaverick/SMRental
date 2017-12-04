package sm.rental.model.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
import sm.rental.model.procedures.RVPs;

@RequiredArgsConstructor
public class ReturningArrival extends ScheduledAction{
    @NonNull private final SMRental model;
    public static int count = 0;

    public double timeSequence(){
        return RVPs.DuRCustomer();
    }

    public void actionEvent(){
        Customer customer = new Customer(model.getClock(), CustomerType.RETURNING, RVPs.uNumPassengers() + 1);
        model.getQRentalLine().offerLast(customer);
        count++;
    }
}