package  sm.rental.model.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;

@Data
@RequiredArgsConstructor
public class Van {
    private static enum VansStatus {
                        BOARDING_T1,
                        BOARDING_T2,
                        BOARDING_RC,
                        EXITING_DP,
                        EXITING_RC,
                        TRAVELLING_TO_T1_FROM_DP,
                        TRAVELLING_TO_T1_FROM_RC,
                        TRAVELLING_TO_T2,
                        TRAVELLING_TO_RC,
                        TRAVELLING_TO_DP,
                        LOADING_T1,
                        LOADING_T2,
                        LOADING_RC,
                        UNLOADING_RC,
                        UNLOADING_DP
    };
    @Getter private VansStatus status;
    @Getter private double mileage = 0; //Total number of miles driven by the van in the observation interval
    private LinkedList<Customer> group = new LinkedList<>();
    private final int numSeats;

    // Required methods to manipulate the group
    public void insertGrp(Customer icgCustomer) {
        group.offerLast(icgCustomer);
    }
    public Customer removeGrp() {
        return group.pop();
    }
    public int getN() {
        return group.size();
    }

    public void addMileage(double milesTravelled){
        this.mileage += milesTravelled;
    }

}
