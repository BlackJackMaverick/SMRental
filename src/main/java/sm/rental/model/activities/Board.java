package sm.rental.model.activities;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.VanStatus;
import sm.rental.model.entities.Customer;
import simulationModelling.ConditionalActivity;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.procedures.UDPs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class Board extends ConditionalActivity {
    @NonNull private final SMRental model;
    private Customer customer = null;
    private Integer van = null;

    public static boolean precondition (SMRental model){
        return getVanForBoarding(model).isPresent();
    }

    @SneakyThrows
    public void startingEvent(){
        Optional<Integer> possibleVanID = getVanForBoarding(model);
        if(!possibleVanID.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No van present");
        van = possibleVanID.get();
        Optional<Customer> possibleCustomer = getCustomerForBoarding(model, van);
        if(!possibleCustomer.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No customer present");
        customer = possibleCustomer.get();
        UDPs.UpdateVanStatus(van, VanStatus.BOARDING);
    }

    protected double duration(){
        return RVPs.uBoardingTime(customer.getNumPassengers());
    }


    public void terminatingEvent(){
        van.addCustomer(customer);
        UDPs.UpdateVanStatus(van, VanStatus.LOADING);
    }

    // Local User Defined procedures
    /**
     * Returns a van that can load a customer at its location.
     * Searches a van and returns the first van that UDP.CanLoadVan(Van) returns true for.
     * Otherwise returns false.
     **/
    private static Optional<Integer> getVanForBoarding(SMRental model) {
        return Arrays.stream(model.getRqVans())
                .filter(UDPs::CanVanLoad)
                .map(Van::getVanId)
                .findFirst();
    }

    /**
     * Finds the first appropriate customer at the vans location for boarding.
     * Otherwise returns false.
     * Uses UDP.GetCustomersAwaiting(Van.Location) to get the queue of customers (awaitingQueue)
     * as input to UDP.GetFirstAppropriateCustomer(awaitingQueue, Van.seatsAvailable)
     **/
    private static Optional<Integer> getCustomerForBoarding(SMRental model, int vanid) {
        Optional<LinkedList<Customer>> queue = UDPs.GetLocationForBoarding(model, vanid);
        if(!queue.isPresent()) return Optional.empty();
        Optional<Customer> possibleCustomer = UDPs.GetFirstAppropriateCustomer(
                queue.get(), model.getRqVans()[vanid].getSeatsAvailable());
        possibleCustomer.ifPresent(queue.get()::remove);
        return possibleCustomer;
    }

    // Predicate
    public static final Function<SMRental,Optional<ConditionalActivity>> function = (SMRental model) -> {
        if(Board.precondition(model))
            return Optional.of(new Board(model));
        else return Optional.empty();
    };
}
