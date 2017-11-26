package  sm.rental.model.entities;

public class RentalCounter {

    private int uNumAgents; //The number of available agents.

    public RentalCounter(int unumAgents) {
        this.uNumAgents = unumAgents;
    }
    public boolean isAgentAvailable(){  // returns true is numagents is not zero
        return uNumAgents > 0;
    }
    public void occupyAgent() throws RuntimeException { // decrease the number of agents by one, if there is 0 agents, error
        if(this.uNumAgents == 0) throw new RuntimeException();
        this.uNumAgents -= 1;
    }
    public void freeAgent(){ // increases the amount of available agents by one
        this.uNumAgents += 1;
    }
}