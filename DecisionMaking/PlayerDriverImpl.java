package DecisionMaking;

import Map.Map;

import java.util.Scanner;
import Map.Lane;
import Vehicles.Vehicle;

public class PlayerDriverImpl implements Driver {
  
  protected Vehicle vehicle;
  private Scanner userScanner = new Scanner(System.in);

  /**
   * Show current position ofg the vehicle
   * Display state of the RoadSegment
   * Show all lanes
   * Display distance to the next intersection
   */
  public void look() {
    System.out.println("Vehicle condition: "+vehicle.condition);
    System.out.println("Current road segment: FROM: " + vehicle.currentRoad.from.name + " TO:" + vehicle.currentRoad.to.name);
    for (Lane l : vehicle.currentRoad.getLanes()) {
      for (int i = 0; i < 10; i++) {
        if (l.getCurrentVehicles().size() > i && l.getCurrentVehicles().get(i) != null) {
          System.out.print(l.getCurrentVehicles().get(i) + "; ");
        } else
          System.out.print(" _ ");
      }
      System.out.println();
    }
    System.out.println(
        "You are " + (9 - vehicle.currentLane.getCurrentVehicles().indexOf(vehicle)) + " km away from the next intersection");
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public boolean verifyPlayerDesicion(int decision, Map map){
    return verify(decision, map, vehicle);
  }

  /**
   * Asks if player wants to gamble nd try to make a dangerous move
   * that might cause an accident
   */
  public boolean gamble() {
    try {
      System.out.println("Would you like to gamble? (Y for yes, N for no)");
      String answer = userScanner.nextLine();
      if (answer.equals("Y"))
        return true;
      else if (answer.equals("N"))
        return false;
      else
        wrongInput(answer);
    } catch (Exception e) {
      System.out.println("Gambling broke: " + e.toString());
    }
    return false;
  }

  

}