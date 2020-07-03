import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * NormalUser is a class that represents a non-administrative user of our trade program.
 *
 * @author Ning Zhang
 * @author Liam
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-03
 */

public class NormalUser extends User implements Serializable {
    private ArrayList<Long> inventory;
    private ArrayList<Long> pendingInventory;
    private ArrayList<Long> wishlist;
    private HashMap<String[], long[]> tradeRequests;

    private Boolean isFrozen;

    private int weeklyTradeMax = 3;
    private int meetingEditMax = 3;
    private int lendMinimum = 1; //to borrow in a one-way trade, user must have lent at least lendMinimum item(s) more than they have borrowed
    private int incompleteMax = 5; //the limit on how many incomplete trades the user can have before their account is at risk of being frozen

    // private Item[] lastestThreeItems = new Item[3];

    /**
     * Class constructor.
     * Creates a NormalUser with given username, email, and password.
     * Also initializes default empty inventory, wishlist, and tradeRequests, and account status non-frozen.
     *
     * @param username the username being assigned to this NormalUser
     * @param email the email address being assigned to this NormalUser
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
     * Getter for this NormalUser's inventory.
     *
     * @return this NormalUser's inventory
     */
    public ArrayList<Long> getInventory() {
        return inventory;
    }

    /**
     * Moves the given ID of an approved Item from this NormalUser's pending inventory to their regular inventory.
     *
     * @param itemIDToAdd the ID of the approved Item being added to this NormalUser's inventory
     */
    public void addInventory(Long itemIDToAdd) {
        inventory.add(itemIDToAdd);
        pendingInventory.remove(itemIDToAdd);
    }

    /**
     * Removes the given ID of an Item from this NormalUser's inventory.
     *
     * @param itemIDToRemove the ID of the Item being removed from this NormalUser's inventory
     */
    public void removeInventory(Long itemIDToRemove) {
        inventory.remove(itemIDToRemove);
    }

    /**
     * Getter for this NormalUser's inventory of items waiting for approval.
     *
     * @return this NormalUser's pending inventory
     */
    public ArrayList<Long> getPendingInventory() {
        return pendingInventory;
    }

    /**
     * Adds the given ID of an Item waiting for approval to this NormalUser's pending inventory.
     *
     * @param itemToAdd the ID of the Item being added to this NormalUser's pending inventory
     */
    public void addPendingInventory(Long itemToAdd) {
        pendingInventory.add(itemToAdd);
    }

    /**
     * Getter for this NormalUser's wishlist.
     *
     * @return this NormalUser's wishlist
     */
    public ArrayList<Long> getWishlist() {
        return wishlist;
    }

    /**
     * Adds the given ID of an Item to this NormalUser's wishlist.
     *
     * @param itemIDToAdd the ID of the Item being added to this NormalUser's wishlist
     */
    public void addWishlist(Long itemIDToAdd) {
        wishlist.add(itemIDToAdd);
    }

    /**
     * Removes the given ID of an Item from this NormalUser's wishlist.
     *
     * @param itemIDToRemove the ID of the Item being removed from this NormalUser's wishlist
     */
    public void removeWishlist(Long itemIDToRemove) {
        wishlist.remove(itemIDToRemove);
    }

    /**
     * Gets whether or not this NormalUser is frozen.
     *
     * @return true if this NormalUser's account is frozen, false otherwise
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
     * Removes a trade request by the given item id.
     *
     * @param itemId the ID of an Item involved in the trade request being removed
     */
    public void removeTradeRequest(int itemId) {

    }

    /**
     * Getter for this NormalUser's trade requests.
     *
     * @return a HashMap containing all of this NormalUser's trade requests
     */
    public HashMap<String[], long[]> getTradeRequest() {
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
     * @return this NormalUser's limit on how many times they can edit a meeting
     */
    public int getMeetingEditMax() {
        return meetingEditMax;
    }

    /**
     * Setter for this NormalUser's meeting edit limit.
     *
     * @param newMax the new limit on how many times they can edit a meeting
     */
    public void setMeetingEditMax(int newMax) {
        meetingEditMax = newMax;
    }

    /**
     * Getter for this NormalUser's minimum lending over borrowing limit.
     *
     * @return this NormalUser's minimum lending over borrowing limit
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
     * @return this NormalUser's limit on incomplete trades
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
}



