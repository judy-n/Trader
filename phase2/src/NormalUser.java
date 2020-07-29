import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a normal user in our trade program.
 * Normal users are allowed to trade items with other normal users and manage their inventory and wishlist.
 * A normal user has several threshold values that restrict their trade activity and can be modified by an admin.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @author Yingjia Liu
 * @author Kushagra Mehta
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-28
 */
public class NormalUser extends User implements Serializable {
    private List<Long> inventory;
    private List<Long> pendingInventory;
    private List<Long> wishlist;
    private Map<String[], long[]> tradeRequests;
    private String homeCity;

    /* status true if the user is frozen */
    private boolean isFrozen;

    /* status true if the user is on vacation */
    private boolean isOnVacation;

    /* number of incomplete trades the user has */
    private int numIncomplete;

    /* the maximum number of transactions this user can schedule in a week */
    private int weeklyTradeMax;

    {
        try {
            String line = Files.readAllLines(Paths.get("src/thresholds.txt")).get(0);
            String[] splitLine = line.split(":");
            weeklyTradeMax = Integer.parseInt(splitLine[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* the maximum number of times this user may edit any of their trade's meeting details */
    private int meetingEditMax;

    {
        try {
            String line = Files.readAllLines(Paths.get("src/thresholds.txt")).get(1);
            String[] splitLine = line.split(":");
            meetingEditMax = Integer.parseInt(splitLine[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* to request a trade, this user must have lent at least lendMinimum item(s) more than they have borrowed */
    private int lendMinimum;
    {
        try {
            String line = Files.readAllLines(Paths.get("src/thresholds.txt")).get(2);
            String[] splitLine = line.split(":");
            lendMinimum = Integer.parseInt(splitLine[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* the maximum number of incomplete trades this user can have before their account is at risk of being frozen */
    private int incompleteMax;
    {
        try {
            String line = Files.readAllLines(Paths.get("src/thresholds.txt")).get(3);
            String[] splitLine = line.split(":");
            incompleteMax = Integer.parseInt(splitLine[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a <NormalUser></NormalUser> with the given username, email, and password.
     * Also initializes default empty inventory, wishlist, and tradeRequests, and account status non-frozen.
     *
     * @param username the username being assigned to this <NormalUser></NormalUser>
     * @param email    the email address being assigned to this <NormalUser></NormalUser>
     * @param password the password being assigned to this <NormalUser></NormalUser>
     */
    public NormalUser(String username, String email, String password) {
        super(username, email, password);
        inventory = new ArrayList<>();
        pendingInventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradeRequests = new HashMap<>();
        isFrozen = false;
        isOnVacation = false;
    }

    /**
     * Creates a <NormalUser></NormalUser> with the given username, email, and password, and homeCity
     * Also initializes default empty inventory, wishlist, and tradeRequests, and account status non-frozen.
     *
     * @param username the username being assigned to this <NormalUser></NormalUser>
     * @param email    the email address being assigned to this <NormalUser></NormalUser>
     * @param password the password being assigned to this <NormalUser></NormalUser>
     * @param homeCity the homeCity of this NormalUser
     */
    public NormalUser(String username, String email, String password, String homeCity) {
        super(username, email, password);
        inventory = new ArrayList<>();
        pendingInventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradeRequests = new HashMap<>();
        isFrozen = false;
        isOnVacation = false;
        this.homeCity  = homeCity;
    }

    /**
     * Returns the number of times this user has requested to borrow an item from someone else.
     * Only counts requests that have yet to be accepted or rejected.
     *
     * @return the number of times this user has requested to borrow
     */
    public int getTimesBorrowed() {
        int timesBorrowed = 0;
        for (String[] key : tradeRequests.keySet()) {
            if (key[0].equals(getUsername())) {
                timesBorrowed++;
            }
        }
        return timesBorrowed;
    }

    /**
     * Removes a certain trade request given the key of that trade request.
     *
     * @param key the key of the trade request to be removed
     */
    public void removeTradeRequests(String[] key) {
        tradeRequests.remove(key);
    }

    /**
     * Increases this user's number of incomplete trades by 1.
     */
    public void increaseNumIncomplete() {
        numIncomplete++;
    }

    /**
     * Returns this user's number of incomplete trades.
     *
     * @return this user's number of incomplete trades
     */
    public int getNumIncomplete() {
        return numIncomplete;
    }

    /**
     * Getter for this user's inventory.
     *
     * @return this user's inventory
     */
    public List<Long> getInventory() {
        return inventory;
    }

    /**
     * Moves the given ID of an approved item from this user's pending inventory to their approved inventory.
     *
     * @param itemIDToAdd the ID of the approved item being added to this user's inventory
     */
    public void addInventory(long itemIDToAdd) {
        inventory.add(itemIDToAdd);
        pendingInventory.remove(itemIDToAdd);
    }

    /**
     * Removes the given item ID from this user's inventory.
     *
     * @param itemIDToRemove the item ID being removed from this user's inventory
     */
    public void removeInventory(long itemIDToRemove) {
        inventory.remove(itemIDToRemove);
    }

    /**
     * Getter for this user's inventory of items waiting for approval.
     *
     * @return this user's pending inventory
     */
    public List<Long> getPendingInventory() {
        return pendingInventory;
    }

    /**
     * Adds the given ID of an item waiting for approval to this user's pending inventory.
     *
     * @param itemIDToAdd the item ID being added to this user's pending inventory
     */
    public void addPendingInventory(long itemIDToAdd) {
        pendingInventory.add(itemIDToAdd);
    }

    /**
     * Removes the given ID of an item waiting for approval from this user's pending inventory.
     * Happens when the item is rejected by an admin.
     *
     * @param itemIDToRemove the item ID being removed from this user's pending inventory
     */
    public void removePendingInventory(long itemIDToRemove) {
        pendingInventory.remove(itemIDToRemove);
    }

    /**
     * Getter for this user's wishlist.
     *
     * @return this user's wishlist
     */
    public List<Long> getWishlist() {
        return wishlist;
    }

    /**
     * Adds the given item ID to this user's wishlist.
     *
     * @param itemIDToAdd the item ID being added to this user's wishlist
     */
    public void addWishlist(long itemIDToAdd) {
        wishlist.add(itemIDToAdd);
    }

    /**
     * Removes the given item ID from this user's wishlist.
     *
     * @param itemIDToRemove the item ID being removed from this user's wishlist
     */
    public void removeWishlist(long itemIDToRemove) {
        wishlist.remove(itemIDToRemove);
    }

    /**
     * Gets whether or not this user is frozen.
     *
     * @return true if this user's account is frozen, false otherwise
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * Sets this user's account status to frozen.
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * Sets this user's status to NOT frozen.
     */
    public void unfreeze() {
        isFrozen = false;
        numIncomplete = 0;
    }

    /**
     * Adds to this user's list of trade requests.
     *
     * @param usernames the usernames of the two traders
     * @param itemIDs   the item IDs involved in the trade request
     */
    public void addTradeRequest(String[] usernames, long[] itemIDs) {
        tradeRequests.put(usernames, itemIDs);
    }

    /**
     * Getter for this user's trade requests.
     *
     * @return a map containing all of this user's trade requests
     */
    public Map<String[], long[]> getTradeRequests() {
        return tradeRequests;
    }

    /**
     * Getter for this user's weekly trade limit.
     *
     * @return this user's weekly limit for trades
     */
    public int getWeeklyTradeMax() {
        return weeklyTradeMax;
    }

    /**
     * Setter for this user's weekly trade limit.
     *
     * @param newMax the new weekly trade limit
     */
    public void setWeeklyTradeMax(int newMax) {
        weeklyTradeMax = newMax;
    }

    /**
     * Getter for this user's meeting edit limit.
     *
     * @return this user's limit on how many times they can edit a meeting
     */
    public int getMeetingEditMax() {
        return meetingEditMax;
    }

    /**
     * Setter for this user's meeting edit limit.
     *
     * @param newMax the new limit on how many times this user can edit a meeting
     */
    public void setMeetingEditMax(int newMax) {
        meetingEditMax = newMax;
    }

    /**
     * Getter for this user's minimum lending over borrowing threshold.
     *
     * @return this user's minimum lending over borrowing threshold
     */
    public int getLendMinimum() {
        return lendMinimum;
    }

    /**
     * Setter for this user's minimum lending over borrowing threshold.
     *
     * @param newMin the new minimum lending over borrowing threshold
     */
    public void setLendMinimum(int newMin) {
        lendMinimum = newMin;
    }

    /**
     * Getter for this user's limit on incomplete trades.
     *
     * @return this user's limit on incomplete trades
     */
    public int getIncompleteMax() {
        return incompleteMax;
    }

    /**
     * Setter for this user's limit on incomplete trades.
     *
     * @param newMax the new limit on incomplete trades
     */
    public void setIncompleteMax(int newMax) {
        incompleteMax = newMax;
    }

    /**
     * Finds whether or not the given item is involved in this normal user's trade requests.
     *
     * @param itemID the ID of the item being searched for in trade requests
     * @return true if the item is involved in a trade request, false otherwise
     */
    public boolean isRequestedInTrade(long itemID) {
        for (Map.Entry<String[], long[]> entry : tradeRequests.entrySet()) {
            if (entry.getValue()[0] == itemID || entry.getValue()[1] == itemID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns where the NormalUser is on Vacation or not.
     *
     * @return true if this user is on vacation, else false
     */
    public boolean getIsOnVacation() {
        return isOnVacation;
    }

    /**
     * Sets NormalUser's status to on Vacation
     */
    public void onVacation() {
        isOnVacation = true;
    }

    /**
     * Sets NormalUser's status to not on Vacation
     */
    public void notOnVacation() {
        isOnVacation = false;
    }

    /**
     * Getter for this user's homecity
     * @return this user's homecity
     */
    public String getHomeCity() {
        return homeCity;
    }

    /**
     * Setter for the homecity of NormalUser
     * @param homeCity the home city
     */
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

}





