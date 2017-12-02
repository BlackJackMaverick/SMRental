package sm.rental.model.procedures;

import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;

public class DVPs
{
	SMRental model;  // for accessing the clock
	
	// Constructor
	public DVPs(SMRental model) { this.model = model; }

	// Translate deterministic value procedures into methods
        /* -------------------------------------------------
	                       Example
	protected double getEmpNum()  // for getting next value of EmpNum(t)
	{
	   double nextTime;
	   if(model.clock == 0.0) nextTime = 90.0;
	   else if(model.clock == 90.0) nextTime = 210.0;
	   else if(model.clock == 210.0) nextTime = 420.0;
	   else if(model.clock == 420.0) nextTime = 540.0;
	   else nextTime = -1.0;  // stop scheduling
	   return(nextTime);
	}
	------------------------------------------------------------*/

    public double travelTime(Van.VanLocation source, Van.VanLocation destination){
        return 0.0;
    }
}
