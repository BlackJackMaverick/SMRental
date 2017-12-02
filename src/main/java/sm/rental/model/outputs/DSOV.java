package sm.rental.model.outputs;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;

import static sm.rental.model.Constants.DRIVER_COST;
import static sm.rental.model.Constants.RA_COST;

@RequiredArgsConstructor
public class DSOV
{
    private static final double SHIFT = 4.5;
	@NonNull private final SMRental model;

	public double vanCost(){
	    return model.getRgVans().stream()
                .mapToDouble(
                Van::getMileage).sum() + (model.getNumVans() * DRIVER_COST * SHIFT);
    }

	public double overallCost(){
	    return vanCost() + (model.getNumRentalAgents() * RA_COST * SHIFT);
    }
}
