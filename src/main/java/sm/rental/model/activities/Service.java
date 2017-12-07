package sm.rental.model.activities;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;
import sm.rental.model.SMRental;
import sm.rental.model.actions.ReturningArrival;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
import sm.rental.model.entities.RentalCounter;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.procedures.UDPs;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class Service extends ConditionalActivity {
    @NonNull private final SMRental model;

    private Customer customer;

    public static boolean precondition(SMRental model) {
        return (model.getRentalLine().size() > 0)
                && isAgentAvailable(model.getRentalCounter());
    }
    public void startingEvent() {
        occupyAgent();
        customer = model.getRentalLine().pop(); // Remove customer
        if(customer == null)
            throw new RuntimeException("Couldn't service");
    }

    public double duration() {
        return RVPs.uServiceTime(customer.getUType());
    }

    public void terminatingEvent() {
        freeAgent();
        if(customer.getUType() == CustomerType.NEW)
            UDPs.HandleCustomerExit(customer);
        else
            model.getReturnLine().offerLast(customer);
    }

    //Local User Defined Procedures
    private static boolean isAgentAvailable(RentalCounter rc){
        return rc.getUNumAgents() > 0;
    }

    private void freeAgent(){
        model.getRentalCounter().addAgent();
    }

    private void occupyAgent(){
        model.getRentalCounter().removeAgent();
    }

    // Predicate

    public static final Function<SMRental, Optional<ConditionalActivity>> function = (SMRental model) -> {
        if(Service.precondition(model))
            return Optional.of(new Service(model));
        else return Optional.empty();
    };
}
