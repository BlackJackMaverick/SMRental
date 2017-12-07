package sm.rental.model.outputs;

import lombok.*;
import sm.rental.model.SMRental;

@ToString(exclude = "model")
@RequiredArgsConstructor
public class SSOV {
	@NonNull private final SMRental model;

	@Getter private int numSatisfied = 0;
	@Getter private int numServed = 0;
	@Getter private Double customerSatisfactionRate = new Double(0.0);

	public void addSatisfiedCust(){
		numSatisfied++;
		numServed++;
		customerSatisfactionRate = new Double(numSatisfied / (double)numServed);
	}

	public void addUnsatisfiedCust(){
		numServed++;
		customerSatisfactionRate = new Double(numSatisfied / (double)numServed);
	}
}
