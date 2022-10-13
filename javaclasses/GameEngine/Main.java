//This is Main file that creates a signleton class GameController
package GameEngine;

import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        System.out.println("Would you like an emoji or plain representation?");
        Scanner scan = new Scanner(System.in);
        String guiOption = scan.nextLine();
        scan.close();
        GameController gc = GameController.getInstance(guiOption, 0);//<-Signleton
    }
}