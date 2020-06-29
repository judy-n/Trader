import java.util.ArrayList;

/**
 * UserDatabase.java
 * Stores all Users in the system.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */
public class UserDatabase {
    private static ArrayList<User> allUser = new ArrayList<>();

    public UserDatabase() {

        //This is just for testing rn will delete later
        User u1 = new User("username", "example@email.com", "pa55word");
        allUser.add(u1);
    }

    /**
     * This method adds a user to the user database
     * @param u user
     */
    public static void addUser(User u) {
        allUser.add(u);
    }

    /**
     * This method returns a user by their username
     * @param username username
     * @return user
     */
    public static User getUserByUsername(String username) {
        for (User u : allUser) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * This method returns a user by their email
     * @param email email
     * @return user
     */
    public static User getUserByEmail(String email) {
        for (User u : allUser) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * This method returns all the users in the user database
     * @return all users
     */
    public static ArrayList<User> getAllUsers() {
        return allUser;
    }

    /**
     * This method gets a user's password by their username
     * @param username username
     * @return password
     */
    public static String usernamePassword(String username) {
        for (User u : allUser) {
            if (u.getUsername().equals(username))
                return u.getPassword();
        }
        return null;
    }

    /**
     * This method gets a user's password by their email
     * @param email email
     * @return password
     */
    public static String emailPassword(String email) {
        for (User u : allUser) {
            if (u.getEmail().equals(email))
                return u.getPassword();
        }
        return null;
    }

    /**
     * This method checks if a user with a certain email already
     * exists in the user database
     * @param email email
     * @return true if user exists, false otherwise
     */
    public static boolean emailExists(String email) {
        for (User u : allUser) {
            if (u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    /**
     * This method checks if a user with a certain username already
     * exists in the user database
     * @param username email
     * @return true if user exists, false otherwise
     */
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
