package Vehicles;

import Graphics.GUI;

public class Truck extends Vehicle {
  public double loadWeight;
  public Truck(){
    length = Math.random()*10+10;
    loadWeight = Math.random()*10+10;
    this.weight +=loadWeight+(Math.random()*90+50);
    displayIMG = GUI.truckDisplay;
  }

}