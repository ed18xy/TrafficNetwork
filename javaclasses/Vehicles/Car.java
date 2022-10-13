package Vehicles;

import Graphics.GUI;

public class Car extends Vehicle {
  public Car(){
    length = Math.random()*10+5;
    passengers = (int)(Math.random()*5+1);
    displayIMG = GUI.carDisplay;
  }

  private int passengers;
  public void loadPassengers(){
    this.weight+=passengers*(Math.random()*90+10);
  }

}