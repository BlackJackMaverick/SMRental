package  sm.rental.model.entities;

public class RentalCounter {

    private int unumAgentBusy; //The number of agents who are serving customers.
    private double cost; // The total cost for all rental agents in the system during one time of experiment.


    public RentalCounter(int numAgentBusy, double c) {
        unumAgentBusy = numAgentBusy;
        cost = c;
    }
    public RentalCounter(){

    }
}