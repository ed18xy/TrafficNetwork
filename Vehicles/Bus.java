package Vehicles;

public class Bus extends Vehicle {
  public Bus(){
    length = Math.random()*5+15;
    publicPassengers = (int) (Math.random()*20);
  }

  private int publicPassengers;

  public void loadPassengers() {
    this.weight += publicPassengers*(Math.random()*60+30)+Math.random()*70;
  }

}