package sm.rental.model.procedures;

import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Van.*;

import static sm.rental.model.Constants.ACCEPTABLE_N_TURNARROUNDT;
import static sm.rental.model.Constants.ACCEPTABLE_R_TURNARROUNDT;

public class UDPs
{
	private static SMRental model;
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
}
