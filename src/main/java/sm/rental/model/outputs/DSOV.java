package sm.rental.model.outputs;

import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;

import static sm.rental.model.Constants.DRIVER_COST;
import static sm.rental.model.Constants.RA_COST;

public class DSOV
{
    private static double SHIFT = 4.5;
	SMRental model;

	public DSOV(SMRental md) { model = md; }

	public double vanCost(){
	    return model.getRgVans().stream()
                .mapToDouble(
                Van::getMileage).sum() + (model.getNumVans() * DRIVER_COST * SHIFT);
    }

	public double overallCost(){
	    return vanCost() + (model.getNumRentalAgents() * RA_COST * SHIFT);
    }
}
