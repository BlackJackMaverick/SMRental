package sm.rental.model.actions;

import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;

class ReturningArrival extends ScheduledAction{
    SMRental model;
    public ReturningArrival(SMRental model){
        this.model=model;
    }

    public double timeSequence(){
        return model.rvp.DuRCustomer();
    }

    public void actionEvent(){
        double startTime = model.getClock();
        int numPassengers = model.rvp.uNumPassengers();

        Customer icgCustomer = new Customer( startTime, Customer.CustomerType.RETURNING, numPassengers );
        model.getRentalLine().add(icgCustomer);
    }
}