//This is implementation of a Factory interface to produce buses as part of Factory Design Pattern
package Vehicles;
public class BusFactory implements Factory {
    public Bus createVehicle() {
        return new Bus();
    }
}