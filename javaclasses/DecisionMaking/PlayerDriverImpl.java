package DecisionMaking;

import Map.Map;

import Vehicles.Vehicle;

public class PlayerDriverImpl implements Driver {
  
  protected Vehicle vehicle;
  public boolean tryingDangerouMove = false;

  /**
   * Show current position ofg the vehicle
   * Display state of the RoadSegment
   * Show all lanes
   * Display distance to the next intersection
   */
  public void look() {
    tryingDangerouMove = false;//reset every time
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public boolean verifyPlayerDesicion(String decision, Map map){
    return verify(decision, map, vehicle);
  }

  /**
   * Asks if player wants to gamble nd try to make a dangerous move
   * that might cause an accident
   */
  public boolean gamble() {
    tryingDangerouMove = true;
    return false;
  }
  

}