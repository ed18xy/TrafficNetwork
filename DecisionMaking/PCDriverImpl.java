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

  public PCDriverImpl(Vehicle v) {
    vehicle = v;
  }

  /**
   * NPC cars look around and evaluate possible moves
   */
  public void look() {
    availableMoves.clear();
    availableMoves.add(0);
    if (vehicle.currentLane.getCurrentVehicles().indexOf(vehicle) == 9) {
      chooseIntersectionMove();
    } else if (vehicle.currentRoad.getLanes().size() > 1) {
      chooseMidlaneMove();
    }
  }

  /**
   * Record available turns at the current intersection
   */
  public void chooseIntersectionMove() {
    for (Turn turn : vehicle.currentRoad.to.getAvailableTurns()) {
      if (turn.direction == Direction.North)
        availableMoves.add(2);
      if (turn.direction == Direction.East)
        availableMoves.add(3);
      if (turn.direction == Direction.West)
        availableMoves.add(4);
      if (turn.direction == Direction.South)
        availableMoves.add(5);
    }
  }

  /**
   * If the lane at the left edge -> right switch available
   * If the lane at the right edge -> left switch available
   */
  public void chooseMidlaneMove() {
    availableMoves.add(0);
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
   * @param map
   */
  public void chooseNextMove(Map map){
    do{
      nextMove = (int) (Math.random() * (availableMoves.size()-1));
    }while(!verify(nextMove, map, vehicle));
    makeMove(nextMove, map, vehicle);
  }
}