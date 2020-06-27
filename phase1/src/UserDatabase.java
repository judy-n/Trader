import java.util.ArrayList;
/**
 * UserDatabase.java
 * Stores all Users in the system
 * @author Ning Zhang
 * @author Judy Naamani
 * created 2020-06-26
 * last modified 2020-06-27
 */
public class UserDatabase {
    private static ArrayList<User> allUser;
    public UserDatabase(){
        allUser = new ArrayList<>();
    }

    public static void addUser(User u){
        allUser.add(u);
    }

    public static ArrayList<User> getAllUser(){
        return allUser;
    }
}
