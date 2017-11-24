package  sm.rental.model.entities;

public class RentalCounter {

    private int unumAgents; //The number of available agents.
    private double cost; // The total cost for all rental agents in the system during one time of experiment.


    public RentalCounter(int unumAgents, double cost) {
        this.unumAgents = unumAgents;
        this.cost = cost;
    }

}