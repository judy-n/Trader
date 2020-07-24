import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all <User></User>s in the system.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @author Yingjia Liu
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-23
 */
public class UserManager extends Manager implements Serializable {
    private List<NormalUser> allNormals;
    private List<AdminUser> allAdmins;
    private List<String> usernamesToFreeze;
    private List<String> unfreezeRequests;
    private List<String> usernamesOnVacation;

    /**
     * Creates a <UserManager></UserManager>, setting all lists to empty by default.
     */
    public UserManager() {
        allNormals = new ArrayList<>();
        allAdmins = new ArrayList<>();
        usernamesToFreeze = new ArrayList<>();
        unfreezeRequests = new ArrayList<>();
        usernamesOnVacation = new ArrayList<>();
    }

    /**
     * Creates a new <NormalUser></NormalUser> with given username, email, and password,
     * then adds it to the list of all normal users.
     *
     * @param username the new user's username
     * @param email    the new user's email
     * @param password the new user's password
     * @param homeCity the new user's homeCity
     */
    public void createNormalUser(String username, String email, String password, String homeCity) {
        allNormals.add(new NormalUser(username, email, password, homeCity));
    }

    /**
     * Creates a new <AdminUser></AdminUser> with given username, email, and password,
     * then adds it to the list of all admins.
     *
     * @param username the new user's username
     * @param email    the new user's email
     * @param password the new user's password
     */
    public void createAdminUser(String username, String email, String password) {
        allAdmins.add(new AdminUser(username, email, password, getNextAdminID()));
    }

    /**
     * Takes the given username and returns the associated <NormalUser></NormalUser>.
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
     * Takes the given username that belongs to an admin and returns the associated <AdminUser></AdminUser>.
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
     * Takes the given email and returns the associated <NormalUser></NormalUser>.
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
     * Takes the given email that belongs to an admin and returns the associated <AdminUser></AdminUser>.
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
     * @return a list of all users in the database
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
     * Takes the given username for any type of user and returns the associated account password.
     *
     * @param username the username of the user whose password is being retrieved
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
     * Takes the given email for any type of user and returns the associated account password.
     *
     * @param email the email of the user whose password is being retrieved
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
     * Checks if a normal user with the given username already exists in the user database.
     *
     * @param username the username being checked for whether it's already taken or not
     * @return true if user with given username exists, false otherwise
     */
    public boolean normalUsernameExists(String username) {
        for (User u : allNormals) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return usernames of all accounts that needs to be frozen.
     *
     * @return the list of usernames belonging to accounts that need to be frozen
     */
    public List<String> getUsernamesToFreeze() {
        return usernamesToFreeze;
    }

    /**
     * Adds the given username to the list of usernames to freeze.
     *
     * @param username the username of a user that needs to be frozen
     */
    public void addUsernamesToFreeze(String username) {
        usernamesToFreeze.add(username);
    }

    /**
     * Clears the list of usernames to freeze.
     */
    public void clearUsernamesToFreeze() {
        usernamesToFreeze.clear();
    }

    /**
     * Returns the next admin ID in the system.
     *
     * @return the next admin ID
     */
    public int getNextAdminID() {
        return allAdmins.size() + 1;
    }

    /**
     * Getter for the list of unfreeze requests.
     *
     * @return the list of usernames of frozen normal users who have requested their account be unfrozen
     */
    public List<String> getUnfreezeRequests() {
        return unfreezeRequests;
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
     * Removes the user at the given index from the list of unfreeze requests and unfreezes them.
     *
     * @param index the index of the user being unfrozen
     */
    public void removeUnfreezeRequest(int index) {
        NormalUser unfreezeUser = getNormalByUsername(unfreezeRequests.get(index));
        unfreezeUser.unfreeze();
        unfreezeRequests.remove(unfreezeUser.getUsername());
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
     * Gets a user by the given username (regardless of type of user).
     *
     * @param username the user's username
     * @return the user with the given username
     */
    public User getUserByUsername(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Gets a user by the given email (regardless of type of user).
     *
     * @param email the user's email
     * @return the user with the given email
     */
    public User getUserByEmail(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Adds the given username to the list of usernames on vacation.
     *
     * @param username the username of a user on vacation
     */
    public void addUsernamesOnVacation(String username) {
        usernamesOnVacation.add(username);
    }

    /**
     * Clears the list of usernames that are on vacation.
     */
    public void clearUsernamesOnVacation() {
        usernamesOnVacation.clear();
    }

    /**
     * Returns usernames of all account that are on vacation.
     *
     * @return usernamesOnVacation
     */
    public List<String> getUsernamesOnVacation() {
        return usernamesOnVacation;
    }

    /**
     * Setter for the home city of the normal user with the given username.
     *
     * @param username the username
     * @param homeCity the home city
     */
    public void setNormalUserHomeCity(String username, String homeCity) {
        getNormalByUsername(username).setHomeCity(homeCity);
    }

    /**
     * Getter for the home city of the normal user with the given username.
     *
     * @param username the username
     * @return that user's home city
     */
    public String getNormalUserHomeCity(String username) {
        return getNormalByUsername(username).getHomeCity();
    }

    /**
     * Getter for a normal user's number of times borrowed.
     *
     * @param username the username of the normal user
     * @return the number of times they've borrowed
     */
    public int getNormalUserTimesBorrowed(String username) {
        return getNormalByUsername(username).getTimesBorrowed();
    }

    /**
     * Getter for if a normal user is requested in trade based on the item's id and the user's username.
     *
     * @param username the normal user's username
     * @param id       the item id
     * @return true iff the item is requested in trade
     */
    public boolean isRequestedInTrade(String username, long id) {
        return getNormalByUsername(username).isRequestedInTrade(id);
    }
}


