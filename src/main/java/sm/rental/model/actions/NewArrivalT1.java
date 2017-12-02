package sm.rental.model.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;

@RequiredArgsConstructor
public class NewArrivalT1 extends ScheduledAction{
    @NonNull private final SMRental model;

    public double timeSequence(){
        return model.getRvp().DuNCustomerT1();
    }

    public void actionEvent(){
        double startTime = model.getClock();
        int numPassengers = model.getRvp().uNumPassengers();
        Customer icgCustomer= new Customer( startTime, Customer.CustomerType.NEW, numPassengers );
        model.getQTerminals().get(0).add(icgCustomer);
    }
}