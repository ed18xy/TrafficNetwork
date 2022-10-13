package Player;

import Map.Map;
import DecisionMaking.PlayerDriverImpl;
import Vehicles.Vehicle;

public class Player extends PlayerDriverImpl {


  /**
   * Player constructor 
   * Assigns specified vehicle to player
   * @param vehicle
   */
  public Player(Vehicle vehicle) {
    this.vehicle = vehicle;
  }
  /**
   * Updates player and advances them the next  position
   * Checks if player have arrived to the final destination
   */
  public void updater(Map map, String nextMove){
    makeMove(nextMove, map, vehicle);
  }

}