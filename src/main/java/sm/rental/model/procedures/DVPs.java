package sm.rental.model.procedures;

import sm.rental.model.entities.Van.VanLocation;

import static sm.rental.model.Constants.VAN_SPEED;

public class DVPs {

    private static final double SIXTY_MINUTES = 60.0;

    public static double travelTime(VanLocation source, VanLocation destination){
        return distanceToTime(UDPs.GetDistanceTravelled(source,destination));
    }

    private static double distanceToTime(double distance){
        return distance/VAN_SPEED*SIXTY_MINUTES;
    }
}
