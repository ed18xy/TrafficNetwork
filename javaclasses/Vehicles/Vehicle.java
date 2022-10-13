package Vehicles;

import GameEngine.Direction;
import GameEngine.Side;
import Map.*;

public class Vehicle {
  public int speed;
  public int damageStatus;
  public Condition condition;
  public int reputationValue;
  public double weight;
  public double length;
  public Lane currentLane;
  public RoadSegment currentRoad;
  public String displayIMG;

  /**
   * Constructors
   * builds new vehicle
   * places the vehicle at the specified location
   */
  public Vehicle() {
    speed = 1; //1km per iteration
    damageStatus = 0;
    setCondition(0);
    weight = Math.random() * 50 + 30;
    reputationValue = 50; // everyone starts with equal reputation
    currentLane = null;
    currentRoad = null;
  }

  /**
   * Sets condition of the car at the start of the game and whenever the damage is
   * taken
   * 
   * @param ds value of the damage status
   */
  public void setCondition(int ds) {
    if (ds > 0 && ds <= 50) {
      condition = Condition.SlightlyDamaged;
    } else if (ds > 50 && ds < 100) {
      condition = Condition.SeverlyDamaged;
    } else if (ds >= 100) {
      condition = Condition.Broken;
    } else {
      condition = Condition.New;
    }
  }

  public void changeSpeed() {
  }

  public void turn(Direction direction) {
  }

  public void changeLane(Side lane) {
  }

  public void takeDamage(int damageValue) {
    damageStatus += damageValue;
    setCondition(damageStatus);
    reputationValue -= 10;
  }

  /**
   * Changes the location of vehicle
   * 
   * @param targetRoad
   * @param targetLane
   * @param position
   */
  public void move(RoadSegment targetRoad, Lane targetLane, int position) {
      try {
        int currentPos = currentLane.getCurrentVehicles().indexOf(this);
      currentLane.getCurrentVehicles().set(currentPos, null);
      currentRoad = targetRoad;
      currentLane = targetLane;
      while (currentLane.getCurrentVehicles().size() < position + 1) {
        currentLane.getCurrentVehicles().add(null);
      }
      currentLane.getCurrentVehicles().set(position, this);
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Could not move vehicle:"+this+"into position "+position+" at "+targetLane+"on the road:"+targetRoad+"\n");
      }
      
  }

  /**
   * Records the first position of the vehicle
   * @param targetRoad
   * @param targetLane
   */
  public void placeMe(RoadSegment targetRoad, Lane targetLane){
    currentRoad = targetRoad;
    currentLane = targetLane;
  }

}