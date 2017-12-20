package sm.rental.model.procedures;

import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Customer.CustomerType;
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

    /**
     * Increments the numServed customers
     * If the customer is satisfied (Meets the corresponding turnaround time for the type of customer)
     * Updates the percent satisfied
     **/
    public static void HandleCustomerExit(Customer iCGCustomer) {

        double t = model.getClock();
	    if(iCGCustomer.getUType() == CustomerType.NEW){
            if((t - iCGCustomer.getStartTime()) <= ACCEPTABLE_N_TURNARROUNDT) model.getSsovs().addSatisfiedCust();
            else model.getSsovs().addUnsatisfiedCust();
        } else {
            if((t - iCGCustomer.getStartTime()) <= ACCEPTABLE_R_TURNARROUNDT) model.getSsovs().addSatisfiedCust();
            else model.getSsovs().addUnsatisfiedCust();
        }
	}

    public static void UpdateVanLocation(int vanid, VanLocation destination) {
        model.getRqVans()[vanid].addMileage(UDPs.GetDistanceTravelled(model.getRqVans()[vanid].getLocation(), destination));
        model.getRqVans()[vanid].setLocation(destination);
    }

    /**
     *  Determines if the specified van can unload at its location. A van can unload if the following conditions are met:
     *  If the van is at a location where it can drop off customers
     *  Van.Location=RENTAL_COUNTER or DROP_OFF
     *  The vans status is UNLOADING
     *  The van has customers
     **/
    public static boolean CanVanUnload(int vanid) {
        if(model.getRqVans()[vanid].getLocation() == VanLocation.DROP_OFF || model.getRqVans()[vanid].getLocation() == VanLocation.RENTAL_COUNTER)
            if(model.getRqVans()[vanid].getStatus() == VanStatus.UNLOADING)
                if(model.getRqVans()[vanid].getN()>0)
                    return true;
        return false;
    }

    /**
     *  Determines if the specified van can load a customer at its location. If the following conditions must be met:
     *  The vans status is LOADING
     *  UDP.GetCustomersAwaiting(Van.location) returns a Queue (awaitingQueue)
     *  UDP.GetCustomerForBoaring(awaitingQueue, Van.seatsAvailable) returns a Customer.
     **/
    public static boolean CanVanLoad(Van van) {
        if(van.getStatus() == VanStatus.LOADING) {
            Optional<LinkedList<Customer>> possibleQueue = GetAwaitingCustomers(van);
            if(possibleQueue.isPresent())
                return GetFirstAppropriateCustomer(possibleQueue.get(), van.getSeatsAvailable()).isPresent();
        }
        return false;
    }

    /**
     *  Returns the queue of customers waiting for pickup
     *  If location is Terminal1, return Q.Terminals[0]
     *  If location is Terminal2, return Q.Terminals[1]
     *  If location is RentalCounter, return Q.ReturnLine
     *  Otherwise return False
     **/
    public static Optional<LinkedList<Customer>> GetAwaitingCustomers(Van van) {
        switch (van.getLocation()){
            case TERMINAL1:
                if(van.getStatus() == VanStatus.LOADING) return Optional.of(model.getTerminals().get(0));
                else return Optional.empty();
            case TERMINAL2:
                if(van.getStatus() == VanStatus.LOADING) return Optional.of(model.getTerminals().get(1));
                else return Optional.empty();
            case RENTAL_COUNTER:
                if(van.getStatus() == VanStatus.LOADING) return Optional.of(model.getReturnLine());
                else return Optional.empty();
            case DROP_OFF:
                return Optional.empty();
        }
        throw new IllegalStateException("Location of Van doesn't exist");
    }

    /**
     *  Returns the first customer in the queue which can fit the specified amount of seats, otherwise returns false.
     *  A customer can fit if Customer.numPassengers <= seatsAvailable.
     **/
    public static Optional<Customer> GetFirstAppropriateCustomer(LinkedList<Customer> queue, int seatsAvailable) {
        return queue.stream()
                .filter(c -> c.getNumPassengers() <= seatsAvailable)
                .findFirst();
    }

    public static Optional<LinkedList<Customer>> GetLocationForBoarding(SMRental model, Van van) {
        if(van.getLocation() == VanLocation.TERMINAL1) return Optional.of(model.getTerminals().get(0));
        else if(van.getLocation() == VanLocation.TERMINAL2) return Optional.of(model.getTerminals().get(1));
        else if(van.getLocation() == VanLocation.RENTAL_COUNTER) return Optional.of(model.getReturnLine());
        else return Optional.empty();
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
}
