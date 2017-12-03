package sm.rental.model.activities;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.VanStatus;
import sm.rental.model.entities.Van.VanLocation;
import sm.rental.model.entities.Customer;
import simulationModelling.ConditionalActivity;
import sm.rental.model.procedures.UDPs;

import java.util.LinkedList;
import java.util.Optional;

@RequiredArgsConstructor
public class Board extends ConditionalActivity {
    @NonNull private final SMRental model;
    private Customer customer = null;
    private Van van = null;

    public static boolean precondition (SMRental model){
        return canCustomerBoardVan(model);
    }

    @SneakyThrows
    public void startingEvent(){
        Optional<Van> possibleVan = getVanForBoarding(model);
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
        return model.getRvp().uBoardingTime(customer.getNumPassengers());
    }


    public void terminatingEvent(){
        van.addCustomer(customer);
        UDPs.UpdateVanStatus(van, VanStatus.LOADING);
    }

    //
    private static boolean canCustomerBoardVan(SMRental model){
        Optional<Van> possibleVan = getVanForBoarding(model);
        return possibleVan.isPresent() &&
                canCustomerBoard(model, possibleVan.get());
    }

    private static Optional<Van> getVanForBoarding(SMRental model){
         return model.getRgVans().stream()
                .filter(van -> van.getStatus() == VanStatus.LOADING)
                .findFirst();
    }

    private static boolean canCustomerBoard(SMRental model, Van van) {
        Optional<LinkedList<Customer>> queue = getLocationForBoarding(model, van);
        return queue.isPresent() &&
                queue.get().stream()
                        .anyMatch(customer -> customer.getNumPassengers() <= van.getSeatsAvailable());
    }

    private static Optional<Customer> getCustomerToBoard(SMRental model, Van van){
        Optional<LinkedList<Customer>> queue = getLocationForBoarding(model, van);
        if(!queue.isPresent()) return Optional.empty();
        Optional<Customer> possibleCustomer = queue.get().stream()
                .filter(customer -> customer.getNumPassengers() <= van.getSeatsAvailable())
                .findFirst();
        possibleCustomer.ifPresent(queue.get()::remove);
        return possibleCustomer;
    }

    private static Optional<LinkedList<Customer>> getLocationForBoarding(SMRental model, Van van){
        if(van.getLocation() == VanLocation.TERMINAL1) return Optional.of(model.getQTerminals().get(0));
        else if(van.getLocation() == VanLocation.TERMINAL2) return Optional.of(model.getQTerminals().get(1));
        else if(van.getLocation() == VanLocation.RENTAL_COUNTER) return Optional.of(model.getQReturnLine());
        else return Optional.empty();
    }
}
