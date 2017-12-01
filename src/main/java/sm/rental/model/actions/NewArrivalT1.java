package sm.rental.model.actions;

import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;

class NewArrivalT1 extends ScheduledAction{
    SMRental model;
    public NewArrivalT1(SMRental model){
        this.model=model;
    }

    public double timeSequence(){
        return model.rvp.DuNCustomerT1();
    }

    public void actionEvent(){
        double startTime = model.getClock();
        int numPassengers = model.rvp.uNumPassengers();

        Customer icgCustomer= new Customer( startTime, Customer.CustomerType.NEW, numPassengers );
        model.getTerminals().get(0).add(icgCustomer);
    }
}