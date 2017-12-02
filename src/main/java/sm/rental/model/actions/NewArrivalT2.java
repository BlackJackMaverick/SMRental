package sm.rental.model.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;

@RequiredArgsConstructor
public class NewArrivalT2 extends ScheduledAction{
    @NonNull private final SMRental model;

    public double timeSequence(){
        return model.getRvp().DuNCustomerT1();
    }

    public void actionEvent(){
        double startTime = model.getClock();
        int numPassengers = model.getRvp().uNumPassengers();

        Customer icgCustomer = new Customer( startTime, CustomerType.NEW, numPassengers );
        model.getQTerminals().get(1).add(icgCustomer);
    }
}