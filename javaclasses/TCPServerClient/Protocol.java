package TCPServerClient;

import java.util.ArrayList;

import GameEngine.GameController;
import Player.PlayerController;
import Map.Map;

public class Protocol {

    private State state = State.WAITING;
    private final String ACTION_CODES = "01234567";
    private String action = "";
    private ArrayList<String> theOutput = new ArrayList<>();
    private PlayerController playerController;
    private Map map;
    private GameController gc;

    public Protocol(GameController gc) {
        this.gc = gc;
        playerController = gc.getPlayerController();
        map = gc.getMap();
    }

    public ArrayList<String> processInput(String theInput) {
        theOutput.clear();// clear at each step
        switch (state) {
            case WAITING:
                display();
                break;
            case ACTION_RESPONSE:
                action = theInput;
                // verify input
                if (!theInput.isEmpty() && ACTION_CODES.contains(theInput)) {
                    if (playerController.verify(map, theInput)) {
                        // update players location
                        playerController.makeMove(map, theInput);
                        // update NPCs
                        gc.stateUpdate();
                        // show the road
                        display();
                    } else {
                        // if gambling possible
                        if (playerController.dangerous()) {
                            theOutput.add("Would you like to gamble? y/n");
                            state = State.GAMBLE_RESPONSE;
                        }
                        // if the move move can't be made
                        else {
                            theOutput.add("This move can't be made. Try again");
                        }
                    }
                } else {
                    theOutput.add("Unsupported action code, try again");
                }
                break;
            case GAMBLE_RESPONSE:
                if (!theInput.isEmpty() && theInput.equals("y")) {
                    // update NPCs
                    gc.stateUpdate();
                    // player makes move after NPC to try gambling
                    playerController.makeMove(map, action);
                    if (playerController.checkCollision())
                        theOutput.add("! COLLISION !;");
                    // show the road
                    display();
                } else if (theInput.equals("n")) {
                    // update NPCs
                    gc.stateUpdate();
                    // player doesn't move
                    // show the road
                    display();
                } else {
                    theOutput.add("Unexpected input. Type y for yes or n for no");
                }
                break;
        }
        return theOutput;
    }

    /**
     * Frequently used code so made it in a seperate block to reuse
     * representation of a step in a simulation
     * 
     * @param gc
     */
    private void display() {
        // display roads
        gc.roadsDisplay().stream().forEach(s -> theOutput.add(s));
        // player look
        playerController.look().stream().forEach(s -> theOutput.add(s));
        // show available moves
        playerController.availMoves().stream().sorted().forEach(s -> theOutput.add(s));
        theOutput.add("choose:");
        // update state to recieve an action code from client (player)
        state = State.ACTION_RESPONSE;
    }
}
