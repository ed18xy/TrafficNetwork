package TCPServerClient;

import java.util.ArrayList;
/**
 * This class simulates database to authenticate users
 */
public class Database {
    private ArrayList<String> USERS = new ArrayList<>();
    public Database(){
        USERS.add("USER1");
        USERS.add("me");
        USERS.add("Player");
    }
    public ArrayList<String> accessUSERStable(){
        return USERS;
    }
}
