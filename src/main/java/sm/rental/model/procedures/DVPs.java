package sm.rental.model.procedures;

import sm.rental.model.entities.Van.VanLocation;

import static sm.rental.model.Constants.VAN_SPEED;

public class DVPs
{
    private static final double DISTANCE_T1_T2 = 0.3;
    private static final double DISTANCE_T2_RC = 2.0;
    private static final double DISTANCE_RC_T1 = 1.5;
    private static final double DISTANCE_RC_DP = 1.7;
    private static final double DISTANCE_DP_T1 = 0.5;
    private static final double SIXTY_MINUTES = 60.0;

    public static double travelTime(VanLocation source, VanLocation destination){
        switch (destination){
            case TERMINAL1:
                if(source == VanLocation.RENTAL_COUNTER) return distanceToTime(DISTANCE_RC_T1);
                else return distanceToTime(DISTANCE_DP_T1);
            case TERMINAL2:
                return distanceToTime(DISTANCE_T1_T2);
            case RENTAL_COUNTER:
                return distanceToTime(DISTANCE_T2_RC);
            case DROP_OFF:
                return distanceToTime(DISTANCE_RC_DP);
        }
        throw new IllegalStateException("Destination doesn't exist");
    }

    private static double distanceToTime(double distance){
        return distance/VAN_SPEED*SIXTY_MINUTES;
    }
}
