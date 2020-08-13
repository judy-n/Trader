package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a normal user in our trade program.
 * Normal users are allowed to trade items with other normal users and manage their inventory and wishlist.
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

    /**
     * Creates a <NormalUser></NormalUser> with the given username, email, password, and home city.
     * Also initializes default empty inventory, wishlist, and tradeRequests,
     * and account status non-frozen + not on vacation.
     *
     * @param username the username being assigned to this <NormalUser></NormalUser>
     * @param email    the email address being assigned to this <NormalUser></NormalUser>
     * @param password the password being assigned to this <NormalUser></NormalUser>
     * @param homeCity the homeCity of this <NormalUser></NormalUser>
     */
    public NormalUser(String username, String email, String password, String homeCity) {
        super(username, email, password);
        this.homeCity = homeCity;
        inventory = new ArrayList<>();
        pendingInventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradeRequests = new HashMap<>();
        this.homeCity = homeCity;
        isFrozen = false;
        isOnVacation = false;
    }

    /**
     * Returns the number of times this user has requested to borrow an item from someone else.
     * Only counts requests that have yet to be accepted or rejected.
     * Doesn't count received trade requests.
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
     * Returns the number of times this user has offered to lend an item to someone else in a trade request.
     * Only counts requests that have yet to be accepted or rejected.
     * Doesn't count received trade requests.
     *
     * @return the number of times this user has offered to lend an item in a trade request
     */
    public int getTimesLent() {
        int timesLent = 0;
        for (String[] key : tradeRequests.keySet()) {
            if (key[0].equals(getUsername()) && tradeRequests.get(key)[0] != 0) {
                timesLent++;
            }
        }
        return timesLent;
    }

    /**
     * Removes a certain trade request given the key of that trade request.
     *
     * @param key the key of the trade request to be removed
     */
    public void removeTradeRequest(String[] key) {
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
     * Returns where the NormalUser is on Vacation or not.
     *
     * @return true if this user is on vacation, else false
     */
    public boolean getIsOnVacation() {
        return isOnVacation;
    }

    /**
     * Setter for this user's vacation status.
     *
     * @param vacationStatus this user's vacation status (true if on vacation, false otherwise).
     */
    public void setOnVacation(boolean vacationStatus) {
        isOnVacation = vacationStatus;
    }

    /**
     * Getter for this user's home city
     *
     * @return this user's home city
     */
    public String getHomeCity() {
        return homeCity;
    }
}





