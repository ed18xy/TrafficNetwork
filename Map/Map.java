package Map;


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import GameEngine.Direction;
import Vehicles.Vehicle;

public class Map {
  final int WIDTH = 9; //length of Map, number of total intersections
  private ArrayList<RoadSegment> roadSegments = new ArrayList<>();
  private ArrayList<Intersection> intersections = new ArrayList<>();

  /**
   * Constructor that builds a map from file input 
   */
  public Map(){
    try (Scanner scanner = new Scanner(new File("Map/Map.txt"));) {
      //generate all Intersections
      char c = 'A';
    for (int i = 0; i < WIDTH; i++){
      Intersection intr =  new Intersection(Character.toString(c));
      intersections.add(intr);//add intersection to array in order
      c++;
    }
    //read map and generate road segments
    for(int x = 0; x < WIDTH; x++){
      for(int y = 0; y < WIDTH; y++){
        int connection = scanner.nextInt();//read int
        if(connection>0){
          //create corresponding RoadSegment
          String dir = scanner.next();
          Direction laneDir = getDirection(dir);
          RoadSegment segm = new RoadSegment(connection, intersections.get(x), intersections.get(y), laneDir);
          roadSegments.add(segm);
          //add a turn to the intersection
          intersections.get(x).addTurn(new Turn(laneDir));
        }
      }
    }
    } catch (Exception e) {
      System.out.println("Map: generation error: "+ e.toString());
    }
    
  }

  private Direction getDirection(String d){
    Direction toD = Direction.North;
    try {
      switch(d){
      case "E": 
      toD = Direction.East;
      break;
      case "N": 
      toD = Direction.North;
      break;
      case "W": 
      toD = Direction.West;
      break;
      case "S": 
      toD = Direction.South;
      break;
      default:
      throw new Exception("Wrong Map input: unexpected direction");
    }
    } catch (Exception e) {
      System.out.println("Reading broke:"+ e.toString());
    }
    return toD;
  }



  /**
   * Place vehicle at the start of specified lane. 
   * If lane is not specified (null for initial placing) -> Place in a random lane
   * @param v vehicle to place
   * @param l in which lane
   * @return
   */
  public boolean placeVehicle(Vehicle vehicle, Lane l, RoadSegment rs){
    RoadSegment roadT;
    Lane laneT;
    if(l==null){//generate random location for NPCs
      roadT =  roadSegments.get((int)(Math.random()*WIDTH));
      laneT = roadT.getLanes().get((int)(Math.random()*roadT.getLanes().size()));
    }else{
      roadT = rs;
      laneT = l;
    }
    if(laneT.getAvailableSpace()>vehicle.length && laneT.getCurrentVehicles().size()<10){
      laneT.newVehicle(vehicle);//place new vehicle at the start
      //update location of all vehicles in this lane
      for (Vehicle v1 : laneT.getCurrentVehicles()) {
        v1.placeMe(roadT, laneT);
      }
      return true;
    }else return false;
  }

  public ArrayList<Intersection> getIntersections() {
    return this.intersections;
  }

  public ArrayList<RoadSegment> getRoadSegments() {
    return this.roadSegments;
  }
}