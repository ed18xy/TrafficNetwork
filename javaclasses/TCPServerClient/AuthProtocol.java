package TCPServerClient;

import java.util.ArrayList;

/**
 * This protocol is for user authentication. Seperate for encapsulation and security
 */
public class AuthProtocol {
    private Database db;

    /**
     * Constructor creates a connection to database
     */
    public AuthProtocol(){
        db = new Database();
    }
    /**
     * Tries to authenticate user
     * @param theInput
     * @return
     */
    public ArrayList<String> processInput(String theInput) {
        ArrayList<String> theOutput = new ArrayList<>();
        if(theInput==null || !db.accessUSERStable().contains(theInput)){
            theOutput.add("User not found! try again");
        }else{
            theOutput.add("Welcome!;");
        }
        return theOutput;
    }
}
