// File: Experiment.java
// Description:
package sm.rental;

import sm.rental.model.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i, NUMRUNS = 30; 
       double startTime=0.0, endTime=660.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMRental mname;  // Simulation object
       int capacity = 12;
       int numVans = 3;
       int numRentalAgents = 99;

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       System.out.println(" Case 1");
       for(i=0 ; i < NUMRUNS ; i++)
       {
          mname = new SMRental(startTime,endTime,vanCapacity,numVans,numRentalAgents,sds[i]);
          mname.runSimulation();
       }
   }
}
