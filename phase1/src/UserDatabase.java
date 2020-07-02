import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * UserDatabase.java
 * Stores all Users in the system.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-01
 */
public class UserDatabase implements Serializable {
    private static ArrayList<User> allUser = new ArrayList<>();

    /**
     * This method updates the userdatabase with all the current users
     */
    public static void update (){

        //This is just for testing rn will delete later
        User u1 = new User("u", "e", "p");
        Item i1 = new Item("Item", "This is an item.");
        Item i2 = new Item("Item", "This is another item.");
        u1.addPendingInventory(i1);
        u1.addPendingInventory(i2);
        u1.addApprovedInventory(i1);
        u1.addApprovedInventory(i2);
        u1.addWishlist(i1);
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


    /**
     * This method reads a .ser file of a list of Users to this UserDatabase's allUser
     * @param path the ser filepath
     * @throws ClassNotFoundException throws this if class isn't found
     */
    public static void readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            allUser = (ArrayList<User>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("IO Error Occurred");
        }
    }

    /**
     * This method writes allUser to a .ser file with path filePath
     * @param filePath the ser filepath
     * @throws IOException throws IOException
     */
    public static void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(allUser);
        output.close();
    }




    //This method is just for testing!! Delete later
    public void printAllUser() {
        for (User u : allUser) {
            System.out.println(u.getUsername());
        }
    }


}
