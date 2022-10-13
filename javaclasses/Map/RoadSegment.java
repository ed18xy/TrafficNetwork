package Map;

import java.util.ArrayList;
import GameEngine.Direction;

public class RoadSegment {
  private ArrayList<Lane> Alllanes = new ArrayList<>();
  private final int LANELENGTH = 200; // 10 vehicles per lane max
  public Intersection fromLane;
  public Intersection toLane;
  private String identifier;
  public String from;
  public String to;
  private String direction;
  private int lanes;


  /**
   * Constructor to generate road segments from XML and make all needed values 
   * @param identifier
   * @param from
   * @param to
   * @param direction
   * @param lanes
   */
  public RoadSegment(String identifier, String from, String to, String direction, int lanes) {
    this.identifier = identifier;
    this.from = from;
    this.to = to;
    this.direction = direction;
    this.lanes = lanes;
    for (int i = 0; i < lanes; i++) {
      Lane l = new Lane(getDirection(direction), LANELENGTH);
      Alllanes.add(l);
    }
  }

  private Direction getDirection(String d) {
    Direction toD = Direction.North;
    try {
      switch (d) {
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
      System.out.println("Reading broke:" + e.toString());
    }
    return toD;
  }

  public ArrayList<Lane> getLanes() {
    return Alllanes;
  }

  // XML
  public String toXMLString() {
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("<roadSegment id=\"%s\">%n", identifier));
    builder.append(String.format("<from>%s</from>%n", from));
    builder.append(String.format("<to>%s</to>%n", to));
    builder.append(String.format("<direction>%s</direction>%n", direction));
    builder.append(String.format("<lanes>%d</lanes>%n", lanes));
    builder.append("</roadSegment>");

    return builder.toString();
  }

  @Override
  public String toString() {
    return String.format("ID: %s%n\tFrom: %s%n\tTo: %s%n\tDirection: %s%n\tLanes: %d%n", identifier, from, to, direction, lanes);
  }

}