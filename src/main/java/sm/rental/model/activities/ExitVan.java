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

@RequiredArgsConstructor
public class ExitVan extends ConditionalActivity{
    @NonNull private final SMRental model;
    private Van rgVan = null;
    private Customer icgCustomer = null;

    public static boolean precondition(SMRental model){
       return canUnloadVan(model);
    }

    public void startingEvent(){
        rgVan = getVanForUnloading();
        icgCustomer = rgVan.removeNextCustomer();
        UDPs.UpdateVanStatus(rgVan, VanStatus.EXITING);
    }

    public double duration() {
        return model.getRvp().uExitingTime(icgCustomer.getNumPassengers());
    }

    public void terminatingEvent(){
        if(rgVan.getSeatsAvailable() == rgVan.getCapacity()){
            UDPs.UpdateVanStatus(rgVan, VanStatus.LOADING);
        } else {
            UDPs.UpdateVanStatus(rgVan, VanStatus.UNLOADING);
        }
        if(icgCustomer.getUType() == CustomerType.NEW){
            model.getQRentalLine().offerLast(icgCustomer);
        } else {
            UDPs.HandleCustomerExit(icgCustomer);
        }
    }

    // Local User Defined Procedures
    private static boolean canUnloadVan(SMRental model){
        return model.getRgVans().stream()
                .anyMatch(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0);
    }

    private Van getVanForUnloading(){
        return model.getRgVans().stream()
                .filter(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0)
                .findFirst().get();
    }
}
