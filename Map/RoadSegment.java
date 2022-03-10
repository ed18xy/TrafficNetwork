package Map;

import java.util.ArrayList;
import GameEngine.Direction;

public class RoadSegment {
  private ArrayList<Lane> lanes = new ArrayList<>();
  private final int LANELENGTH = 200; //10 vehicles per lane max 
  public Intersection from;
  public Intersection to;
  /**
   * 
   * @param numLanes
   * @param from
   * @param to
   * @param d
   */
  public RoadSegment(int numLanes, Intersection from, Intersection to, Direction d){
    this.from = from;
    this.to = to;
    //generate lanes
    for(int i = 0; i < numLanes; i++){
      Lane l = new Lane(d, LANELENGTH);
      lanes.add(l);
    }
  }

  public ArrayList<Lane> getLanes() {
  return lanes;
  }

}