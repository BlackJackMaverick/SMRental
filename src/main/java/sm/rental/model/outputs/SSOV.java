package sm.rental.model.outputs;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import sm.rental.model.SMRental;

@RequiredArgsConstructor
public class SSOV
{
	@NonNull private final SMRental model;

	@Getter private int numSatisfied = 0;
	@Getter private int numServed = 0;
	@Getter private double customerSatisfactionRate =0.0;

	public void addSatisfiedCust(){
		numSatisfied++;
		numServed++;
		customerSatisfactionRate = (double)numSatisfied/(double)numServed;
	}

	public void addUnsatisfiedCust(){
		numServed++;
		customerSatisfactionRate = (double)numSatisfied/(double)numServed;
	}
}
