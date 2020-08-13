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
 * last modified 2020-08-12
 */
public class UserManager extends Manager implements Serializable {
    private List<NormalUser> allNormals;
    private List<AdminUser> allAdmins;
    private List<String> allNormalUsernames;

    private List<String> usernamesToFreeze;
    private List<String> unfreezeRequests;
    private List<String> usernamesOnVacation;

    private UserNotificationHelper notifHelper;
    private UserThresholds thresholdSystem;

    /**
     * Creates a <UserManager></UserManager>, setting all lists to empty by default.
     */
    public UserManager() {
        allNormals = new ArrayList<>();
        allAdmins = new ArrayList<>();
        allNormalUsernames = new ArrayList<>();

        usernamesToFreeze = new ArrayList<>();
        unfreezeRequests = new ArrayList<>();
        usernamesOnVacation = new ArrayList<>();

        notifHelper = new UserNotificationHelper();
        thresholdSystem = new UserThresholds();
    }

    /**
     * Creates a new <NormalUser></NormalUser> with given username, email, and password,
     * then adds it to the list of all normal users.
     *
     * @param username the new user's username
     * @param email    the new user's email
     * @param password the new user's password
     * @param homeCity the new user's home city
     */
    public void createNormalUser(String username, String email, String password, String homeCity) {
        allNormals.add(new NormalUser(username, email, password, homeCity));
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
     * Returns the next admin ID in the system.
     *
     * @return the next admin ID
     */
    public int getNextAdminID() {
        return allAdmins.size() + 1;
    }

    /**
     * Returns the admin ID of the admin account associated with the given username.
     *
     * @param username the username of the admin whose ID is being retrieved
     * @return the ID of the admin account associated with the given username
     */
    public int getAdminID(String username) {
        return getAdminByUsername(username).getAdminID();
    }

    /**
     * Takes in an admin ID and returns the associated admin's username.
     *
     * @param adminID the ID of the admin whose username is being retrieved
     * @return the username of the admin associated with the given ID.
     */
    public String getAdminUsernameByID(int adminID) {
        for (AdminUser adminUser : allAdmins) {
            if (adminUser.getAdminID() == adminID) {
                return adminUser.getUsername();
            }
        }
        return ("");
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
     * Changes the <User></User>'s password
     * @param username the username of the user
     * @param newPassword the new password
     */
    public void changeUserPassword(String username, String newPassword){
        getUserByUsername(username).setPassword(newPassword);
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
     * Getter for the list of unfreeze requests.
     *
     * @return the list of usernames of frozen normal users who have requested their account be unfrozen
     */
    public List<String> getUnfreezeRequests() {
        return unfreezeRequests;
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
     * Adds the given username to the list of unfreeze requests.
     *
     * @param username the username of the normal user requesting to be unfrozen
     */
    public void addUnfreezeRequest(String username) {
        unfreezeRequests.add(username);
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
     * Getter for whether or not the account associated with the given username is on vacation.
     *
     * @param username the username to query
     * @return true iff the account associated with the given username is on vacation
     */
    public boolean getNormalUserOnVacation(String username) {
        return getNormalByUsername(username).getIsOnVacation();
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
     * Getter for if the given item ID is involved in the given user's trade requests.
     *
     * @param username the normal user's username
     * @param itemID   the item ID
     * @return true iff the item is requested in trade
     */
    public boolean isRequestedInTrade(String username, long itemID) {
        NormalUser currUser = getNormalByUsername(username);
        for (Map.Entry<String[], long[]> entry : currUser.getTradeRequests().entrySet()) {
            if (entry.getValue()[0] == itemID || entry.getValue()[1] == itemID) {
                return true;
            }
        }
        return false;
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
     * Removes the trade request with the given key from both accounts involved in the request.
     *
     * @param usernames the usernames of the two traders
     */
    public void removeTradeRequestBothUsers(String[] usernames) {
        NormalUser initiator = getNormalByUsername(usernames[0]);
        NormalUser recipient = getNormalByUsername(usernames[1]);
        initiator.removeTradeRequest(usernames);
        recipient.removeTradeRequest(usernames);
    }

    /**
     * Remove all trade requests involving the given item ID from both accounts involved in the request.
     *
     * @param username the username of the owner of the given item
     * @param itemID   the item ID to remove trade requests for
     * @return the keys of the trade requests removed
     */
    public List<String[]> removeTradeRequestByItemID(String username, long itemID) {
        NormalUser currUser = getNormalByUsername(username);
        String otherUsername;
        List<String[]> otherUsernames = new ArrayList<>();

        for (Map.Entry<String[], long[]> entry : currUser.getTradeRequests().entrySet()) {
            if (entry.getValue()[0] == itemID || entry.getValue()[1] == itemID) {
                if (entry.getKey()[0].equals(username)) {
                    otherUsername = entry.getKey()[1];
                } else {
                    otherUsername = entry.getKey()[0];
                }
                currUser.getTradeRequests().remove(entry.getKey());
                getNormalByUsername(otherUsername).getTradeRequests().remove(entry.getKey());
                otherUsernames.add(entry.getKey());
            }
        }
        return otherUsernames;
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
    public void addToNormalUserInventory(long itemIDToAdd, String username) {
        getNormalByUsername(username).addInventory(itemIDToAdd);
    }

    /**
     * Removes the given item ID from inventory of account with associated username.
     *
     * @param itemID   the item ID to be removed from inventory
     * @param username the username of the account whose inventory is being modified
     */
    public void removeFromNormalUserInventory(long itemID, String username) {
        getNormalByUsername(username).removeInventory(itemID);
    }

    /**
     * Getter for the pending inventory of the account associated with the given username.
     *
     * @param username the username of the account whose pending inventory is being retrieved
     * @return the pending inventory of the account associated with the given username
     */
    public List<Long> getNormalUserPending(String username) {
        return getNormalByUsername(username).getPendingInventory();
    }

    /**
     * Adds the given item ID to the pending inventory of the account associated with the given username.
     *
     * @param itemID   the item ID to be added to pending inventory
     * @param username the username of the account whose pending inventory is being modified
     */
    public void addToNormalUserPending(long itemID, String username) {
        getNormalByUsername(username).addPendingInventory(itemID);
    }

    /**
     * Removes the given item ID from the pending inventory of the account associated with the given username.
     *
     * @param itemIDToRemove the item ID being removed from pending inventory
     * @param username       the username of the account whose pending inventory is being modified
     */
    public void removeFromNormalUserPending(long itemIDToRemove, String username) {
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
    public void addToNormalUserWishlist(long itemIDToAdd, String username) {
        getNormalByUsername(username).addWishlist(itemIDToAdd);
    }

    /**
     * Removes the given item ID from the wishlist of the account associated with the given username.
     *
     * @param itemID   the item ID being removed from wishlist
     * @param username the username of the account whose wishlist is being modified
     */
    public void removeFromNormalUserWishlist(long itemID, String username) {
        getNormalByUsername(username).removeWishlist(itemID);
    }

    /**
     * Takes in an item ID and returns whether or not it's in the given user's wishlist
     *
     * @param itemID   the item ID to query
     * @param username the username of the account whose wishlist is being searched
     * @return true iff the given item ID is in the given user's wishlist
     */
    public boolean isInNormalUserWishlist(long itemID, String username) {
        return getNormalUserWishlist(username).contains(itemID);
    }

    /**
     * Return whether or not the account associated with given username is frozen.
     *
     * @param username the username whose account status is being retrieved
     * @return true iff user is frozen
     */
    public boolean getNormalUserIsFrozen(String username) {
        return getNormalByUsername(username).getIsFrozen();
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

    /**
     * Gets the notification helper which gives access to the methods that help
     * create notifications triggered by certain user actions.
     *
     * @param username the username of the user being notified
     *                 (except in the case where an admin's username is passed in,
     *                 which doesn't notify the admin but records an action in the activity log)
     * @return the notification helper
     */
    public UserNotificationHelper notifyUser(String username) {
        notifHelper.setCurrUserToNotify(getUserByUsername(username));
        return notifHelper;
    }

    /**
     * Gets the threshold system which gives access to the methods that help modify the threshold values.
     *
     * @return the threshold system
     */
    public UserThresholds getThresholdSystem() {
        return thresholdSystem;
    }

    /**
     * Increases the number of incomplete trades by one for the account associated with the given username.
     *
     * @param username the username of the account whose number of incomplete trades is being incremented by 1
     */
    public void increaseNormalUserNumIncomplete(String username) {
        getNormalByUsername(username).increaseNumIncomplete();
    }

    /**
     * Getter for number of incomplete trades made by the account associated with the given username.
     *
     * @param username the username of the account whose number of incomplete trades is being retrieved
     * @return the number of incomplete trades made by the account associated with the given username
     */
    public int getNormalUserNumIncomplete(String username) {
        return getNormalByUsername(username).getNumIncomplete();
    }

    /**
     * Getter for the trade requests of the account associated with the given username.
     *
     * @param username the username of the account whose trade requests are being retrieved
     * @return the trade requests of the account associated with the given username
     */
    public Map<String[], long[]> getNormalUserTradeRequests(String username) {
        return getNormalByUsername(username).getTradeRequests();
    }
}