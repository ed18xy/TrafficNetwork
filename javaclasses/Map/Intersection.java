package Map;

import java.util.ArrayList;

public class Intersection {
  public String name;
  private ArrayList<Turn> turnsAvailable = new ArrayList<>();
  public Intersection(String name){
    this.name = name;
  }

  public void addTurn(Turn t){
    turnsAvailable.add(t);
  }
  
  public ArrayList<Turn> getAvailableTurns() {
  return turnsAvailable;
  }

}