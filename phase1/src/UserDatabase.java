import java.util.ArrayList;
/**
 * UserDatabase.java
 * Stores all Users in the system
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-26
 */
public class UserDatabase {
    private ArrayList<User> allUser;
    public UserDatabase(){
        allUser = new ArrayList<>();
    }

    public void addUser(User u){
        allUser.add(u);
    }
}
