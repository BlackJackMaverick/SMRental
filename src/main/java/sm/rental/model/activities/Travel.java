package sm.rental.model.activities;

import simulationModelling.ConditionalActivity;
import sm.rental.model.SMRental;

public class Travel extends ConditionalActivity
   {
   private SMRental model;
//   private Location origin;
//   private Location destination;
   private int vanId;

   public Travel(SMRental model) {
      this.model = model;
   }

   public static boolean precondition(SMRental model)
   {
      return false;
   }

   @Override
   public void startingEvent()
   {
   }

   @Override
   protected double duration()
   {
      return 0.0;
   }

   @Override
   protected void terminatingEvent()
   {
   }
}
