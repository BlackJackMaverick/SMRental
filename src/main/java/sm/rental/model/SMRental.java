package sm.rental.model;

import java.util.*;

import lombok.Getter;
import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.RentalCounter;
import sm.rental.model.entities.Van;
import sm.rental.model.outputs.Output_1;
import sm.rental.model.procedures.DVPs;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.actions.Initialise;
import sm.rental.model.procedures.UDPs;


//
// The Simulation model Class
public class SMRental extends AOSimulationModel
{
	// Constants available from Constants class
	/* Parameter */
	@Getter int numSeats;
	@Getter int numVans;
	@Getter int numRentalAgents;

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue entities */
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	@Getter private ArrayList<Van> rgVans;
	@Getter private RentalCounter rgRentalCounter;

	@Getter private ArrayList<LinkedList<Customer>> qTerminals;
	@Getter private LinkedList<Customer> qReturnLine;
	@Getter private LinkedList<Customer> qRentalLine;

	// Objects can be created here or in the Initialise Action

	/* Input Variables */
	// Define any Independent Input Variables here
	
	// References to RVP and DVP objects
	@Getter private RVPs rvp;  // Reference to rvp object - object created in constructor
	@Getter private DVPs dvp = new DVPs(this);  // Reference to dvp object

	// Output_1 object
	@Getter protected Output_1 output1 = new Output_1(this);
	
	// Output_1 values - define the public methods that return values
	// required for experimentation.


	// Constructor
	public SMRental(double t0time, double tftime, int numSeats, int numVans, int numRentalAgents, Seeds sd) {
        // Setup procedures
        UDPs.ConfigureUDPs(this);
		// Initialise parameters here
		this.numSeats = numSeats;
		this.numVans = numVans;
		this.numRentalAgents = numRentalAgents;

		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// Create Structural Entities Corresponding to Resources
        qTerminals = new ArrayList<>();
        qTerminals.add(new LinkedList<Customer>());
        qTerminals.add(new LinkedList<Customer>());
        qReturnLine = new LinkedList<>();
        qRentalLine = new LinkedList<>();
        rgRentalCounter = new RentalCounter(numRentalAgents);
        rgVans = new ArrayList<>();

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
