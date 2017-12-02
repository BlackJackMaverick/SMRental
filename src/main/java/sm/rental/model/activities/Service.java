package sm.rental.model.activities;
import lombok.Getter;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
import sm.rental.model.entities.RentalCounter;

import static sm.rental.model.procedures.UDPs.HandleCustomerExit;

public class Service extends ConditionalActivity{
    @Getter private Customer icgCustomer = null;
    @Getter private SMRental model;

    public Service(SMRental model){
        this.model = model;
    }

    protected static boolean precondition(SMRental model){
        return model.getQRentalLine().size() > 0 && isAgentAvailable(model.getRgRentalCounter());
    }
    public void startingEvent(){
        occupyAgent();
        icgCustomer = model.getQRentalLine().pop(); // Remove customer
    }

    public double duration(){
        return model.getRvp().uServiceTime(icgCustomer.getUType());
    }

    public void terminatingEvent(){
        freeAgent();
        if(icgCustomer.getUType() == CustomerType.NEW){
            HandleCustomerExit(icgCustomer);
        } else {
            model.getQReturnLine().offerLast(icgCustomer);
        }
        icgCustomer = null;
    }

    //Local User Defined Procedures
    private static boolean isAgentAvailable(RentalCounter rc){
        return rc.getUNumAgents() > 0;
    }

    private void freeAgent(){
        model.getRgRentalCounter().addAgent();
    }

    private void occupyAgent(){
        model.getRgRentalCounter().removeAgent();
    }
}
