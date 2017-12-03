package sm.rental.model.activities;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
import sm.rental.model.entities.RentalCounter;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.procedures.UDPs;

@RequiredArgsConstructor
public class Service extends ConditionalActivity {
    @NonNull private final SMRental model;

    private Customer customer = null;

    protected static boolean precondition(SMRental model){
        return model.getQRentalLine().size() > 0
                && isAgentAvailable(model.getRgRentalCounter());
    }
    public void startingEvent(){
        occupyAgent();
        customer = model.getQRentalLine().pop(); // Remove customer
    }

    public double duration(){
        return RVPs.uServiceTime(customer.getUType());
    }

    public void terminatingEvent(){
        freeAgent();
        if(customer.getUType() == CustomerType.NEW){
            UDPs.HandleCustomerExit(customer);
        } else {
            model.getQReturnLine().offerLast(customer);
        }
        customer = null;
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
