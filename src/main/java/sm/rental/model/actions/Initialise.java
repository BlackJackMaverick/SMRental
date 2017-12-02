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
		ArrayList<Van> vans = model.getRgVans();
		int numVans = model.getNumVans();
		int vanCapacity = model.getVanCapacity();
		for(int i=0; i<numVans; i++) vans.add(new Van(vanCapacity));
	}
}
