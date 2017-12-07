package sm.rental.model.activities;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;

import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.*;
import sm.rental.model.procedures.DVPs;
import sm.rental.model.procedures.UDPs;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class Travel extends ConditionalActivity {
    @NonNull private final SMRental model;
    private Van van = null;
    private VanLocation nextDestination = null;

    public static boolean precondition(SMRental model){
        return getVanForTravel(model).isPresent();
    }

    public void startingEvent() {
        Optional<Van> possibleVan = getVanForTravel(model);
        if(!possibleVan.isPresent())
            throw new RuntimeException("Event Started but precondition must've been false: No van present");
        van = possibleVan.get();
        nextDestination = getNextDestinationForVan(van);
        UDPs.UpdateVanStatus(van, VanStatus.TRAVELLING);
    }

    public double duration(){
        return DVPs.travelTime(van.getLocation(), nextDestination);
    }

    public void terminatingEvent(){
        UDPs.UpdateVanLocation(van, nextDestination);
        if(( nextDestination == VanLocation.RENTAL_COUNTER || nextDestination == VanLocation.DROP_OFF )
                && van.getN() != 0)
                van.setStatus(VanStatus.UNLOADING);
        else UDPs.UpdateVanStatus(van, VanStatus.LOADING);
    }

    //Local User Defined Procedures
    /**
     * A van cannot load anymore customers at its location and
     * the van cannot unload anymore customers at its location
     **/
    private static Optional<Van> getVanForTravel(SMRental model){
        return model.getVans().stream()
                .filter(van -> van.getStatus() == VanStatus.LOADING && (!UDPs.CanVanLoad(van)))
                .findFirst();
    }

    /**
     * If the van is at Terminal 1, then it will go to Terminal 2
     * If the van is at Terminal 2, then it will go to the Rental Counter
     * If the van is located at rental counter, then if it has 0 customer, then it will go to terminal 1. Otherwise it will go to the drop off point.
     * If the van is at the drop off point, then it will go to terminal 1
     * Otherwise Throws error
     **/
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
            default:
                throw new IllegalStateException("Van Location doesn't exist");
        }
    }

    // Predicate
    public static final Function<SMRental, Optional<ConditionalActivity>> function = (SMRental model) -> {
        if(Travel.precondition(model))
            return Optional.of(new Travel(model));
        else return Optional.empty();
    };
}
