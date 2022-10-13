//This is a Signleton class as part of e Singleton Creational Design Pattern
package GameEngine;

import java.util.ArrayList;
import Vehicles.*;
import Map.*;
import Player.PlayerController;
import Player.Player;
import DecisionMaking.PCDriverImpl;
import Graphics.GUI;

public class GameController {
  private static GameController instance = null;// a single instance of this class
  private final int VEHICLEMAX = 100;
  private final int VEHICLEMIN = 20;
  private static int start = 0;// Road Segment number
  private Map map;
  private ArrayList<Vehicle> vehicles = new ArrayList<>();
  private ArrayList<Thread> NPCs = new ArrayList<>();
  private ArrayList<Player> playerList = new ArrayList<>();// to support multiplayer
  private Player player;
  private ArrayList<PCDriverImpl> PCDrivers = new ArrayList<>();
  private PlayerController playerController;
  private Factory carFactory = new CarFactory();
  private Factory busFactory = new BusFactory();
  private Factory truckFactory = new TruckFactory();

  /**
   * private GameController class constructor
   * 
   * @param guiType
   * @param amountPlayers if 0, its just simmulation, determent by server size
   */
  private GameController(String guiType, int amountPlayers) {
    generateGUI(guiType);
    generateMap();
    if (amountPlayers > 0) {
      addPlayer();
      playerList.add(player);
    }
    loadVehicles();
    for (Thread t : NPCs) {
      t.start();
    }
    // if no players-> run simulation, otherswise Client-Server connection
    if (amountPlayers == 0)
      simulator.run();
  }

  /**
   * This method returns the instance of GameController class
   * allows only one such class to exist
   * 
   * @return
   */
  public static synchronized GameController getInstance(String guiType, int amountPlayers) {
    if (instance == null)
      instance = new GameController(guiType, amountPlayers);
    return instance;
  }

  /**
   * Returns a String array displaying all vehicles on the map
   * 
   * @return
   */
  public ArrayList<String> roadsDisplay() {
    ArrayList<String> aView = new ArrayList<>();
    String outLine = "";
    for (RoadSegment rs : map.getRoadSegments()) {
      aView.add("FROM:" + rs.fromLane.name + "  " + "TO:" + rs.toLane.name + ";");
      for (Lane l : rs.getLanes()) {
        for (int i = 9; i > -1; i--) {
          if (l.getCurrentVehicles().size() > i && l.getCurrentVehicles().get(i) != null)
            outLine += " " + l.getCurrentVehicles().get(i).displayIMG + " ";
          else
            outLine += " _ ";
        }
        outLine += ";";
        aView.add(outLine);
        outLine = "";
      }
    }
    return aView;
  }

  /**
   * Generates random vehicle using Factories
   * 
   * @return vehicle (car, bus or truck)
   */
  private Vehicle vehicleGenerator() {
    switch ((int) (Math.random() * 10)) {
      case 0:
      case 1:
        return busFactory.createVehicle();
      case 2:
      case 3:
      case 4:
        return truckFactory.createVehicle();
      default:
        return carFactory.createVehicle();
    }
  }

  /**
   * Updates the state of simulation
   */
  public synchronized void stateUpdate() {
    notifyAll();
  }

  /**
   * Simmulator
   * 
   * @run starts a continous simmulation
   */
  Runnable simulator = () -> {
    while (true) {
      try {
        for (String s : roadsDisplay()) {
          System.out.println(s);
        }
        stateUpdate();
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  };

    /**
     * Updates NPC on each step of simulation
     * @param myNPC
     */
    public synchronized void stepUpd(PCDriverImpl myNPC) {
      try {
        while(true){
          synchronized(this){wait();}
          myNPC.look();
          myNPC.chooseNextMove();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  /**
   * Generate a new empty map
   */
  private void generateMap() {
    try {
      map = Map.load("map.xml");
      map.initializeMap();
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
        PCDriverImpl driver = new PCDriverImpl(v, map);
        PCDrivers.add(driver);// assign drivers
        Thread npc = new Thread(() -> stepUpd(driver));
        NPCs.add(npc);//aasign threads
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

  // generates GUI for road display and user interaction
  // in cases we will be implementing visual later
  public void generateGUI(String iconsType) {
    GUI.generateGUI(iconsType);
  }

  /**
   * Create a player, place it's vehicle at the start of Rout
   * and set final destinastion as the End of Rout
   */
  private void addPlayer() {
    Car playerCar = new Car();
    playerCar.displayIMG = GUI.playerDisplay;
    vehicles.add(playerCar);
    RoadSegment routStartRS = map.getRoadSegments().get(start);
    Lane routStart = routStartRS.getLanes().get(0);
    map.placeVehicle(playerCar, routStart, routStartRS);
    this.player = new Player(playerCar);
    playerController = new PlayerController(player);
  }

  // getters methods to ensure encaplsulation
  public Map getMap() {
    return map;
  }

  public PlayerController getPlayerController() {
    return playerController;
  }

}
