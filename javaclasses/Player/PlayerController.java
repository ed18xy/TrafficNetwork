//This is a contorller as a part of MVC pattern for player interactions
package Player;

import java.util.ArrayList;

import Map.Map;

public class PlayerController {

    private View view;

    public PlayerController(Player player) {
        view = new View(player);
    }
    public ArrayList<String> availMoves(){
        return view.showMoves();
    }
    public ArrayList<String> look(){
        return view.showSurroundings();
    }
    public boolean verify(Map map, String actionCode){
        return view.checkDecision(map, actionCode);
    }
    public boolean dangerous(){
        return view.dangerousMove;
    }
    public void makeMove(Map map, String aMove){
        view.updater(map, aMove);
    }
    public boolean checkCollision(){
        return view.checkCollision();
    }
    public boolean checkStatus(){
        return view.checkStatus();
    }
}