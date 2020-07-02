import java.io.*;
import java.util.ArrayList;

/**
 * Stores all Users in the system.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-02
 */
public class UserDatabase implements Serializable {
    private static ArrayList<User> allNormals = new ArrayList<>();
    private static ArrayList<AdminUser> allAdmins = new ArrayList<>();

    /**
     * Updates the userdatabase with all the current users.
     *
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
        allNormals.add(u1);
    }

    /**
     * Adds the given User to the user database.
     *
     * @param userToAdd the User being added to the database
     */
    public static void addUser(User userToAdd) {
        if (userToAdd instanceof AdminUser) {
            allAdmins.add((AdminUser) userToAdd);
        } else {
            allNormals.add(userToAdd);
        }
    }

    /**
     * Takes the given username and returns the associated User.
     *
     * @param username the username of the User being searched for
     * @return the User associated with the given username
     */
    public static User getUserByUsername(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Takes the given email and returns the associated User.
     *
     * @param email the email of the User being searched for
     * @return the User associated with the given email
     */
    public static User getUserByEmail(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Getter for all Users in the user database.
     *
     * @return an ArrayList of all Users in the database
     */
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.addAll(allNormals);
        allUsers.addAll(allAdmins);
        return allUsers;
    }

    /**
     * Takes the given username and returns the associated account password.
     *
     * @param username the username of the User whose password is being searched for
     * @return the account password associated with the given username
     */
    public static String usernamePassword(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username))
                return u.getPassword();
        }
        return null;
    }

    /**
     * Takes the given email and returns the associated account password.
     *
     * @param email the email of the User whose password is being searched for
     * @return the account password associated with the given email
     */
    public static String emailPassword(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equals(email))
                return u.getPassword();
        }
        return null;
    }

    /**
     * Checks if a User with the given email already exists in the user database.
     *
     * @param email the email being checked for whether it's already taken or not
     * @return true if User with the given email exists, false otherwise
     */
    public static boolean emailExists(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    /**
     * Checks if a User with the given username already exists in the user database.
     *
     * @param username the username being checked for whether it's already taken or not
     * @return true if User with the given username exists, false otherwise
     */
    public static boolean usernameExists(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    /**
     * Reads in a .ser file of a list of non-admin Users to this UserDatabase's allNormals.
     *
     * @param path the ser filepath
     * @throws ClassNotFoundException throws this if class isn't found
     */
    public static void readNormalsFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            allNormals = (ArrayList<User>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("IO Error Occurred");
        }
    }

    /**
     * Reads in a .ser file of a list of AdminUsers to this UserDatabase's allAdmins.
     *
     * @param path the ser filepath
     * @throws ClassNotFoundException throws this if class isn't found
     */
    public static void readAdminsFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            allAdmins = (ArrayList<AdminUser>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("IO Error Occurred");
        }
    }

    /**
     * Writes allNormals to a .ser file with path filePath.
     *
     * @param filePath the ser filepath
     * @throws IOException throws IOException
     */
    public static void saveNormalsToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(allNormals);
        output.close();
    }

    /**
     * Writes allAdmins to a .ser file with path filePath.
     *
     * @param filePath the ser filepath
     * @throws IOException throws IOException
     */
    public static void saveAdminsToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(allAdmins);
        output.close();
    }




    //This method is just for testing!! Delete later
    public static void printAllUser() {
        for (User u : getAllUsers()) {
            System.out.println(u.getUsername());
        }
    }
}
