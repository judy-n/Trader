import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NormalUser is a class that represents a normal user of our trade program.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-11
 */

public class NormalUser extends User implements Serializable {
    private List<Long> inventory;
    private List<Long> pendingInventory;
    private List<Long> wishlist;
    private Map<String[], long[]> tradeRequests;

    private boolean isFrozen;
    private int numIncomplete;

    private int weeklyTradeMax = 3;
    private int meetingEditMax = 3;
    private int lendMinimum = 1; //to borrow in a one-way trade, user must have lent at least lendMinimum item(s) more than they have borrowed
    private int incompleteMax = 5; //the limit on how many incomplete trades the user can have before their account is at risk of being frozen

    /**
     * Class constructor.
     * Creates a <NormalUser></NormalUser> with given username, email, and password.
     * Also initializes default empty inventory, wishlist, and tradeRequests, and account status non-frozen.
     *
     * @param username the username being assigned to this NormalUser
     * @param email    the email address being assigned to this NormalUser
     * @param password the password being assigned to this NormalUser
     */
    public NormalUser(String username, String email, String password) {
        super(username, email, password);
        inventory = new ArrayList<>();
        pendingInventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradeRequests = new HashMap<>();
        isFrozen = false;
    }

    /**
     * This method returns the number of times the user requested to borrow
     * an item from someone else
     *
     * @return the number of times the user requested to borrow
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
     * This method returns true if the user has tried to borrow something
     *
     * @return true if the user has tried to borrow something, false otherwise
     */
    public boolean hasBorrowed() {
        for (String[] key : tradeRequests.keySet()) {
            if (key[0].equals(getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method removes a certain trade request given the key of that trade request.
     *
     * @param key the key of the trade request
     */
    public void removeTradeRequests(String[] key) {
        tradeRequests.remove(key);
    }

    /**
     * This method increases the user's number of incomplete trades
     */
    public void increaseNumIncomplete() {
        numIncomplete++;
    }

    /**
     * This method returns the user's number of incomplete trades
     *
     * @return number of incomplete trades
     */
    public int getNumIncomplete() {
        return numIncomplete;
    }

    /**
     * Getter for this NormalUser's inventory.
     *
     * @return this user's inventory
     */
    public List<Long> getInventory() {
        return inventory;
    }

    /**
     * Moves the given ID of an approved Item from this NormalUser's pending inventory to their regular inventory.
     *
     * @param itemIDToAdd the ID of the approved Item being added to this user's inventory
     */
    public void addInventory(Long itemIDToAdd) {
        inventory.add(itemIDToAdd);
        pendingInventory.remove(itemIDToAdd);
    }

    /**
     * Removes the given ID of an Item from this NormalUser's inventory.
     *
     * @param itemIDToRemove the ID of the Item being removed from this user's inventory
     */
    public void removeInventory(Long itemIDToRemove) {
        inventory.remove(itemIDToRemove);
    }

    /**
     * Getter for this NormalUser's inventory of items waiting for approval.
     *
     * @return this user's pending inventory
     */
    public List<Long> getPendingInventory() {
        return pendingInventory;
    }

    /**
     * Adds the given ID of an Item waiting for approval to this NormalUser's pending inventory.
     *
     * @param itemIDToAdd the ID of the Item being added to this user's pending inventory
     */
    public void addPendingInventory(Long itemIDToAdd) {
        pendingInventory.add(itemIDToAdd);
    }

    /**
     * Removes the given ID of an Item waiting for approval from this NormalUser's pending inventory.
     * Happens when this NormalUser's item is rejected.
     *
     * @param itemIDToRemove the ID of the Item being removed from this user's pending inventory
     */
    public void removePendingInventory(Long itemIDToRemove) {
        pendingInventory.remove(itemIDToRemove);
    }

    /**
     * Getter for this NormalUser's wishlist.
     *
     * @return this user's wishlist
     */
    public List<Long> getWishlist() {
        return wishlist;
    }

    /**
     * Adds the given ID of an Item to this NormalUser's wishlist.
     *
     * @param itemIDToAdd the ID of the Item being added to this user's wishlist
     */
    public void addWishlist(Long itemIDToAdd) {
        wishlist.add(itemIDToAdd);
    }

    /**
     * Removes the given ID of an Item from this NormalUser's wishlist.
     *
     * @param itemIDToRemove the ID of the Item being removed from this user's wishlist
     */
    public void removeWishlist(Long itemIDToRemove) {
        wishlist.remove(itemIDToRemove);
    }

    /**
     * Gets whether or not this NormalUser is frozen.
     *
     * @return true if this user's account is frozen, false otherwise
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * Sets this NormalUser's status to frozen.
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * Sets this NormalUser's status to NOT frozen.
     */
    public void unfreeze() {
        isFrozen = false;
        numIncomplete = 0;
    }

    /**
     * Adds to this NormalUser's list of trade requests.
     *
     * @param usernames an array containing the usernames of the two traders
     * @param itemIDs   an array containing the IDs of the Items involved in the trade request
     */
    public void addTradeRequest(String[] usernames, long[] itemIDs) {
        tradeRequests.put(usernames, itemIDs);
    }

    /**
     * Getter for this NormalUser's trade requests.
     *
     * @return a map containing all of this user's trade requests
     */
    public Map<String[], long[]> getTradeRequests() {
        return tradeRequests;
    }

    /**
     * Getter for this NormalUser's weekly trade limit.
     *
     * @return this NormalUser's weekly limit for trades
     */
    public int getWeeklyTradeMax() {
        return weeklyTradeMax;
    }

    /**
     * Setter for this NormalUser's weekly trade limit.
     *
     * @param newMax the new weekly trade limit
     */
    public void setWeeklyTradeMax(int newMax) {
        weeklyTradeMax = newMax;
    }

    /**
     * Getter for this NormalUser's meeting edit limit.
     *
     * @return this user's limit on how many times they can edit a meeting
     */
    public int getMeetingEditMax() {
        return meetingEditMax;
    }

    /**
     * Setter for this NormalUser's meeting edit limit.
     *
     * @param newMax the new limit on how many times this user can edit a meeting
     */
    public void setMeetingEditMax(int newMax) {
        meetingEditMax = newMax;
    }

    /**
     * Getter for this NormalUser's minimum lending over borrowing limit.
     *
     * @return this user's minimum lending over borrowing limit
     */
    public int getLendMinimum() {
        return lendMinimum;
    }

    /**
     * Setter for this NormalUser's minimum lending over borrowing limit.
     *
     * @param newMin the new minimum lending over borrowing limit
     */
    public void setLendMinimum(int newMin) {
        lendMinimum = newMin;
    }

    /**
     * Getter for this NormalUser's limit on incomplete trades.
     *
     * @return this user's limit on incomplete trades
     */
    public int getIncompleteMax() {
        return incompleteMax;
    }

    /**
     * Setter for this NormalUser's limit on incomplete trades.
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
            if (entry.getValue()[1] == itemID) {
                return true;
            }
        }
        return false;
    }
}




