package sm.rental.model.procedures;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Uniform;
import sm.rental.model.entities.Customer;
import sm.rental.model.SMRental;
import sm.rental.model.Seeds;

public class RVPs
{
	SMRental model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds
	private Exponential NCustomerT1;
	private Exponential NCustomerT2;
	private Exponential RCustomer;
	private Uniform RCServiceTime;
	private Uniform NCServiceTime;
	private MersenneTwister AdditionalPassengers;
	private Exponential BoardingTime;
	private Exponential ExitingTime;




	// Constructor
	public RVPs(SMRental model, Seeds sd)
	{ 
		this.model = model; 
		// Set up distribution functions
		//interArrDist = new Exponential(1.0/WMEAN1, new MersenneTwister(sd.seed1));
		NCustomerT1 = new Exponential(
				1.0/T1Means[0],
				new MersenneTwister(sd.getNewCustomerSeedT1()));

		NCustomerT2 = new Exponential(
				1.0/T2Means[0],
				new MersenneTwister(sd.getNewCustomerSeedT2()));

		RCustomer = new Exponential(1.0/RCMeans[0], new MersenneTwister(sd.getReturningCustomerSeed()));
		RCServiceTime = new Uniform(CIMax, CIMin, sd.getReturningCustomerServiceTimeSeed());
		NCServiceTime = new Uniform(COMax, COMin, sd.getNewCustomerServiceTimeSeed());
		AdditionalPassengers = new MersenneTwister(sd.getAdditionalPassengerSeed());
		BoardingTime = new Exponential(1.0/ avgBoardingTime, new MersenneTwister(sd.getBoardingTimeSeed()));
		ExitingTime = new Exponential(1.0/ avgExitTime, new MersenneTwister(sd.getExitTimeSeed()));

	}
	
	/* Random Variate Procedure for Arrivals */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double WMEAN1=10.0;
	protected double duInput()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return nxtInterArr + model.getClock();
	}
	// Mean arrival times
	private static final double [] T1Means = {
			15, 7.5, 2, 4, 3.33, 4.29, 4.62, 6, 15, 0, 6, 4.29, 3.75, 4, 8.57, 20, 15, 30 };

	public double DuNCustomerT1(){
		double nextCustomer;
		double t = model.getClock();
		double mean = getMean(T1Means, t);

		nextCustomer = t + this.NCustomerT1.nextDouble(1.0/mean);
		//missing if statement to determine if past closing time of the system, missing parameter

		return nextCustomer;
	}

	private static final double [] T2Means = {
			20, 10, 6.67, 4, 3.53, 3.16, 4.29, 10, 20, 0, 2.86, 4.29, 3.16, 5, 12, 30, 20, 20 };

	public double DuNCustomerT2(){
		double nextCustomer;
		double t = model.getClock();
		double mean = getMean(T2Means, t);
		nextCustomer = t + this.NCustomerT2.nextDouble(1.0/mean);
		//missing if statement to determine if past closing time of the system, missing parameter

		return nextCustomer;
	}

	private static final double [] RCMeans = {
			5, 6.67, 3.33, 2.14, 2.61, 2.86, 3.75, 5.45, 3.53, 0, 1.67, 2.5, 1.875, 3.75, 4.62, 4.62, 12, 15 };

	public double DuRCustomer(){
		double nextCustomer;
		double t = model.getClock();
		double mean = getMean(RCMeans, t);
		nextCustomer = t + this.RCustomer.nextDouble(1.0/mean);
		//missing if statement to determine if past closing time of the system, missing parameter
		return nextCustomer;
	}

	//Check in and check out, min and max times in minutes
	private static final double COMax = 5.1;
	private static final double COMin = 1.6;
	private static final double CIMax = 4.8;
	private static final double CIMin = 1;
	//Check in time is associated with returning customers
	//Check out time is associated with newe customers
	public double uServiceTime(Customer.CustomerType uType){
		double serviceTime = 0;
		if(uType == Customer.CustomerType.RETURNING){
			serviceTime = this.RCServiceTime.nextDouble();
		} else if(uType == Customer.CustomerType.NEW){
			serviceTime = this.NCServiceTime.nextDouble();
		}
		return serviceTime;
	}

	//percentage of additional passengers with customers
	private static final double Additional1 = 0.2;
	private static final double Additional2 = 0.15;
	private static final double Additional3 = 0.05;

	public int uNumPassengers(){
		double rand = this.AdditionalPassengers.nextDouble();
		int additionalPassengers = 0;
		if(rand < Additional3){
			additionalPassengers = 3;
		} else if(rand < Additional2){
			additionalPassengers = 2;
		} else if(rand < Additional1){
			additionalPassengers = 1;
		} else {
			additionalPassengers = 0;
		}
		return additionalPassengers;
	}

	private static final double avgBoardingTime = 0.2;

	public double uBoardingTime(int numPassengers){
		double boardingTime = 0;
		for(int i = 0; i < numPassengers; i++)
			boardingTime += this.BoardingTime.nextDouble();
		return boardingTime/60;
	}

	private static final double avgExitTime = 0.1;

	public double uExitingTime(int numPassengers){
		double exitTime = 0;
		for(int i = 0; i < numPassengers; i++)
			exitTime += this.ExitingTime.nextDouble();
		return exitTime/60;
	}

	public double getMean(double [] means, double t) {
		return means[(int)(t / 15)];
	}
}
