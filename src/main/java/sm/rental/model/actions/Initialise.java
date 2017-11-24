package sm.rental.model.actions;

import simulationModelling.*;
import simulationModelling.ScheduledAction;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;
import java.util.ArrayList;

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

		Arraylist<Van> vans = model.rgVans.getVans();
		int numVans = model.rgVans.getNumVans();
		vans.clear();
		for(int i=0; i<numVans; i++){
			model.rgVans[i].status= model.rgVans[i].status.LOADING_T1;
			model.rgVans.Van(12);
			model.rgVans[i].n=0 ;
			model.rgVans[i].mileage=0;
		}
		}
		// System Initialisation
		// Add initilisation instructions
	}
	

}
