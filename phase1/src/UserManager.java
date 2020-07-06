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
public class UserManager implements Serializable {
    private ArrayList<NormalUser> allNormals;
    private ArrayList<AdminUser> allAdmins;
    private ArrayList<String> usernamesToFreeze;
    private ArrayList<String> unfreezeRequests;

    /**
     * Class constructor.
     * Creates a UserManager, setting all lists to empty by default.
     *
     */
    public UserManager() {
        allNormals = new ArrayList<>();
        allAdmins = new ArrayList<>();
        usernamesToFreeze = new ArrayList<>();
        unfreezeRequests = new ArrayList<>();
    }


    /*public void update (){

        //This is just for testing rn will delete later
        NormalUser u1 = new NormalUser("u", "e", "p");
        Item i1 = new Item("Item", "This is an item.",u1.getUsername());
        Item i2 = new Item("Item", "This is another item.", u1.getUsername());
        u1.addPendingInventory(i1.getId());
        u1.addPendingInventory(i2.getId());
        u1.addInventory(i1.getId());
        u1.addInventory(i2.getId());
        u1.addWishlist(i1.getId());
        allNormals.add(u1);
    }*/

    /**
     * Adds the given User to the user database.
     *
     * @param userToAdd the User being added to the database
     */
    public void addUser(User userToAdd) {
        if (userToAdd instanceof AdminUser) {
            allAdmins.add((AdminUser) userToAdd);
        } else {
            allNormals.add((NormalUser) userToAdd);
        }
    }

    /**
     * Takes the given username that belongs to a non-admin user and returns the associated NormalUser.
     *
     * @param username the username of the non-admin user being retrieved
     * @return the non-admin user associated with the given username
     */
    public NormalUser getNormalByUsername(String username) {
        for (NormalUser u : allNormals) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Takes the given username that belongs to an admin and returns the associated AdminUser.
     *
     * @param username the username of the admin being retrieved
     * @return the admin associated with the given username
     */
    public AdminUser getAdminByUsername(String username) {
        for (AdminUser u : allAdmins) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Takes the given email that belongs to a non-admin user and returns the associated NormalUser.
     *
     * @param email the email of the non-admin user being retrieved
     * @return the non-admin user associated with the given email
     */
    public NormalUser getNormalByEmail(String email) {
        for (NormalUser u : allNormals) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Takes the given email that belongs to an admin and returns the associated AdminUser.
     *
     * @param email the email of the admin being retrieved
     * @return the admin associated with the given email
     */
    public AdminUser getAdminByEmail(String email) {
        for (AdminUser u : allAdmins) {
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
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.addAll(allNormals);
        allUsers.addAll(allAdmins);
        return allUsers;
    }


    public ArrayList<NormalUser> getAllNormals() {
        return allNormals;
    }

    public ArrayList<AdminUser> getAllAdmins() {
        return allAdmins;
    }

    /**
     * Takes the given username and returns the associated account password.
     *
     * @param username the username of the User whose password is being searched for
     * @return the account password associated with the given username
     */
    public String usernamePassword(String username) {
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
    public String emailPassword(String email) {
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
    public boolean emailExists(String email) {
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
    public boolean usernameExists(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    /**
     * Return all the usernames that needs to be frozen
     * @return usernames to freeze
     */
    public ArrayList<String> getUsernamesToFreeze(){
        return usernamesToFreeze;
    }


    /**
     * Clears the usernamesToFreeze arraylist
     */
    public void clearUsernamesToFreeze(){
        usernamesToFreeze.clear();
    }

    /**
     * Returns the last admin ID
     * @return the last admin ID
     */
    public int getAdminId(){
        return allAdmins.size();
    }

    /**
     * Getter for the list of unfreeze requests.
     *
     * @return the list of frozen non-admin users who have requested their account be unfrozen
     */
    public ArrayList<NormalUser> getUnfreezeRequests() {
        ArrayList<NormalUser> userRequests = new ArrayList<>();
        for (String username : unfreezeRequests) {
            userRequests.add(getNormalByUsername(username));
        }
        return userRequests;
    }

    /**
     * Adds the given username to the list of unfreeze requests.
     *
     * @param username the username of a non-admin user requesting to be unfrozen
     */
    public void addUnfreezeRequest(String username) {
        unfreezeRequests.add(username);
    }

    /**
     * Unfreezes the NormalUser associated with the given username.
     *
     * @param username the username of the non-admin being unfrozen
     */
    public void unfreeze(String username) {
        getNormalByUsername(username).unfreeze();
        unfreezeRequests.remove(username);
    }

    /**
     * Removes all usernames from the list of unfreeze requests.
     *
     */
    public void rejectAllRequests() {
        unfreezeRequests.clear();
    }

    //This method is just for testing!! Delete later
    public void printAllUser() {
        for (User u : getAllUsers()) {
            System.out.println(u.getUsername());
        }
    }
}
