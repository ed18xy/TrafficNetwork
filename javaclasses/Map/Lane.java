package Map;


import GameEngine.Direction;
import Vehicles.Vehicle;

import java.util.ArrayList;

public class Lane {
  public Direction direction;
  private double length;
  private ArrayList<Vehicle> vehicles = new ArrayList<>();
  /**
   * constructs a Lane 
   * @param side
   * @param length
   * 
   */
  public Lane(Direction direction, double length) {
    this.direction = direction;
    this.length = length;
  }

  /**
   * @return array of vehicles on this lane at the moment
   */
  public ArrayList<Vehicle> getCurrentVehicles() {
  return this.vehicles;
  }

  /**
   * Add the vegicle to the start
   * @param vehicle
   */
  public void newVehicle(Vehicle vehicle){
    vehicles.add(0,vehicle);
  }

  /**
   * 
   * @return space that is not taken by other cars
   */
  public double getAvailableSpace() {
    double totalL = 0;
    for (Vehicle vehicle : this.vehicles) {
      if(vehicle!=null)totalL += vehicle.length;
    }
    return this.length-totalL;
  }

}