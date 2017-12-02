package sm.rental.model.activities;


import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Customer;
import sm.rental.model.entities.Van;
import sm.rental.model.procedures.UDPs;

public class ExitVan extends ConditionalActivity{
    SMRental model;
    Van rgVan;
    Customer icgCustomer;
    UDPs udp;
    public ExitVan(SMRental model){ this.model = model; }
    public static boolean precondition(SMRental model){
        boolean returnValue = false;

       if (model.udp.CanUnloadVan() == true){
           returnValue = true;
       };
        return returnValue;
    }
    public void startingEvent(){
        rgVan= model.udp.GetVanForUnloading();
        icgCustomer= model.udp.GetCustomerForUnloading(rgVan);
        model.udp.UpdateVanStatus(rgVan, "EXITING");
    }
    public double duration()
    {
        return model.rvp.uExitingTime(icgCustomer.getNumPassengers());
    }
    public void terminatingEvent(){
        model.udp.RemoveCustomerFromVan(rgVan, icgCustomer);
        if(rgVan.getSeatsAvailable()==rgVan.getCapacity()){
            model.udp.UpdateVanStatus(rgVan,"LOADING");
        }
        else {
            model.udp.UpdateVanStatus(rgVan, "UNLOADING");
        }
        if(icgCustomer.getUType().equals("NEW")){
            model.getRentalLine().add(icgCustomer);
        }
        else{
        model.udp.HandleCustomerExit(icgCustomer);
        }
    }




}
