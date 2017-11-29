package sm.rental.model.actions;

import simulationModelling.ScheduledAction;

class NewCustomerArrivalT2 extends ScheduledAction{
    SMRental model;
    public NewCustomerArrivalT1(SMRental model){
        this.model=model;
    }

    public double timeSequence(){
        return model.rvp.DuNCustomerT1();
    }

    public void actionEvent(){
        double startTime = model.getClock();
        CustomerType uType = model.NEW;
        int numPassengers = model.rvp.uNumPassengers();

        Customer icgCustomer = new Customer( startTime, uType, numPassengers );
        model.Terminal2.spInsertQue(icgCustomer);
    }
}