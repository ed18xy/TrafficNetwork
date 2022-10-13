package DecisionMaking;

import Map.Map;
import Map.Turn;
import java.util.ArrayList;
import GameEngine.Direction;
import Vehicles.Vehicle;

public class PCDriverImpl implements Driver {

  public Vehicle vehicle;
  private ArrayList<Integer> availableMoves = new ArrayList<>();
  private int nextMove;
  private Map map;

  public PCDriverImpl(Vehicle v, Map m) {
    vehicle = v;
    map =  m;
  }

  /**
   * NPC cars look around and evaluate possible moves
   */
  public void look() {
    availableMoves.clear();
    if (vehicle.currentLane.getCurrentVehicles().indexOf(vehicle) == 9) {
      chooseIntersectionMove();
    } else if (vehicle.currentRoad.getLanes().size() > 1) {
      chooseMidlaneMove();
    }else{
      availableMoves.add(1);
    }
  }

  /**
   * Record available turns at the current intersection
   */
  public void chooseIntersectionMove() {
    if(vehicle.currentRoad.toLane.getAvailableTurns().isEmpty())availableMoves.add(0);
    for (Turn turn : vehicle.currentRoad.toLane.getAvailableTurns()) {
      if (turn.direction == Direction.North)
        availableMoves.add(2);
      else if (turn.direction == Direction.East)
        availableMoves.add(3);
      else if (turn.direction == Direction.West)
        availableMoves.add(4);
      else if (turn.direction == Direction.South)
        availableMoves.add(5);
    }
  }

  /**
   * If the lane at the left edge -> right switch available
   * If the lane at the right edge -> left switch available
   */
  public void chooseMidlaneMove() {
    availableMoves.add(1);
    if (vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane) == 0)
      availableMoves.add(6);
    else if (vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane) == vehicle.currentRoad.getLanes().size() - 1)
      availableMoves.add(7);
    else {
      availableMoves.add(6);
      availableMoves.add(7);
    }
  }

  /**
   * Computer cars do not take risky turns
   */
  public boolean gamble() {
    return false;
  }

  /**
   * Look for a safe move
   * The block is syncronized because it changes the Map
   * @param map
   */
  public void chooseNextMove() {
    int index = (int) (Math.random() * (availableMoves.size() - 1));
    nextMove = availableMoves.get(index);
    synchronized(this){if (!verify(Integer.toString(nextMove), map, vehicle))
      nextMove = 0;
    makeMove(Integer.toString(nextMove), map, vehicle);}
  }
}