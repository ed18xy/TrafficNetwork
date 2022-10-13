package DecisionMaking;

import GameEngine.Direction;
import GameEngine.WrongInputException;
import Map.Lane;
import Map.Map;
import Map.RoadSegment;
import Map.Turn;
import Vehicles.Vehicle;

public interface Driver {

  final int standardDamage = 10;

  public void look();

  /**
   * checks if the move is available and the lane has space
   * and the next spot is clear (the car in front might be staying and waiting for
   * the intersection to clear)
   * If there is not enough space it asks player if they want to gamble
   * 
   * @param decision input from player
   * @param map      current map
   * @return true if move can be made safely
   */
  public default boolean verify(String decision, Map map, Vehicle vehicle) {
    try {
      int vehicleIndex = vehicle.currentLane.getCurrentVehicles().indexOf(vehicle);
      int totalLanes = vehicle.currentRoad.getLanes().size();
      int currentLaneInd = vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane);
      switch (decision) {
        case "0": // staying still should always be a valid option
          return true;
        case "1": // Keep straight
          return (vehicleIndex != 9 && (checkEmptySpot(vehicle.currentLane, vehicleIndex + 1, vehicle) || gamble()))
              ? true
              : false;
        case "2": // Turn Norths
          if (vehicleIndex == 9) {
            for (Turn t : vehicle.currentRoad.toLane.getAvailableTurns()) {
              if (t.direction == Direction.North && (checkTurn(map, Direction.North, vehicle) || gamble()))
                return true;
            }
            return false;
          } else
            return false;
        case "3": // Turn East
        if (vehicleIndex == 9) {
          for (Turn t : vehicle.currentRoad.toLane.getAvailableTurns()) {
            if (t.direction == Direction.East && (checkTurn(map, Direction.East, vehicle) || gamble()))
              return true;
          }
          return false;
        } else
          return false;
        case "4": // Turn West
          if (vehicleIndex == 9) {
            for (Turn t : vehicle.currentRoad.toLane.getAvailableTurns()) {
              if (t.direction == Direction.West && (checkTurn(map, Direction.West, vehicle) || gamble()))
                return true;
            }
            return false;
          } else
            return false;
        case "5": // Turn South
          if (vehicleIndex == 9) {
            for (Turn t : vehicle.currentRoad.toLane.getAvailableTurns()) {
              if (t.direction == Direction.South && (checkTurn(map, Direction.South, vehicle) || gamble()))
                return true;
            }
            return false;
          } else
            return false;
        case "6": // Switch to the right lane
          return (vehicleIndex != 9
              && totalLanes > 1
              && currentLaneInd < totalLanes + 1
              && (checkEmptySpot(vehicle.currentRoad.getLanes().get(currentLaneInd + 1), vehicleIndex + 1, vehicle)
                  || gamble()))
                      ? true
                      : false;
        case "7": // Switch to the left lane
          return (vehicleIndex != 9
              && totalLanes > 1
              && currentLaneInd > 0
              && (checkEmptySpot(vehicle.currentRoad.getLanes().get(currentLaneInd - 1), vehicleIndex + 1, vehicle)
                  || gamble()))
                      ? true
                      : false;
        default:
          wrongInput(decision);
      }
    } catch (Exception e) {
      System.out.println("Verification broke:" + e.toString());
    }
    return false;
  }

  /**
   * Checks if the lane has spot empty and if the lane has space
   * @param l
   * @param vehicleIndex
   * @return
   */
  public default boolean checkEmptySpot(Lane l, int vehicleIndex, Vehicle vehicle) {
    int laneSize = l.getCurrentVehicles().size();
    if (laneSize < 10 && l.getAvailableSpace() > vehicle.length) {
      if (laneSize < vehicleIndex + 1)
        return true;
      else if (l.getCurrentVehicles().get(vehicleIndex) == null)
        return true;
      else
        return false;
    } else
      return true;
  }

  /**
   * Returns true if there is enough space in the lane where the vehicle is trying
   * to go
   * 
   * @param map
   * @param direction
   * @return boolean
   */
  public default boolean checkTurn(Map map, Direction direction, Vehicle vehicle) {
    for (RoadSegment rs : map.getRoadSegments()) {
      if (rs.fromLane.name.equals(vehicle.currentRoad.toLane.name) && rs.getLanes().get(0).direction == direction) {
        int currentLaneIndex = vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane);
        if (rs.getLanes().size() > currentLaneIndex
            && (rs.getLanes().get(currentLaneIndex).getAvailableSpace() > vehicle.length)
            || checkEmptySpot(rs.getLanes().get(currentLaneIndex), -1, vehicle))
          return true;
      }
    }
    return false;
  }

  /**
   * Use of a custom Exception class
   * @param <T>    type of input (String/int)
   * @param uInput last input from user
   * @throws Exception wrong intput
   */
  public default <T> void wrongInput(T uInput) throws Exception {
    throw new WrongInputException("You entered: '" + uInput + "', which is not a valid option!");
  }

  public boolean gamble();

  /**
   * When accident occurs cars get damage and their condition is update
   * Damage is based on weight and reputation
   * Player loses reputation
   * Everyone stays on their places
   * if Player's car breaks, game ends
   * 
   * @param crashLane lane where accident happened
   * @param location  position where two vehicles tried to go
   */
  public default void collision(Lane crashLane, int location, Vehicle vehicle) {
    Vehicle otherVehicle = crashLane.getCurrentVehicles().get(location);
    int damageV = (int) (standardDamage + 0.2 * vehicle.weight + 0.2 * otherVehicle.weight
        + 0.3 * vehicle.reputationValue);
    int damageV2 = (int) (standardDamage + 0.2 * vehicle.weight + 0.2 * otherVehicle.weight
        + 0.3 * otherVehicle.reputationValue);
    vehicle.takeDamage(damageV);
    otherVehicle.takeDamage(damageV2);
  }

  /**
   * Try to make a move that player choose
   * results in succesfull movement or collision
   */
  public default void makeMove(String nextMove, Map map, Vehicle vehicle) {
    int vehicleCurrent = vehicle.currentLane.getCurrentVehicles().indexOf(vehicle);
    int laneIndex = vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane);
    int vehicleNext;
    switch (nextMove) {
      case "0": // Stay still
        break;// always should be a safe option
      case "1": // Keep straight
        vehicleNext = vehicleCurrent + 1;
        if (vehicle.currentLane.getCurrentVehicles().size() <= vehicleNext
            || vehicle.currentLane.getCurrentVehicles().get(vehicleNext) == null) {
          vehicle.move(vehicle.currentRoad, vehicle.currentLane, vehicleNext);
        } else
          collision(vehicle.currentLane, vehicleNext, vehicle);
        break;
      case "2": // Turn North
        tryTurn(map, Direction.North, vehicle);
        break;
      case "3": // Turn East
        tryTurn(map, Direction.East, vehicle);
        break;
      case "4": // Turn West
        tryTurn(map, Direction.West, vehicle);
        break;
      case "5": // Turn South
        tryTurn(map, Direction.South, vehicle);
        break;
      case "6": // Swtich to right lane
        vehicleNext = vehicleCurrent + 1;
        trySwitchLane(laneIndex + 1, vehicleNext, vehicle);
        break;
      case "7": // Switch to left lane
        vehicleNext = vehicleCurrent + 1;
        trySwitchLane(laneIndex - 1, vehicleNext, vehicle);
        break;
    }
  }

  /**
   * try to switch to the other lane
   * 
   * @param int index of target lane
   * @param int index of target vehicle position
   */
  public default void trySwitchLane(int laneIndex, int vehicleNext, Vehicle vehicle) {
    Lane targetL1 = vehicle.currentRoad.getLanes().get(laneIndex);
    if (targetL1.getCurrentVehicles().size() <= vehicleNext
        || targetL1.getCurrentVehicles().get(vehicleNext) == null)
      vehicle.move(vehicle.currentRoad, targetL1, vehicleNext);
    else
      collision(targetL1, vehicleNext, vehicle);
  }

  /**
   * Trying to make turn
   * may result in collision
   * 
   * @param map
   * @param d
   */
  private void tryTurn(Map map, Direction d, Vehicle vehicle) {
    int vehicleNext = 0;
    Lane targetL =  vehicle.currentLane;
    for (RoadSegment rs : map.getRoadSegments()) {
      if (rs.fromLane.name.equals(vehicle.currentRoad.toLane.name) 
      && rs.getLanes().get(0).direction == d
      && checkTurn(map, d, vehicle)){
        targetL = rs.getLanes().get(vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane));
        vehicle.move(rs, targetL, 0);
        vehicleNext = -1;
        break;
      }
    }
    if (vehicleNext != -1)
      collision(targetL, vehicleNext, vehicle);
  }
}