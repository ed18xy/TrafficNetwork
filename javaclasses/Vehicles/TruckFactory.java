//This is implementation of a Factory interface to produce trucks as part of Factory Design Pattern
package Vehicles;
public class TruckFactory implements Factory {
    public Truck createVehicle() {
        return new Truck();
    }
}