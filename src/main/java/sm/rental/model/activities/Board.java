package sm.rental.model.activities;

import static smrental.Constants.*;
import sm.rental.model.SMRental;
import sm.rental.model.entities.Van;
import sm.rental.model.entities.Customer;
import simulationModelling.ConditionalActivity;

public class Board extends ConditionalActivity{
    private SMRental model;
    private Customer CCustomer;
    private van CVan;

    public Board (SMRental model){
        this.model = model;
    }

    public static boolean precondition (SMRental model){
        return model.udp.CanBoardVan(CVan.VanStatus);
    }

    /* UDP function CanBoardVan()
    //need to import van and customer for this function
    //also need to set variables CCustomer and CVan
    //in customer.java, needs a getCapacity() function

    public boolean CanBoardVan(VanStatus status){
    boolean check = false;
        if(status == VanStatus.BOARDING_T1 || status == VanStatus.BOARDING_T2 || status == VanStatus.BOARDING_RC){
            if(CVan.getCapacity() >= CCustomer.numPassengers+1){
            check = true;
            }
            return check;
        }else{
        return check;
        }
    }
    */

    protected double duration(){
        return this.model.rvp.uBoardingTime(this.CCustomer.numPassengers);
    }

    public void eventStart(){
        this.CVan = this.model.udp.GetVanForBoarding();
        this.CCustomer = this.model.udp.GetCustomerForBoarding(CVan);
        this.model.udp.UpdateVanStatus(CVan,"BOARDING");
    }

    /*
    //UDP function GetVanForBoarding()
    public Van GetVanForBoarding(){

    }

    //UDP function GetCustomerForBoarding
    public Customer GetCustomerForBoarding(Van v){

    }

    //UDP function UpdateVanStatus(Van, "BOARDING")
    public void UpdateVanStatus(Van v, String s){
    if(s.equals("BOARDING")){
        v.VanStatus = VanStatus.BOARDING;
     }else if (s.equals("LOADING")){
       v.VanStatus = VanStatus.LOADING;
     }
    }

     */


    public void eventEnd(){
        this.model.udp.AddCustomerToVan(CVan, CCustomer);
        this.model.udp.UpdateVanStatus(CVan, "LOADING");
    }

    /*
    //UDP function AddCustomerToVan(Van, Customer)

    public void AddCustomerToVan(Van v, Customer c){
        v.insertGrp(c);
    }
     */

}
