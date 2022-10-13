//This is implementation of a Factory interface to produce cars as part of Factory Design Pattern
package Vehicles;
public class CarFactory implements Factory {
    public Car createVehicle() {
        return new Car();
    }
}