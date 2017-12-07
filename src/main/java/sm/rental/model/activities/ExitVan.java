package sm.rental.model.activities;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.VanStatus;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.procedures.UDPs;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ExitVan extends ConditionalActivity {
    @NonNull
    private final SMRental model;
    private Van van = null;
    private Customer customer = null;

    public static boolean precondition(SMRental model) {
        return getVanForUnloading(model).isPresent();
    }

    public void startingEvent() {
        Optional<Van> possibleVan = getVanForUnloading(model);
        if (! possibleVan.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No van present");
        van = possibleVan.get();
        Optional<Customer> possibleCustomer = van.removeNextCustomer();
        if (! possibleCustomer.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No customer present in van");
        customer = possibleCustomer.get();
        UDPs.UpdateVanStatus(van, VanStatus.EXITING);
    }

    public double duration() {
        return RVPs.uExitingTime(customer.getNumPassengers());
    }

    public void terminatingEvent() {
        if (van.getSeatsAvailable() == van.getCapacity())
            UDPs.UpdateVanStatus(van, VanStatus.LOADING);
        else
            UDPs.UpdateVanStatus(van, VanStatus.UNLOADING);
        if (customer.getUType() == CustomerType.NEW)
            model.getRentalLine().offerLast(customer);
        else
            UDPs.HandleCustomerExit(customer);
    }

    // Local User Defined Procedures

    /**
     * The van is located at rental counter or drop off point (Van.Location=RENTAL_COUNTER or DROP_OFF)
     * The vans status is unloading
     * The van is not empty
     **/
    private static Optional<Van> getVanForUnloading(SMRental model) {
        return model.getVans().stream()
                .filter(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0)
                .findFirst();
    }

    // Predicate
    public static final Function<SMRental, Optional<ConditionalActivity>> function = (SMRental model) -> {
        if (ExitVan.precondition(model)) {
            return Optional.of(new ExitVan(model));
        } else
            return Optional.empty();
    };
}
