package sm.rental.model.procedures;

import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.*;

import java.util.LinkedList;
import java.util.Optional;

import static sm.rental.model.Constants.ACCEPTABLE_N_TURNARROUNDT;
import static sm.rental.model.Constants.ACCEPTABLE_R_TURNARROUNDT;

public class UDPs {
	private static SMRental model;
    private static final double DISTANCE_T1_T2 = 0.3;
    private static final double DISTANCE_T2_RC = 2.0;
    private static final double DISTANCE_RC_T1 = 1.5;
    private static final double DISTANCE_RC_DP = 1.7;
    private static final double DISTANCE_DP_T1 = 0.5;

	public static void ConfigureUDPs(SMRental smRental) {
	    model = smRental;
    }

    public static void HandleCustomerExit(Customer customer) {
        double t = model.getClock();
	    if(customer.getUType() == Customer.CustomerType.NEW){
            if(t - customer.getStartTime() <= ACCEPTABLE_N_TURNARROUNDT) model.getSsovs().addSatisfiedCust();
            else model.getSsovs().addUnsatisfiedCust();
        } else {
            if(t - customer.getStartTime() <= ACCEPTABLE_R_TURNARROUNDT) model.getSsovs().addSatisfiedCust();
            else model.getSsovs().addUnsatisfiedCust();
        }
	}

	public static void UpdateVanStatus(Van rgVan, VanStatus status){
        rgVan.setStatus(status);
    }

    public static double GetDistanceTravelled(VanLocation from, VanLocation to){
        switch (to){
            case TERMINAL1:
                if(from == VanLocation.RENTAL_COUNTER) return DISTANCE_RC_T1;
                else return DISTANCE_DP_T1;
            case TERMINAL2:
                return DISTANCE_T1_T2;
            case RENTAL_COUNTER:
                return DISTANCE_T2_RC;
            case DROP_OFF:
                return DISTANCE_RC_DP;
        }
        throw new IllegalStateException("Destination doesn't exist");
    }
    public static boolean CanCustomerBoardVan(SMRental model){
        Optional<Van> possibleVan = GetVanForBoarding(model);
        return possibleVan.isPresent() &&
                CanCustomerBoard(model, possibleVan.get());
    }
    public static boolean CanCustomerExitVan(SMRental model){
        Optional<Van> possibleVan = GetVanForBoarding(model);
        return possibleVan.isPresent() &&
                CanCustomerBoard(model, possibleVan.get());
    }

    public static Optional<Van> GetVanForBoarding(SMRental model){
        return model.getRgVans().stream()
                .filter(van -> van.getStatus() == VanStatus.LOADING)
                .findFirst();
    }
    public static boolean CanCustomerBoard(SMRental model, Van van) {
        Optional<LinkedList<Customer>> queue = GetLocationForBoarding(model, van);
        return queue.isPresent() &&
                queue.get().stream()
                        .anyMatch(customer -> customer.getNumPassengers() <= van.getSeatsAvailable());
    }

    public static Optional<LinkedList<Customer>> GetLocationForBoarding(SMRental model, Van van){
        if(van.getLocation() == VanLocation.TERMINAL1) return Optional.of(model.getQTerminals().get(0));
        else if(van.getLocation() == VanLocation.TERMINAL2) return Optional.of(model.getQTerminals().get(1));
        else if(van.getLocation() == VanLocation.RENTAL_COUNTER) return Optional.of(model.getQReturnLine());
        else return Optional.empty();
    }
}
