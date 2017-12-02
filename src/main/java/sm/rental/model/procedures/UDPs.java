package sm.rental.model.procedures;

import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;

public class UDPs
{
	private static SMRental model;  // for accessing the clock
	
	// Constructor
	public static void ConfigureUDPs(SMRental smRental) { model = smRental; }

	public static void HandleCustomerExit(Customer customer) {
		//TODO: Implement after outputs
	}
	
}
