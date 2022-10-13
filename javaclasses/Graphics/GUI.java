package Graphics;

public class GUI {
  public static String busDisplay;
  public static String carDisplay;
  public static String truckDisplay;
  public static String playerDisplay;

  //Generates corresponding visual as player selected. In case emoji are not supported  
  public final static void generateGUI(String type) {
    switch(type){
      case "emoji":
      busDisplay = "🚌";
      carDisplay = "🚗";
      truckDisplay = "🚚";
      playerDisplay = "🚙";
      break;
      case "plain":
      default:
      busDisplay = "[BUS]";
      carDisplay = "[CAR]";
      truckDisplay = "[TRUCK]";
      playerDisplay = "[PLAYER]";
      break;
    }
  }

}