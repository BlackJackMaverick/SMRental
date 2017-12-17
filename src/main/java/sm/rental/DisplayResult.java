package sm.rental;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class DisplayResult {
    @Getter private final List<List<Result>> case1;
    @Getter private final List<List<Result>> case2;
    @Getter private final List<List<Result>> case3;
    @Getter private final List<List<Result>> improvedCase1;
    @Getter private final List<List<Result>> improvedCase2;
    @Getter private final List<List<Result>> improvedCase3;


    public DisplayResult(List<List<Result>> c1, List<List<Result>> c2, List<List<Result>> c3,
                         List<List<Result>> ic1, List<List<Result>> ic2, List<List<Result>> ic3){
        this.improvedCase1 = ic1;
        this.improvedCase2 = ic2;
        this.improvedCase3 = ic3;
        this.case1=c1;
        this.case2=c2;
        this.case3=c3;
    }

    public String ShowTable(List<List<Result>> showMe){
        StringBuilder s = new StringBuilder();
        int runNumber;
        s.append("Run Number \t Van Capacity \t Customers Served \t Customer Satisfaction \t Cost");
        s.append("\n");

        for (List <Result> i:showMe) {
            runNumber= 1;
            for (Result j:i) {
                s.append(runNumber++);
                s.append("\t\t\t\t");
                s.append(j.getCapacity());
                s.append("\t\t\t\t\t");
                s.append(j.getCustomersServed());
                s.append("\t\t\t\t\t");
                s.append(String.format("%.2f", j.getSatisfactionRate()));
                s.append("\t\t\t ");
                s.append(j.getOverAllCost());
                s.append("\n");
            }
        }

        return s.toString();
    }

    public String ShowDifferenceTable(List<List<Result>> difference1, List<List<Result>> difference2){
        StringBuilder s = new StringBuilder();
        int runNumber=1;
        s.append("Run Number \t Van Capacity \t Customers Served \t Customer Satisfaction \t Cost");
        s.append("\n");

        for (int i = 0; i < difference1.size(); i++) {
            runNumber=1;
            for (int j = 0; j < difference1.get(i).size(); j++) {
                s.append(runNumber);
                s.append("\t\t\t\t");
                s.append(difference1.get(i).get(j).getCapacity() - difference2.get(i).get(j).getCapacity());
                s.append("\t\t\t\t\t");
                s.append(difference1.get(i).get(j).getCustomersServed() - difference2.get(i).get(j).getCustomersServed());
                s.append("\t\t\t\t\t");
                s.append(String.format("%.2f",difference1.get(i).get(j).getSatisfactionRate() - difference2.get(i).get(j).getSatisfactionRate()));
                s.append("\t\t\t ");
                s.append(difference1.get(i).get(j).getOverAllCost() - difference2.get(i).get(j).getOverAllCost());
                s.append("\n");
                runNumber++;
            }
        }
            return s.toString();
        }
//
//        for (List <Result> d1:difference1) {
//            for (List<Result> d2 : difference2) {
//                runNumber = 1;
//                for (Result j : d1) {
//                    for (Result k : d2) {
//                        s.append(runNumber++);
//                        s.append("\t\t\t\t");
//                        s.append(j.getCapacity()-k.getCapacity());
//                        s.append("\t\t\t\t\t");
//                        s.append(j.getCustomersServed()-k.getCustomersServed());
//                        s.append("\t\t\t\t\t");
//                        s.append(String.format("%.2f", j.getSatisfactionRate()-k.getSatisfactionRate()));
//                        s.append("\t\t\t ");
//                        s.append(j.getOverAllCost()-k.getOverAllCost());
//                        s.append("\n");
//                    }
//                }
//            }
//        }

//        return s.toString();
    }

