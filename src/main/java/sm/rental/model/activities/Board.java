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

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class Board extends ConditionalActivity {
    @NonNull private final SMRental model;
    private Customer customer = null;
    private Van van = null;

    public static boolean precondition (SMRental model){
        return UDPs.CanCustomerBoardVan(model);
    }

    @SneakyThrows
    public void startingEvent(){
        Optional<Van> possibleVan = UDPs.GetVanForBoarding(model);
        if(!possibleVan.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No van present");
        van = possibleVan.get();
        Optional<Customer> possibleCustomer = getCustomerToBoard(model, van);
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
    private static Optional<Customer> getCustomerToBoard(SMRental model, Van van){
        Optional<LinkedList<Customer>> queue = UDPs.GetLocationForBoarding(model, van);
        if(!queue.isPresent()) return Optional.empty();
        Optional<Customer> possibleCustomer = queue.get().stream()
                .filter(customer -> customer.getNumPassengers() <= van.getSeatsAvailable())
                .findFirst();
        possibleCustomer.ifPresent(queue.get()::remove);
        return possibleCustomer;
    }

    // Predicate
    public static final Function<SMRental,Optional<ConditionalActivity>> function = (SMRental model) -> {
        if(Board.precondition(model)){
            return Optional.of(new Board(model));
        }
        else return Optional.empty();
    };
}
