package Player;

import Map.Map;

import java.util.ArrayList;
import java.util.Scanner;

import DecisionMaking.PlayerDriverImpl;
import GameEngine.Direction;
import GameEngine.Side;
import Vehicles.Vehicle;
import Map.Turn;

public class Player extends PlayerDriverImpl {

  private int nextMove;
  private Scanner userScanner = new Scanner(System.in);

  public Player(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  /**
   * Show the list of available actions
   */
  public void showMoves() {
    System.out.println("0.Stay still");
    if (vehicle.currentLane.getCurrentVehicles().indexOf(vehicle) == 9) {
      chooseIntersectionMove();
    } else if (vehicle.currentRoad.getLanes().size() > 1) {
      chooseMidlaneMove();
    }else  System.out.println("1.Keep straight");
  }

  /**
   * Asks player to choose the next move
   * if move was verified -> nextMove
   * otherwise -> nextMove = 0 (stay at the same position)
   * @param map
   */
  public void chooseNextMove(Map map){
    try {
      int decision = userScanner.nextInt();
      nextMove = (verifyPlayerDesicion(decision, map)) ? decision : 0;
    } catch (Exception e) {
      System.out.println("Desicion making broke: " + e.toString());
    }
  }

  /**
   * Show available turns at the current intersection
   */
  public void chooseIntersectionMove() {
    ArrayList<String> turns = new ArrayList<>();
    System.out.println("Available turns: ");
    for (Turn turn : vehicle.currentRoad.to.getAvailableTurns()) {
      if (turn.direction == Direction.North)
      turns.add("2.Turn North");
      if (turn.direction == Direction.East)
      turns.add("3.Turn East");
      if (turn.direction == Direction.West)
      turns.add("4.Turn West");
      if (turn.direction == Direction.South)
      turns.add("5.Turn South");
    }
    turns.stream().sorted().forEach(s -> System.out.println(s));
  }

  /**
   * If the lane at the left edge -> right switch available
   * If the lane at the right edge -> left 
   */
  public void chooseMidlaneMove() {
    System.out.println("1.Keep straight");
    if (vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane) == 0)
      System.out.println("6.Change lane to the "+ Side.Right);
    else if (vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane) == vehicle.currentRoad.getLanes().size() - 1)
      System.out.println("7.Change lane to the "+ Side.Left);
    else{
      System.out.println("6.Change lane to the "+ Side.Right);
      System.out.println("7.Change lane to the "+ Side.Left);
    }
  }
  
  /**
   * Updates player and advances them the next  position
   * Checks if player have arrived to the final destination
   */
  public void updater(Map map){
    makeMove(nextMove, map, vehicle);
  }

}