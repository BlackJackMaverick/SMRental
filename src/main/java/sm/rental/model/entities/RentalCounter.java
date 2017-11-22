package  sm.rental.model.entities;


import com.sun.org.apache.regexp.internal.RE;

public class RentalCounter {

    public int unumAgentBusy; //The number of agents who are serving customers.
    public double cost; // The total cost for all rental agents in the system during one time of experiment.


public RentalCounter(int numAgentBusy, double c){
    unumAgentBusy = numAgentBusy;
    cost = c;
}
public RentalCounter(){
}
}