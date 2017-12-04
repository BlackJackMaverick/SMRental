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

import static java.util.stream.Collectors.toList;

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
        return model.getClock() + DVPs.travelTime(van.getLocation(), nextDestination);
    }

    public void terminatingEvent(){
        updateVanLocation(van, nextDestination);
        if( nextDestination == VanLocation.RENTAL_COUNTER || nextDestination == VanLocation.DROP_OFF){
            if( van.getSeatsAvailable() < van.getCapacity())
                UDPs.UpdateVanStatus(van, VanStatus.UNLOADING);
        } else UDPs.UpdateVanStatus(van, VanStatus.LOADING);
    }

    //Local User Defined Procedures
    private static boolean canUnloadVan(Van van){
        return van.getStatus() == VanStatus.UNLOADING && van.getN() > 0;
    }

    private static Optional<Van> getVanForTravel(SMRental model){
        return model.getRgVans().stream()
                .filter( v ->!UDPs.CanCustomerBoard(model, v) && !canUnloadVan(v) && v.getStatus() != VanStatus.TRAVELLING)
                .findFirst();
    }

    private static VanLocation getNextDestinationForVan(Van van){
        VanLocation newLocation = null;
        switch (van.getLocation()){
            case TERMINAL1:
                newLocation = VanLocation.TERMINAL2;
                break;
            case TERMINAL2:
                newLocation = VanLocation.RENTAL_COUNTER;
                break;
            case RENTAL_COUNTER:
                if(van.getN() > 0)
                    newLocation = VanLocation.DROP_OFF;
                else
                    newLocation = VanLocation.TERMINAL1;
                break;
            case DROP_OFF:
                newLocation = VanLocation.TERMINAL1;
                break;
            default:
                throw new IllegalStateException("Van Location doesn't exist");
        }
        return newLocation;
    }

    private static void updateVanLocation(Van van, VanLocation destination){
        van.addMileage(UDPs.GetDistanceTravelled(van.getLocation(), destination));
        van.setLocation(destination);
    }

    // Predicate
    public static final Function<SMRental,Optional<ConditionalActivity>> function = (SMRental model) -> {
        if(Travel.precondition(model)){
            return Optional.of(new Travel(model));
        }
        else return Optional.empty();
    };
}
