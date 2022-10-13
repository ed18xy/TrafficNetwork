//This is Factory interface for the Fatory Design Pattern
package Vehicles;
public interface Factory {
    /**
     * Default method returns a Vehicle 
     * implementations will return car, bus, or truck
     * @return vehicle
     */
    default Vehicle createVehicle(){
        return new Vehicle();
    }
}