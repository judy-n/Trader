import java.util.ArrayList;

/**
 * UserDatabase.java
 * Stores all Users in the system.
 *
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-28
 */
public class UserDatabase {
    private static ArrayList<User> allUser = new ArrayList<>();

    public UserDatabase() {

        //This is just for testing rn will delete later
        User u1 = new User("username", "example@email.com", "pa55word");
        allUser.add(u1);
    }

    public static void addUser(User u) {
        allUser.add(u);
    }


    public static User getUserByUsername(String username) {
        for (User u : allUser) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User u : allUser) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUser;
    }


    public static String usernamePassword(String username) {
        for (User u : allUser) {
            if (u.getUsername().equals(username))
                return u.getPassword();
        }
        return null;
    }

    public static String emailPassword(String email) {
        for (User u : allUser) {
            if (u.getEmail().equals(email))
                return u.getPassword();
        }
        return null;
    }

    public static boolean emailExists(String email) {
        for (User u : allUser) {
            if (u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public static boolean usernameExists(String username) {
        for (User u : allUser) {
            if (u.getUsername().equals(username))
                return true;
        }
        return false;
    }


    //This method is just for testing!! Delete later
    public void printAllUser() {
        for (User u : allUser) {
            System.out.println(u.getUsername());
        }
    }


}
