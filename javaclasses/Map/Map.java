package Map;

import java.util.ArrayList;
import Vehicles.Vehicle;

public class Map {
  final int WIDTH = 9; // length of Map, number of total intersections
  private ArrayList<RoadSegment> roadSegments = new ArrayList<>();
  private ArrayList<Intersection> intersections = new ArrayList<>();

  public void initializeMap(){
    //create all intersections
    //add from/to Intersections to road segments
    //add a turn to 'to' Intersection in the direction of the lanes
    for(char c = 'A'; c<'J'; c++){
      Intersection i = new Intersection(Character.toString(c));
      intersections.add(i);
      for (RoadSegment rs : roadSegments) {
        if(rs.from.compareTo(i.name)==0){
          rs.fromLane = i;
          i.addTurn(new Turn(rs.getLanes().get(0).direction));
        }
        if(rs.to.compareTo(i.name)==0){
          rs.toLane = i;
        }
      }
    }
  }
  /**
   * Place vehicle at the start of specified lane.
   * If lane is not specified (null for initial placing) -> Place in a random lane
   * 
   * @param v vehicle to place
   * @param l in which lane
   * @return
   */
  public boolean placeVehicle(Vehicle vehicle, Lane l, RoadSegment rs) {
    RoadSegment roadT;
    Lane laneT;
    if (l == null) {// generate random location for NPCs
      roadT = roadSegments.get((int) (Math.random() * WIDTH));
      laneT = roadT.getLanes().get((int) (Math.random() * roadT.getLanes().size()));
    } else {
      roadT = rs;
      laneT = l;
    }
    if (laneT.getAvailableSpace() > vehicle.length && laneT.getCurrentVehicles().size() < 10) {
      laneT.newVehicle(vehicle);// place new vehicle at the start
      // update location of all vehicles in this lane
      for (Vehicle v1 : laneT.getCurrentVehicles()) {
        v1.placeMe(roadT, laneT);
      }
      return true;
    } else
      return false;
  }

  public ArrayList<RoadSegment> getRoadSegments() {
    return this.roadSegments;
  }

  // XML
  public void addRoadSegment(RoadSegment roadSegment) {
    roadSegments.add(roadSegment);
  }

  public static Map load(String filename) {
    XMLReader<Map> reader = new XMLReader<>();
    reader.setXMLSchema("map.xsd");
    reader.setXMLNodeConverter(new MapReader());
    return reader.readXML(filename);
  }

  public String toXMLString() {
    StringBuilder builder = new StringBuilder();
    builder.append("<map>" + System.lineSeparator());
    roadSegments.forEach(x -> builder.append(x.toXMLString() + System.lineSeparator()));
    builder.append("</map>");

    return builder.toString();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    roadSegments.forEach(x -> builder.append(x.toString()));

    return builder.toString();
  }
}