//This is a View as a part of a MVC patter for player interactions
package Player;

import java.util.ArrayList;
import Map.*;
import Vehicles.Condition;
import Vehicles.Vehicle;
import GameEngine.*;

public class View {
    private Player player;
    public boolean dangerousMove = false;
    public boolean collisionOccured = false;

    /**
     * Constructor creates a view for player
     * 
     * @param player
     */
    public View(Player player) {
        this.player = player;
    }

    /**
     * Display the list of available moves at a current position
     * 
     * @return
     */
    public ArrayList<String> showMoves() {
        Vehicle vehicle = player.getVehicle();
        ArrayList<String> aList = new ArrayList<>();
        aList.add("0.Stay still;");
        if (vehicle.currentLane.getCurrentVehicles().indexOf(vehicle) == 9) {
            // Show all intersection moves:
            for (Turn turn : vehicle.currentRoad.toLane.getAvailableTurns()) {
                if (turn.direction == Direction.North)
                    aList.add("2.Turn North;");
                if (turn.direction == Direction.East)
                    aList.add("3.Turn East;");
                if (turn.direction == Direction.West)
                    aList.add("4.Turn West;");
                if (turn.direction == Direction.South)
                    aList.add("5.Turn South;");
            }
        } else if (vehicle.currentRoad.getLanes().size() > 1) {
            // Show all the available moves
            aList.add("1.Keep straight;");
            if (vehicle.currentRoad.getLanes().indexOf(vehicle.currentLane) == 0)
                aList.add("6.Change lane to the " + Side.Left + ";");
            else if (vehicle.currentRoad.getLanes()
                    .indexOf(vehicle.currentLane) == vehicle.currentRoad.getLanes().size()
                            - 1)
                aList.add("7.Change lane to the " + Side.Right + ";");
            else {
                aList.add("6.Change lane to the " + Side.Left + ";");
                aList.add("7.Change lane to the " + Side.Right + ";");
            }
        } else
            aList.add("1.Keep straight;");
        return aList;
    }

    /**
     * Basically allows player to look around
     * 
     * @return
     */
    public ArrayList<String> showSurroundings() {
        Vehicle vehicle = player.getVehicle();
        ArrayList<String> surroundings = new ArrayList<>();
        surroundings.add("Vehicle condition: " + vehicle.condition + ";");
        surroundings.add("Current road segment: FROM: " + vehicle.currentRoad.fromLane.name + " TO:"
                + vehicle.currentRoad.toLane.name + ";");
        String outLine = "";
        for (Lane l : vehicle.currentRoad.getLanes()) {
            for (int i = 9; i > -1; i--) {
                if (l.getCurrentVehicles().size() > i && l.getCurrentVehicles().get(i) != null) {
                    outLine += (" " + l.getCurrentVehicles().get(i).displayIMG + " ");
                } else
                    outLine += (" _ ");
            }
            outLine += ";";
            surroundings.add(outLine);
            outLine = "";
        }
        surroundings.add("You are " + (9 - vehicle.currentLane.getCurrentVehicles().indexOf(vehicle))
                + " km away from the next intersection;");
        return surroundings;
    }

    /**
     * Verifies player's decision
     * 
     * @param map
     * @param decision
     * @return
     */
    public boolean checkDecision(Map map, String decision) {
        dangerousMove = false;
        Vehicle vehicle = player.getVehicle();
        boolean b = player.verify(decision, map, vehicle);
        dangerousMove = player.tryingDangerouMove;
        return b;
    }

    /**
     * Moves player's car
     * 
     * @param map
     * @param nextMove
     */
    public void updater(Map map, String nextMove) {
        collisionOccured = false;
        try {
            int damageS = player.getVehicle().damageStatus;
            player.updater(map, nextMove);
            if (damageS < player.getVehicle().damageStatus)
                collisionOccured = true;
        } catch (Exception e) {
            System.out.println("View: updater broke:" + e.toString());
        }
    }

    /**
     * Checks if collision have occured
     * 
     * @return
     */
    public boolean checkCollision() {
        return collisionOccured;
    }

    /**
     * Checks if car was broken in a collision
     * 
     * @return
     */
    public boolean checkStatus() {
        return player.getVehicle().condition == Condition.Broken;
    }

}