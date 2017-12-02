package sm.rental.model.activities;

import lombok.Getter;
import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;
import sm.rental.model.procedures.DVPs;
import sm.rental.model.procedures.RVPs;
import sm.rental.model.procedures.UDPs;

public class Travel extends ConditionalActivity {
    @Getter SMRental model;
    UDPs udp;
    Van rgVan;
    DVPs dvp;

    public Travel(SMRental model){ this.model = model; }
    public static boolean precondition(SMRental model){
        boolean returnValue = false;

        if (model.udp.CanUnloadVan() == true){

            returnValue = true;
        };
        return returnValue;
    }
    public void startingEvent() {
        rgVan = this.model.udp.GetVanForTravel();
        VanLocation nextDestination = this.model.udp.GetNextDestinationForVan(rgVan);
        this.model.udp.UpdateVanStatus( rgVan, "TRAVELLING");
    }
    protected double duration(){

        return (this.model.dvp.travelTime( rgVan,nextDestination ));

    }
    public void terminatingEvent(){
        this.model.udp.UpdateVanLocation( rgVan,nextDestination);
        if( nextDestination.equals("RENTAL_COUNTER" || nextDestination.equals("DROP_OFF"))){
            if( rgVan.getSeatsAvailable() != rgVan.getCapacity()){
                this.model.udp.UpdateVanStatus( rgVan,"UNLOADING");
            }

        }
        else{
            this.model.udp.UpdateVanStatus( rgVan,"UNLOADING");
        }
    }






}
