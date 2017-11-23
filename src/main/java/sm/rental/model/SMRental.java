package sm.rental.model;

import java.util.*;
import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.RentalCounter;
import sm.rental.model.entities.Van;
import sm.rental.model.outputs.Output_1;
import sm.rental.model.procedures.DVPs;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.procedures.UDPs;
import sm.rental.model.actions.Initialise;


//
// The Simulation model Class
public class SMRental extends AOSimulationModel
{
	// Constants available from Constants class
	/* Parameter */
        // Define the parameters

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue entities */
	// Define the reference variables to the various 
	// entities with scope Set and Unary

	public Van [] rgVan = new Van[id]; // Don't know how to describe id.
	public RentalCounter rgRentalCounter = new RentalCounter();

	public ArrayList<Customer> Terminal1 = new ArrayList<Customer>();
	public ArrayList<Customer> Terminal2 = new ArrayList<Customer>();
	public ArrayList<Customer> ReturnLine = new ArrayList<Customer>();
	public ArrayList<Customer> RentalLine = new ArrayList<Customer>();
	// Objects can be created here or in the Initialise Action

	/* Input Variables */
	// Define any Independent Input Varaibles here
	
	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	protected DVPs dvp = new DVPs(this);  // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	// Output_1 object
	protected Output_1 output1 = new Output_1(this);
	
	// Output_1 values - define the public methods that return values
	// required for experimentation.


	// Constructor
	public SMRental(double t0time, double tftime, /*define other args,*/ Seeds sd)
	{
		// Initialise parameters here
		
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// rgCounter and qCustLine objects created in Initalise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		// Check preconditions of Conditional activities

		// Check preconditions of Interruptions in Extended activities
	}
	
	public void eventOccured()
	{
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output_1 class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 
	}

	// Standard Procedure to start Sequel activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}	

}


