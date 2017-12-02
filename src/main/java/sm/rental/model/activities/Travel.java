package sm.rental.model.activities;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;

import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.*;
import sm.rental.model.procedures.UDPs;

import java.util.Optional;

@RequiredArgsConstructor
public class Travel extends ConditionalActivity {
    @NonNull private final SMRental model;
    private Van rgVan;
    private VanLocation nextDestination;

    public static boolean precondition(SMRental model){
        return canUnloadVan(model);
    }

    public void startingEvent() {
        Optional<Van> possibleVan = getVanForTravel(model);
        if(!possibleVan.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No van present");
        rgVan = possibleVan.get();
        nextDestination = getNextDestinationForVan(rgVan);
        UDPs.UpdateVanStatus(rgVan, VanStatus.TRAVELLING);
    }

    public double duration(){
        return model.getDvp().travelTime(rgVan.getLocation(), nextDestination );
    }

    public void terminatingEvent(){
        updateVanLocation(rgVan, nextDestination);
        if( nextDestination == VanLocation.RENTAL_COUNTER || nextDestination == VanLocation.DROP_OFF){
            if( rgVan.getSeatsAvailable() < rgVan.getCapacity())
                UDPs.UpdateVanStatus(rgVan, VanStatus.UNLOADING);
        } else UDPs.UpdateVanStatus(rgVan, VanStatus.LOADING);
    }

    //Local User Defined Procedures
    private static boolean canUnloadVan(SMRental model){
        return model.getRgVans().stream()
                .anyMatch(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0);
    }

    private static Optional<Van> getVanForTravel(SMRental model){
        return model.getRgVans().stream()
                .filter(van -> van.getStatus() == VanStatus.UNLOADING && van.getN() > 0)
                .findFirst();
    }

    private static VanLocation getNextDestinationForVan(Van van){
        switch (van.getLocation()){
            case TERMINAL1:
                return VanLocation.TERMINAL2;
            case TERMINAL2:
                return VanLocation.RENTAL_COUNTER;
            case RENTAL_COUNTER:
                if(van.getN() > 0)
                    return VanLocation.DROP_OFF;
                else
                    return VanLocation.TERMINAL1;
            case DROP_OFF:
                return VanLocation.TERMINAL1;
        }
        throw new IllegalStateException("Van Location doesn't exist");
    }

    private static void updateVanLocation(Van van, VanLocation destination){
        van.setLocation(destination);
    }

}
