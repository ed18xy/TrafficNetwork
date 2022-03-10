package GameEngine;

import java.util.ArrayList;
import java.util.Scanner;
import Vehicles.*;
import Map.*;
import Player.Player;
import DecisionMaking.PCDriverImpl;
import Graphics.GUI;
import Vehicles.Condition;

public class Main {
  private final int VEHICLEMAX = 100;
  private final int VEHICLEMIN = 20;
  private static int start = 0;// Road Segment number
  private Map map;
  private ArrayList<Vehicle> vehicles = new ArrayList<>();
  private Player player;
  private ArrayList<PCDriverImpl> PCDrivers = new ArrayList<>();
  private Scanner scan = new Scanner(System.in);
  private GUI visualRepr;

  /**
   * Main class constructor
   */
  public Main() {
    gameSetUp();
    simulator.run();
  }

  /**
   * Generates random vehicle
   * @return
   */
  private Vehicle vehicleGenerator() {
    switch ((int) (Math.random() * 10)) {
      case 0:
      case 1:
        return new Bus();
      case 2:
      case 3:
      case 4:
        return new Truck();
      default:
        return new Car();
    }
  }

  /**
   * Set up all needed resourses for the game
   */
  public void gameSetUp() {
    generateMap();
    addPlayer();
    loadVehicles();
  }

  /**
   * Updates the state of simulation
   */
  private void stateUpdate() {
    player.look();
    player.showMoves();
    player.chooseNextMove(map);
    for (PCDriverImpl npc : PCDrivers) {
      npc.look();
    }
    player.updater(map);
    for (PCDriverImpl npc : PCDrivers) {
      npc.chooseNextMove(map);
    }
  }

  /**
   * Simmulator
   * 
   * @run starts a continous simmulation until:
   *      Player's vehicle breaks
   */
  Runnable simulator = () -> {
    while (true) {
      roadsDisplay();
      stateUpdate();
      if (player.getVehicle().condition == Condition.Broken){
        System.out.println("You lost :(");
        break;
      }
    }
  };

  /**
   * Display the state of all the roads and places ofvehicles
   */
  private void roadsDisplay() {
    for (RoadSegment rs : map.getRoadSegments()) {
      System.out.print("FROM:" + rs.from.name + "  ");
      System.out.println("TO:" + rs.to.name);
      for (Lane l : rs.getLanes()) {
        for (int i = 0; i < 10; i++) {
          if (l.getCurrentVehicles().size() > i && l.getCurrentVehicles().get(i) != null)
            System.out.print(l.getCurrentVehicles().get(i));
          else
            System.out.print(" _ ");
        }
        System.out.println();
      }
    }
  }

  /**
   * Generate a new empty map
   */
  private void generateMap() {
    try {
      map = new Map();
    } catch (Exception e) {
      System.out.println("Map broke:" + e.toString());
    }
  }

  /**
   * Create vehicles
   * Assign non-player drivers
   * place them around map
   */
  private void loadVehicles() {
    try {
      boolean tryPlacing;
      for (int i = 0; i < ((Math.random() * (VEHICLEMAX - VEHICLEMIN)) + VEHICLEMIN); i++) {
        Vehicle v = vehicleGenerator();
        this.vehicles.add(v);
        PCDrivers.add(new PCDriverImpl(v));
      }
      for (Vehicle veh : vehicles) {
        if (veh.currentLane == null) {// don't place player's vehicle for 2nd time
          do {
            tryPlacing = map.placeVehicle(veh, null, null);
          } while (!tryPlacing);
        }
      }
    } catch (Exception e) {
      System.out.println("Vechiles generation broke:" + e.toString());
    }
  }

  //generates GUI for road display and user interaction
  //in cases we will be implementing visual later 
  private GUI generateGUI() {
    return null;
  }

  /**
   * Create a player, place it's vehicle at the start of Rout
   * and set final destinastion as the End of Rout
   */
  private void addPlayer() {
    System.out.println("Enter your nickname:");
    String nick = scan.nextLine();
    Car playerCar = new Car(){
      private String driverName = nick;
      /**
       * To use later for scoring table with multiplayer
       * @return
       */
      public String getDriversName (){
        return nick;
      }
    };
    vehicles.add(playerCar);
    RoadSegment routStartRS = map.getRoadSegments().get(start);
    Lane routStart = routStartRS.getLanes().get(0);
    map.placeVehicle(playerCar, routStart, routStartRS);
    this.player = new Player(playerCar);
  }

  public static void main(String[] args) {
    new Main();
  }

}
