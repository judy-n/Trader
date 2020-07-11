import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all Users in the system.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-11
 */
public class UserManager implements Serializable {
    private List<NormalUser> allNormals;
    private List<AdminUser> allAdmins;
    private List<String> usernamesToFreeze;
    private List<String> unfreezeRequests;

    /**
     * Class constructor.
     * Creates a UserManager, setting all lists to empty by default.
     */
    public UserManager() {
        allNormals = new ArrayList<>();
        allAdmins = new ArrayList<>();
        usernamesToFreeze = new ArrayList<>();
        unfreezeRequests = new ArrayList<>();
    }

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
     * Takes the given username and returns the associated NormalUser.
     *
     * @param username the username of the normal user being retrieved
     * @return the normal user associated with the given username
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
     * Takes the given email and returns the associated NormalUser.
     *
     * @param email the email of the normal user being retrieved
     * @return the normal user associated with the given email
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
     * Getter for all users in the user database.
     *
     * @return a list of all Users in the database
     */
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(allNormals);
        allUsers.addAll(allAdmins);
        return allUsers;
    }

    /**
     * Getter for all normal users in the user database.
     *
     * @return a list of all normal users in the user database
     */
    public List<NormalUser> getAllNormals() {
        return allNormals;
    }

    /**
     * Getter for all admins in the user database.
     *
     * @return a list of all admins in the user database
     */
    public List<AdminUser> getAllAdmins() {
        return allAdmins;
    }

    /**
     * Takes the given username and returns the associated account password.
     *
     * @param username the username of the AdminUser whose password is being searched for
     * @return the account password associated with the given username
     */
    public String adminUsernamePassword(String username) {
        for (User u : getAllAdmins()) {
            if (u.getUsername().equals(username))
                return u.getPassword();
        }
        return null;
    }

    /**
     * Takes the given username for a NormalUser and returns the associated account password.
     *
     * @param username the username of the NormalUser whose password is being searched for
     * @return the account password associated with the given username
     */
    public String normalUsernamePassword(String username) {
        for (User u : getAllNormals()) {
            if (u.getUsername().equals(username))
                return u.getPassword();
        }
        return null;
    }

    /**
     * Takes the given username for a User and returns the associated account password.
     *
     * @param username the username of the User whose password is being searched for
     * @return the account password associated with the given username
     */
    public String usernamePassword(String username) {
        for (User u : getAllNormals()) {
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
     * Takes the given email and returns the associated admin account password.
     *
     * @param email the email of the AdminUser whose password is being searched for
     * @return the account password associated with the given email
     */
    public String adminEmailPassword(String email) {
        for (User u : getAllAdmins()) {
            if (u.getEmail().equals(email))
                return u.getPassword();
        }
        return null;
    }

    /**
     * Takes the given email and returns the associated Normal account password.
     *
     * @param email the email of the NormalUser whose password is being searched for
     * @return the account password associated with the given email
     */
    public String normalEmailPassword(String email) {
        for (User u : getAllNormals()) {
            if (u.getEmail().equals(email))
                return u.getPassword();
        }
        return null;
    }

    /**
     * Checks if a user with the given email already exists in the user database,
     * regardless of what type of user is passed in.
     *
     * @param email the email being checked for whether it's already taken or not
     * @return true if user with given email exists, false otherwise
     */
    public boolean emailExists(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a user with the given username already exists in the user database,
     * regardless of what type of user is passed in.
     *
     * @param username the username being checked for whether it's already taken or not
     * @return true if user with given username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a user with the given email already exists in the user database.
     * Searches separately in normal or admin database depending on given boolean isAdmin.
     *
     * @param email   the email being checked for whether it's already taken or not
     * @param isAdmin true if the user is an admin, false otherwise
     * @return true if user with the given email exists in the correct database, false otherwise
     */
    public boolean emailExists(String email, boolean isAdmin) {
        if (isAdmin) {
            for (AdminUser u : allAdmins) {
                if (u.getEmail().equals(email))
                    return true;
            }
        } else {
            for (NormalUser u : allNormals) {
                if (u.getEmail().equals(email))
                    return true;
            }
        }
        return false;
    }

    // made the method below the inverse of the original usernameExists function
    // cuz Intellij will give a warning otherwise
    /**
     * Checks if a user with the given username does NOT exist in the user database.
     * Searches separately in normal or admin database depending on given boolean isAdmin.
     *
     * @param username the username being checked for whether it's already taken or not
     * @param isAdmin  true if the user is an admin, false otherwise
     * @return true if the given username does NOT exist in the correct database, false otherwise
     */
    public boolean usernameNotExists(String username, boolean isAdmin) {
        if (isAdmin) {
            for (AdminUser u : allAdmins) {
                if (u.getUsername().equals(username))
                    return false;
            }
        } else {
            for (NormalUser u : allNormals) {
                if (u.getUsername().equals(username))
                    return false;
            }
        }
        return true;
    }


    /**
     * Return usernames of all accounts that needs to be frozen.
     *
     * @return usernames to freeze
     */
    public List<String> getUsernamesToFreeze() {
        return usernamesToFreeze;
    }


    public void addUsernamesToFreeze(String username) {
        usernamesToFreeze.add(username);
    }


    /**
     * Clears the usernamesToFreeze arraylist.
     */
    public void clearUsernamesToFreeze() {
        usernamesToFreeze.clear();
    }

    /**
     * Returns the next admin ID.
     *
     * @return the next admin ID
     */
    public int getAdminId() {
        return allAdmins.size() + 1;
    }

    /**
     * Getter for the list of unfreeze requests.
     *
     * @return the list of frozen normal users who have requested their account be unfrozen
     */
    public List<NormalUser> getUnfreezeRequests() {
        List<NormalUser> userRequests = new ArrayList<>();
        for (String username : unfreezeRequests) {
            userRequests.add(getNormalByUsername(username));
        }
        return userRequests;
    }


    /**
     * Returns the username of someone's unfreeze request at the given index.
     *
     * @param index the index of an unfreeze request in the list
     * @return the username at the given index
     */
    public String getUnfreezeRequest(int index) {
        return unfreezeRequests.get(index - 1);
    }

    /**
     * Returns the total number of unfreeze requests.
     *
     * @return the total number of unfreeze requests
     */
    public int getNumUnfreezeRequest() {
        return unfreezeRequests.size();
    }

    /**
     * Removes the given username from the list of unfreeze requests.
     *
     * @param username the username being removed from the unfreeze requests
     */
    public void removeUnfreezeRequest(String username) {
        unfreezeRequests.remove(username);
    }

    /**
     * Adds the given username to the list of unfreeze requests.
     *
     * @param username the username of the normal user requesting to be unfrozen
     */
    public void addUnfreezeRequest(String username) {
        unfreezeRequests.add(username);
    }

    /**
     * Checks if user already sent an unfreeze request.
     *
     * @param username the username of the user who's being checked
     * @return true if the user already sent an unfreeze request, false otherwise
     */
    public boolean containsUnfreezeRequest(String username) {
        return unfreezeRequests.contains(username);
    }
}
