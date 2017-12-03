package sm.rental.model.activities;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.VanStatus;
import sm.rental.model.procedures.UDPs;

import java.util.Optional;

@RequiredArgsConstructor
public class ExitVan extends ConditionalActivity {
    @NonNull private final SMRental model;
    private Van van = null;
    private Customer customer = null;

    public static boolean precondition(SMRental model){
       return canUnloadVan(model);
    }

    public void startingEvent(){
        Optional<Van> possibleVan = getVanForUnloading(model);
        if(!possibleVan.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No van present");
        van = possibleVan.get();
        Optional<Customer> possibleCustomer = van.removeNextCustomer();
        if(!possibleCustomer.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No customer present");
        customer = possibleCustomer.get();
        UDPs.UpdateVanStatus(van, VanStatus.EXITING);
    }

    public double duration() {
        return model.getRvp().uExitingTime(customer.getNumPassengers());
    }

    public void terminatingEvent(){
        if(van.getSeatsAvailable() == van.getCapacity()){
            UDPs.UpdateVanStatus(van, VanStatus.LOADING);
        } else {
            UDPs.UpdateVanStatus(van, VanStatus.UNLOADING);
        }
        if(customer.getUType() == CustomerType.NEW){
            model.getQRentalLine().offerLast(customer);
        } else {
            UDPs.HandleCustomerExit(customer);
        }
    }

    // Local User Defined Procedures
    private static boolean canUnloadVan(SMRental model){
        return model.getRgVans().stream()
                .anyMatch(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0);
    }

    private static Optional<Van> getVanForUnloading(SMRental model){
        return model.getRgVans().stream()
                .filter(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0)
                .findFirst();
    }
}
