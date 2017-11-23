package sm.rental.model.actions;

import simulationModelling.*;
import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;

public class Initialise extends ScheduledAction
{
	SMRental model;
	
	// Constructor
	public Initialise(SMRental model) { this.model = model; }

	double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0;  // set index to first entry.
	protected double timeSequence() 
	{

		return ts[tsix++];  // only invoked at t=0
	}

	protected void actionEvent() 
	{
		model.RentalLine.clear();
		model.ReturnLine.clear();
		model.Terminal1.clear();
		model.Terminal2.clear();

		int id;
		for(id=1; id<=  ;id++) {
			// Don't know the value of id.
			model.rgVan[id].status= model.rgVan[id].status.LOADING_T1;
			model.rgVan[id].capacity=model.rgVan[id].vanCapacity;
			model.rgVan[id].n=0 ;
			model.rgVan[id].mileage=0;
		}

		// System Initialisation
		// Add initilisation instructions
	}
	

}
