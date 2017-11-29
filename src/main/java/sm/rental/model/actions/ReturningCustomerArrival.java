package sm.rental.model.actions;

import simulationModelling.ScheduledAction;

class ReturningCustomerArrival extends ScheduledAction{
    SMRental model;
    public ReturningCustomerArrival (SMRental model){
        this.model=model;
    }

    public double timeSequence(){
        return model.rvp.DuRCustomer();
    }

    public void actionEvent(){
        double startTime = model.getClock();
        CustomerType uType = model.RETURNING;
        int numPassengers = model.rvp.uNumPassengers();

        Customer icgCustomer = new Customer( startTime, uType, numPassengers );
        model.RentalLine.spInsertQue(icgCustomer);
    }
}