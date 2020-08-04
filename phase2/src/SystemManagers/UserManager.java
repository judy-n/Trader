package SystemManagers;

import Entities.User;
import Entities.NormalUser;
import Entities.AdminUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stores all <User></User>s in the system.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @author Yingjia Liu
 * @author Kushagra Mehta
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-03
 */
public class UserManager extends Manager implements Serializable {
    private List<NormalUser> allNormals;
    private List<AdminUser> allAdmins;
    private List<String> usernamesToFreeze;
    private List<String> unfreezeRequests;
    private List<String> usernamesOnVacation;
    private int[] currDefaultThresholds;
    private UserNotificationHelper notifHelper;
    private List<String> allNormalUsernames;

    /**
     * Creates a <UserManager></UserManager>, setting all lists to empty by default.
     */
    public UserManager() {
        allNormals = new ArrayList<>();
        allAdmins = new ArrayList<>();
        usernamesToFreeze = new ArrayList<>();
        unfreezeRequests = new ArrayList<>();
        usernamesOnVacation = new ArrayList<>();
        currDefaultThresholds = new int[4];
        notifHelper = new UserNotificationHelper();
        allNormalUsernames = new ArrayList<>();
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
        allNormals.add(new NormalUser(username, email, password, homeCity, currDefaultThresholds));
        allNormalUsernames.add(username);
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
     * Getter or all normal users' usernames in the system.
     *
     * @return a list of all normal users' usernames in the system
     */
    public List<String> getAllNormalUsernames() {
        return allNormalUsernames;
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
        return allNormalUsernames.contains(username);
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
     * Returns the username at the given index in the list of unfreeze requests.
     *
     * @param index the index of the user being unfrozen
     * @return the username at the given index in the list of unfreeze requests
     */
    public String getUnfreezeUsername(int index) {
        return unfreezeRequests.get(index);
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

    private User getUserByUsername(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    private User getUserByEmail(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Gets a user by the given username or email (regardless of type of user).
     *
     * @param usernameOrEmail the user's email
     * @return the user with the given email
     */
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        if (usernameOrEmail.contains("@")) {
            return getUserByEmail(usernameOrEmail);
        } else {
            return getUserByUsername(usernameOrEmail);
        }
    }

    /**
     * Adds the given username to the list of usernames on vacation
     * and sets their account status to on vacation.
     *
     * @param username the username of a user on vacation
     */
    public void addUsernamesOnVacation(String username) {
        usernamesOnVacation.add(username);
        getNormalByUsername(username).setOnVacation(true);
    }

    /**
     * Removes the given username from the list of usernames on vacation
     * and sets their account status to NOT on vacation.
     *
     * @param username the username of a user not on vacation
     */
    public void removeUsernamesOnVacation(String username) {
        usernamesOnVacation.remove(username);
        getNormalByUsername(username).setOnVacation(false);
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
     * Getter for whether or not the account associated with the given username is on vacation.
     *
     * @param username the username to query
     * @return true iff the account associated with the given username is on vacation
     */
    public boolean getNormalUserOnVacation(String username) {
        return getNormalByUsername(username).getIsOnVacation();
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
     * Getter for a normal user's number of items borrowed in their trade requests.
     *
     * @param username the username of the normal user
     * @return the number of items they've requested to borrow an item
     */
    public int getNormalUserTimesBorrowed(String username) {
        return getNormalByUsername(username).getTimesBorrowed();
    }

    /**
     * Getter for a normal user's number of items lent in their trade requests.
     *
     * @param username the username of the normal user
     * @return the number of items they've offered to lend an item
     */
    public int getNormalUserTimesLent(String username) {
        return getNormalByUsername(username).getTimesLent();
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

    /**
     * Makes a trade request using the details given and adds it to the list of trade requests
     * for both the involved users.
     *
     * @param usernames the usernames of the two traders
     * @param itemIDs   the item IDs involved in the trade request
     */
    public void addTradeRequestBothUsers(String[] usernames, long[] itemIDs) {
        NormalUser initiator = getNormalByUsername(usernames[0]);
        NormalUser recipient = getNormalByUsername(usernames[1]);
        initiator.addTradeRequest(usernames, itemIDs);
        recipient.addTradeRequest(usernames, itemIDs);
    }

    /**
     * Getter for the inventory of the user associated with the given username.
     *
     * @param username the username of the user whose inventory is being retrieved
     * @return the given user's inventory
     */
    public List<Long> getNormalUserInventory(String username) {
        return getNormalByUsername(username).getInventory();
    }

    /**
     * Adds the given item ID to the inventory of the account associated with the given username.
     *
     * @param itemIDToAdd the item ID being added to inventory
     * @param username    the username of the account whose inventory is being modified
     */
    public void addNormalUserInventory(long itemIDToAdd, String username) {
        getNormalByUsername(username).addInventory(itemIDToAdd);
    }

    /**
     * Removes the given item ID from the pending inventory of the account associated with the given username.
     *
     * @param itemIDToRemove the item ID being removed from pending inventory
     * @param username       the username of the account whose pending inventory is being modified
     */
    public void removeNormalUserPending(long itemIDToRemove, String username) {
        getNormalByUsername(username).removePendingInventory(itemIDToRemove);
    }

    /**
     * Getter for the wishlist of the user associated with the given username.
     *
     * @param username the username of the user whose wishlist is being retrieved
     * @return the given user's wishlist
     */
    public List<Long> getNormalUserWishlist(String username) {
        return getNormalByUsername(username).getWishlist();
    }

    /**
     * Adds the given item ID to the wishlist of the user associated with the given username.
     *
     * @param itemIDToAdd the item ID being added to wishlist
     * @param username    the username of the user whose wishlist is being modified
     */
    public void addNormalUserWishlist(long itemIDToAdd, String username) {
        getNormalByUsername(username).addWishlist(itemIDToAdd);
    }

    /**
     * Freezes the account associated with the given username.
     *
     * @param usernameToFreeze the username of the account being frozen
     */
    public void freezeNormalUser(String usernameToFreeze) {
        getNormalByUsername(usernameToFreeze).freeze();
    }

    /**
     * Getter for the weekly trade limit of the user associated with the given username.
     *
     * @param username the username of the user whose weekly trade limit is being retrieved
     * @return the weekly trade limit of the user associated with the given username
     */
    public int getNormalUserWeeklyTradeMax(String username) {
        return getNormalByUsername(username).getWeeklyTradeMax();
    }

    /**
     * Setter for the weekly trade limit of the user associated with the given username.
     *
     * @param username  the username of the user whose weekly trade limit is being modified
     * @param threshold the new weekly trade limit
     */
    public void setNormalUserWeeklyTradeMax(String username, int threshold) {
        getNormalByUsername(username).setWeeklyTradeMax(threshold);
    }

    /**
     * Getter for the meeting edit limit of the user associated with the given username.
     *
     * @param username the username of the user whose meeting edit limit is being retrieved
     * @return the meeting edit limit of the user associated with the given username
     */
    public int getNormalUserMeetingEditMax(String username) {
        return getNormalByUsername(username).getMeetingEditMax();
    }

    /**
     * Setter for the meeting edit limit of the user associated with the given username.
     *
     * @param username  the username of the user whose meeting edit limit is being modified
     * @param threshold the new meeting edit limit
     */
    public void setNormalUserMeetingEditMax(String username, int threshold) {
        getNormalByUsername(username).setMeetingEditMax(threshold);
    }

    /**
     * Getter for the minimum lending over borrowing limit of the user associated with the given username.
     *
     * @param username the username of the user whose minimum lending over borrowing limit is being retrieved
     * @return the minimum lending over borrowing limit of the user associated with the given username
     */
    public int getNormalUserLendMinimum(String username) {
        return getNormalByUsername(username).getLendMinimum();
    }

    /**
     * Setter for the minimum lending over borrowing limit of the user associated with the given username.
     *
     * @param username  the username of the user whose minimum lending over borrowing limit is being modified
     * @param threshold the new minimum lending over borrowing limit
     */
    public void setNormalUserLendMinimum(String username, int threshold) {
        getNormalByUsername(username).setLendMinimum(threshold);
    }

    /**
     * Getter for the incomplete trade limit of the user associated with the given username.
     *
     * @param username the username of the user whose incomplete trade limit is being retrieved
     * @return the incomplete trade limit of the user associated with the given username
     */
    public int getNormalUserIncompleteMax(String username) {
        return getNormalByUsername(username).getIncompleteMax();
    }

    /**
     * Setter for the incomplete trade limit of the user associated with the given username.
     *
     * @param username  the username of the user whose incomplete trade limit is being modified
     * @param threshold the new incomplete trade limit
     */
    public void setNormalUserIncompleteMax(String username, int threshold) {
        getNormalByUsername(username).setIncompleteMax(threshold);
    }

    /**
     * Sets all normal user weekly trade max thresholds to the given number.
     *
     * @param threshold the new threshold
     */
    public void setAllNormalUserWeeklyTradeMax(int threshold) {
        for (String username : allNormalUsernames) {
            setNormalUserWeeklyTradeMax(username, threshold);
        }
    }

    /**
     * Sets all normal user incomplete trade max thresholds to the given number.
     *
     * @param threshold the new threshold
     */
    public void setAllNormalUserIncompleteMax(int threshold) {
        for (String username : allNormalUsernames) {
            setNormalUserIncompleteMax(username, threshold);
        }
    }

    /**
     * Sets all normal user minimum lending thresholds to the given number.
     *
     * @param threshold the new threshold
     */
    public void setAllNormalUserLendMinimum(int threshold) {
        for (String username : allNormalUsernames) {
            setNormalUserLendMinimum(username, threshold);
        }
    }

    /**
     * Sets all normal user max meeting edit threshold to the given number.
     *
     * @param threshold the new threshold
     */
    public void setALlNormalUserMeetingEditMax(int threshold) {
        for (String username : allNormalUsernames) {
            setNormalUserMeetingEditMax(username, threshold);
        }
    }

    /**
     * Sets the <currentThresholds></currentThresholds> (the default thresholds for new users) to the given thresholds.
     *
     * @param thresholds the new threshold values
     */
    public void setCurrentThresholds(int[] thresholds) {
        currDefaultThresholds = thresholds;
    }

    /**
     * Getter for the <currentThresholds></currentThresholds> (default threshold values).
     *
     * @return the system's default thresholds
     */
    public int[] getCurrentThresholds() {
        return currDefaultThresholds;
    }

    /**
     * Returns the number of threshold values in the program.
     *
     * @return the number of threshold values in the program
     */
    public int getNumThresholds() {
        return currDefaultThresholds.length;
    }

    /**
     * Getter for a user's password given the associated username or email.
     *
     * @param usernameOrEmail the username of the user
     * @return the associated password
     */
    public String getUserPassword(String usernameOrEmail) {
        return getUserByUsernameOrEmail(usernameOrEmail).getPassword();
    }

    /**
     * Return true iff user with given username or email is an admin.
     *
     * @param usernameOrEmail the given username or email
     * @return true iff user is an admin
     */
    public boolean isAdmin(String usernameOrEmail) {
        return getUserByUsernameOrEmail(usernameOrEmail) instanceof AdminUser;
    }

    private NormalUser getNormalByUsernameOrEmail(String usernameOrEmail) {
        if (usernameOrEmail.contains("@")) {
            return getNormalByEmail(usernameOrEmail);
        } else {
            return getNormalByUsername(usernameOrEmail);
        }
    }

    private AdminUser getAdminByUsernameOrEmail(String usernameOrEmail) {
        if (usernameOrEmail.contains("@")) {
            return getAdminByEmail(usernameOrEmail);
        } else {
            return getAdminByUsername(usernameOrEmail);
        }
    }

    /**
     * Return whether or not the account associated with given username is frozen.
     *
     * @param username the user's username or email
     * @return true iff user is frozen
     */
    public boolean getNormalUserIsFrozen(String username) {
        return getNormalByUsernameOrEmail(username).getIsFrozen();
    }

    /**
     * Getter for the notification helper.
     * Gives access to the methods that help create notifications triggered by certain user actions.
     *
     * @param username the username of the user being notified
     * @return the notification helper
     */
    public UserNotificationHelper getNotifHelper(String username) {
        notifHelper.setCurrUserToNotify(getNormalByUsername(username));
        return notifHelper;
    }

    /**
     * Gets a username of a user, given the associated username or email of that user.
     *
     * @param usernameOrEmail the associated username or email
     * @return the associated username
     */
    public String getUsername(String usernameOrEmail) {
        return getUserByUsernameOrEmail(usernameOrEmail).getUsername();
    }

    /**
     * Increases NumIncomplete threshold for NormalUser with associated username or email
     *
     * @param usernameOrEmail the associated username or email
     */
    public void increaseNormalUserNumIncomplete(String usernameOrEmail) {
        getNormalByUsernameOrEmail(usernameOrEmail).increaseNumIncomplete();
    }

    /**
     * Getter for Number of Incomplete trades made by Normal User with associated username or email.
     *
     * @param usernameOrEmail the associated username or email
     * @return NumIncomplete, the number of incomplete trades
     */
    public int getNormalUserNumIncomplete(String usernameOrEmail) {
        return getNormalByUsernameOrEmail(usernameOrEmail).getNumIncomplete();
    }

    /**
     * Returns the ID of the Admin with the associated username or email.
     *
     * @param usernameOrEmail the Admin's username or email
     * @return the Admin's ID
     */
    public int getAdminID(String usernameOrEmail) {
        return getAdminByUsernameOrEmail(usernameOrEmail).getAdminID();
    }

    /**
     * Return true iff user with associated is not equal to null.
     *
     * @param username the associated username or email
     * @return true iff user exists
     */
    public boolean doesUserExist(String username) {
        return getUserByUsernameOrEmail(username) != null;
    }

    /**
     * Removes item with associated itemID from inventory of Normal User with associated username or email.
     *
     * @param itemID          the itemID to be removed
     * @param usernameOrEmail the Normal User's username or email
     */
    public void removeNormalUserinventory(long itemID, String usernameOrEmail) {
        getNormalByUsernameOrEmail(usernameOrEmail).removeInventory(itemID);
    }

    /**
     * Getter for a Normal User's pending inventory.
     *
     * @param usernameOrEmail the associated Normal User's username or Email
     * @return the Normal User's pending inventory
     */
    public List<Long> getNormalUserPendingInventory(String usernameOrEmail) {
        return getNormalByUsernameOrEmail(usernameOrEmail).getPendingInventory();
    }

    /**
     * Adds item with itemID to the Normal User's pending inventory with given username or email.
     *
     * @param itemID          the itemID to be added to pending inventory
     * @param usernameOrEmail the user's username or email
     */
    public void addNormalUserPendingInventory(long itemID, String usernameOrEmail) {
        getNormalByUsernameOrEmail(usernameOrEmail).addPendingInventory(itemID);
    }

    /**
     * Getter for a Normal User's trade requests, given associated username or email.
     *
     * @param usernameOrEmail the associated username or email
     * @return the trade requests
     */
    public Map<String[], long[]> getNormalUserTradeRequests(String usernameOrEmail) {
        return getNormalByUsernameOrEmail(usernameOrEmail).getTradeRequests();
    }

    /**
     * Removes given tradeRequests from the Normal User with given username or email's trade requests.
     *
     * @param key             the key of the trade request to be removed
     * @param usernameOrEmail the username or email of the user
     */
    public void removeTradeRequests(String[] key, String usernameOrEmail) {
        getNormalByUsernameOrEmail(usernameOrEmail).removeTradeRequests(key);
    }

    /**
     * Removes the item with the given itemID from the user with the given username or email's wishlist.
     *
     * @param itemID          the item ID to be removed
     * @param usernameOrEmail the username or email of the normal user
     */
    public void removeFromNormalUserWishlist(long itemID, String usernameOrEmail) {
        getNormalByUsernameOrEmail(usernameOrEmail).removeWishlist(itemID);
    }
}